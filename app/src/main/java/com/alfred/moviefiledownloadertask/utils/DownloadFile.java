package com.alfred.moviefiledownloadertask.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.alfred.moviefiledownloadertask.data.response.Movie;
import com.alfred.moviefiledownloadertask.di.adapters.MovieAdapter;
import com.blankj.utilcode.util.ToastUtils;
import com.downloader.Error;
import com.downloader.OnCancelListener;
import com.downloader.OnDownloadListener;
import com.downloader.OnPauseListener;
import com.downloader.OnProgressListener;
import com.downloader.OnStartOrResumeListener;
import com.downloader.PRDownloader;
import com.downloader.PRDownloaderConfig;
import com.downloader.Progress;
import com.downloader.Status;

import java.util.List;

public class DownloadFile {

    private List<Movie> dataList ;

    int downloadId;

    public void downloadFile(Context context, String url, int downloadId, List<Movie> movieList, Movie movie, MovieAdapter adapter){
        this.downloadId = downloadId;
        this.dataList = movieList;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Downloading.....");
        progressDialog.setMessage("Preparing.....");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel",
                (DialogInterface.OnClickListener) null);
        progressDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Pause/Resume",
                (DialogInterface.OnClickListener) null);
        progressDialog.show();

        progressDialog.getButton(DialogInterface.BUTTON_NEGATIVE).setOnClickListener(
                view -> {
                    PRDownloader.cancel(this.downloadId);
                    progressDialog.dismiss();
                });
        progressDialog.getButton(DialogInterface.BUTTON_POSITIVE).setOnClickListener(
                view -> {
                    if (Status.RUNNING == PRDownloader.getStatus(this.downloadId)) {
                        PRDownloader.pause(this.downloadId);
                    }else if (Status.PAUSED == PRDownloader.getStatus(this.downloadId)) {
                        PRDownloader.resume(this.downloadId);
                    }
                });

        PRDownloaderConfig config = PRDownloaderConfig.newBuilder()
                .setDatabaseEnabled(true)
                .build();
        PRDownloader.initialize(context, config);

        /*download id*/
        this.downloadId = PRDownloader.download(url, Utils.getRootDirPath(context), Utils.getFileName(url,context))
                .build()
                .setOnStartOrResumeListener(new OnStartOrResumeListener() {
                    @Override
                    public void onStartOrResume() {
                        progressDialog.setTitle("Download Started");
                    }
                })
                .setOnPauseListener(new OnPauseListener() {
                    @Override
                    public void onPause() {
                        progressDialog.setTitle("Download Paused");
                    }
                })
                .setOnCancelListener(new OnCancelListener() {
                    @Override
                    public void onCancel() {
                        Toast.makeText(context, "Download cancelled", Toast.LENGTH_SHORT).show();
                    }
                })
                .setOnProgressListener(new OnProgressListener() {
                    @Override
                    public void onProgress(Progress progress) {
                        long progressPercentage = progress.currentBytes*100 / progress.totalBytes;
                        progressDialog.setProgress(((int) progressPercentage));

                        progressDialog.setMessage(Utils.getBytesToMBString(progress.currentBytes) +"/"+ Utils.getBytesToMBString(progress.totalBytes));
                    }
                })
                .start(new OnDownloadListener() {
                    @Override
                    public void onDownloadComplete() {
                        Toast.makeText(context, "Download completed", Toast.LENGTH_SHORT).show();
                        int position = movieList.indexOf( movie);
                        dataList.get(position).setStatus(1);
                        //adapter.setList(dataList);
                        adapter.notifyItemChanged(position);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(Error error) {
                        Toast.makeText(context, "Download error", Toast.LENGTH_SHORT).show();

                    }
                });
    }
}
