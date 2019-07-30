package com.example.ezsport.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.ezsport.Upload;

import com.example.ezsport.Upload_Info;
import com.squareup.picasso.Picasso;
import com.example.ezsport.R;

import java.util.List;

public class imageAdapter extends RecyclerView.Adapter<imageAdapter.ImageViewHolder> {
    private Context mContext;
    private List<Upload_Info> mUploads1;

    public imageAdapter(Context context,List<Upload_Info>uploads){
        mContext = context;
        mUploads1 = uploads;
    }
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {

        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item_artikel,parent,false );
        return new ImageViewHolder(v);
    }
    //get data dari upload
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {

        Upload_Info uploadcurrent = mUploads1.get(position);
        holder.textViewTitle.setText(uploadcurrent.getmTitleinfo());
        Picasso.get()
                .load(uploadcurrent.getmImageUrl())
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {

        return mUploads1.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder{
        public TextView textViewTitle;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            imageView = itemView.findViewById(R.id.image_view_upload);

        }
        }
    }
