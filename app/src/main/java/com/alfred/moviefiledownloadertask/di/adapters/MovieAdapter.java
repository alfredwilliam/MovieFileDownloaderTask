package com.alfred.moviefiledownloadertask.di.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alfred.moviefiledownloadertask.R;
import com.alfred.moviefiledownloadertask.data.response.Movie;
import com.alfred.moviefiledownloadertask.databinding.ItemDownloaderCardBinding;

import java.util.ArrayList;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.AckdServiceActivityViewModel> {

    private List<Movie> types = new ArrayList<>();
    private Context context;
    private static ClickListener clickListener;
// Interface Object
    private MovieAdapterInterface adapterInterface;

    public MovieAdapter(Context context, MovieAdapterInterface adapterInterface){
        this.context = context;

        // Initialize your interface to send updates to fragment.
        this.adapterInterface = adapterInterface;
    }

    public MovieAdapter(List<Movie> types, Context context) {
        this.types = types;
        this.context = context;
    }

    @NonNull
    @Override
    public AckdServiceActivityViewModel onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ItemDownloaderCardBinding itemMainLoginCardBinding = DataBindingUtil.inflate(
        LayoutInflater.from(viewGroup.getContext()), R.layout.item_downloader_card, viewGroup, false
        );

        return new AckdServiceActivityViewModel(itemMainLoginCardBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AckdServiceActivityViewModel viewHolder, int i) {
        /*viewHolder.itemMainLoginCardBinding.tvCardTitle.setText(types.get(i).getName());
        viewHolder.itemMainLoginCardBinding.tvCounter.setText(types.get(i).getId()+1);*/
        /*if (types.isEmpty())
            viewHolder.itemMainLoginCardBinding.empty.setVisibility(View.VISIBLE);

        viewHolder.itemMainLoginCardBinding.empty.setVisibility(View.GONE);*/
        viewHolder.itemMainLoginCardBinding.setModel(types.get(i) );
        if (types.get(i).getStatus()==1){
            viewHolder.itemMainLoginCardBinding.btnDownload.setEnabled(false);
        }else {
            viewHolder.itemMainLoginCardBinding.btnDownload.setEnabled(true);
        }
        /*if (types.get(i).getId().equals("9")){
            viewHolder.itemView.setVisibility(View.GONE);
            viewHolder.itemMainLoginCardBinding.cl.setVisibility(View.GONE);
        }
        if (types.get(i).getId().equals("10")){
            viewHolder.itemView.setVisibility(View.GONE);
            viewHolder.itemMainLoginCardBinding.cl.setVisibility(View.GONE);
        }
        if (types.get(i).getId().equals("11")){
            viewHolder.itemView.setVisibility(View.GONE);
            viewHolder.itemMainLoginCardBinding.cl.setVisibility(View.GONE);
        }*/
        /*Glide.with(context)
                .load(imageUri)
                .fitCenter()
                .into(viewHolder.itemMainLoginCardBinding.ivCardImage);*/


        viewHolder.itemMainLoginCardBinding.btnDownload.setOnClickListener(v -> {
            if (types.get(i).getId() != null) {
                try {
                    //Toast.makeText(context, ""+types.get(i).getId(), Toast.LENGTH_SHORT).show();
                    //Intent intent = new Intent();
                    adapterInterface.OnItemClicked(types.get(i), types.get(i).getId().toString(), types.get(i).getUrl().toString());

                    /*intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(types.get(i).getId()+""));
                    viewHolder.itemView.getContext().startActivity(intent);*/
                } catch (Exception e) {
                    Log.i("*** Uri *** Excep",e.toString());
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return types.size();
    }

    public void setList(List<Movie> types){
        this.types = types;
        //notifyDataSetChanged();
    }

    public class AckdServiceActivityViewModel extends RecyclerView.ViewHolder implements View.OnClickListener {

        ItemDownloaderCardBinding itemMainLoginCardBinding;

        AckdServiceActivityViewModel(@NonNull ItemDownloaderCardBinding itemView) {
            super(itemView.getRoot());
            itemMainLoginCardBinding = itemView;
        }



        @Override
        public void onClick(View v) {
            clickListener.onClick(v, getAdapterPosition());
        }

    }


    public void setOnItemClickListener(ClickListener clickListener) {
        MovieAdapter.clickListener = clickListener;
    }


    public interface ClickListener {
        void onClick(View view, int position);

    }


    // Your interface to send data to your fragment
    public interface MovieAdapterInterface {
        void OnItemClicked(Movie movie, String item_id, String url);
    }
}
