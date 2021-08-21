package com.alfred.moviefiledownloadertask.ui.mainActivity;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import com.alfred.moviefiledownloadertask.BuildConfig;
import com.alfred.moviefiledownloadertask.R;
import com.alfred.moviefiledownloadertask.data.response.Movie;
import com.alfred.moviefiledownloadertask.data.response.APIStatus;
import com.alfred.moviefiledownloadertask.databinding.ActivityMainBinding;
import com.alfred.moviefiledownloadertask.di.adapters.MovieAdapter;
import com.alfred.moviefiledownloadertask.utils.DownloadFile;
import com.alfred.moviefiledownloadertask.utils.LoadingDialog;
import com.alfred.moviefiledownloadertask.utils.PermissionCheck;
import com.alfred.moviefiledownloadertask.utils.ViewModelProviderFactory;
import com.blankj.utilcode.util.ToastUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MainActivity extends DaggerAppCompatActivity {


    @Inject
    ViewModelProviderFactory providerFactory;

    ActivityMainBinding binding;
    private LoadingDialog dialog;
    private List<Movie> dataList ;
    MainActivityViewModel loginViewModel;
    MovieAdapter adapter;
    int downloadId;
    Context context;
    Activity activity;
    PermissionCheck permissionCheck ;
    String permission = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        context = this;
        activity = this;
        dialog = new LoadingDialog(this);
        loginViewModel = new ViewModelProvider(this,providerFactory).get(MainActivityViewModel.class);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        loginViewModel.moviesList();
        initRecyclerView();
        subscribeRequest();
        permissionCheck = new PermissionCheck();
        permissionCheck.permissionCheck(this, context);
    }

    private void initRecyclerView() {

        binding.rvMovie.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        adapter = new MovieAdapter(getApplicationContext(), adapterInterface);
        binding.rvMovie.setAdapter(adapter);
    }

    MovieAdapter.MovieAdapterInterface adapterInterface = new MovieAdapter.MovieAdapterInterface() {
        @Override
        public void OnItemClicked(Movie movie, String item_id, String url) {

            DownloadFile downloadFile = new DownloadFile();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionCheck.getPermission().equals("1"))
                {
                    //download file
                    downloadFile.downloadFile(context, url, Integer.valueOf(item_id), dataList, movie, adapter);
                }else {
                    permissionCheck.permissionCheck(activity, context);
                }
            }else {
                //download file
                downloadFile.downloadFile(context, url, Integer.valueOf(item_id), dataList, movie, adapter);

            }
        }
    };

    private void subscribeRequest() {


        loginViewModel.getResponseLiveData().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                dataList = new ArrayList<>();
                if (movies.size()>0){
                    dataList = movies;
                    binding.rvMovie.setVisibility(View.VISIBLE);
                    adapter.setList(movies);
                    adapter.notifyDataSetChanged();
                }else {
                    binding.rvMovie.setVisibility(View.GONE);
                }
            }
        });

        loginViewModel.getStatus().observe(this, new Observer<APIStatus>() {
            @Override
            public void onChanged(APIStatus status) {
                switch (status)
                {
                    case IDLE:
                        dialog.stopLoader();
                        break;
                    case LOADING:
                        dialog.startLoadingDialog();
                        break;
                    case ERROR:
                        Toast.makeText(getApplicationContext(), "429 - Too Many Requests. Refer: https://beeceptor.com/pricing", Toast.LENGTH_SHORT).show();
                        dialog.stopLoader();
                        break;
                    case ERRORServer:
                        Toast.makeText(getApplicationContext(), "Internal Server Error", Toast.LENGTH_SHORT).show();
                        dialog.stopLoader();
                        break;
                    case SUCCESS:

                        //Toast.makeText(getApplicationContext(), "successful", Toast.LENGTH_SHORT).show();
                        //String loggedINUser = Constants.getLoggedInUserType();
                        dialog.stopLoader();
                        break;
                }
            }
        });
    }
}