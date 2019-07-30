package com.example.ezsport.Adapter;

import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ezsport.Upload_Info;

import com.squareup.picasso.Picasso;
import com.example.ezsport.R;

import java.util.List;
public class ImageAdapter_info extends RecyclerView.Adapter<ImageAdapter_info.ImageViewHolder>{
        private Context mContext;
        private List<Upload_Info> mUploads;

        public ImageAdapter_info (Context context,List<Upload_Info> uploads){
            mContext = context;
            mUploads = uploads;

        }


    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.image_item_info, viewGroup, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder imageViewHolder, int position) {
                Upload_Info uploadcurrent =mUploads.get(position);
                imageViewHolder.textViewDesc.setText(uploadcurrent.getmDescinfo());
                Picasso.get()
                        .load(uploadcurrent.getmImageUrl())
                        .fit()
                        .into(imageViewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewDesc;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }

}
