package com.example.ezsport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class Detail_info extends AppCompatActivity {
    private ImageView imgPost;
    private TextView txtPostDesc,txtPostTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_info);

        imgPost = findViewById(R.id.detail_img_info);

        txtPostTitle = findViewById(R.id.detail_title_info);
        txtPostDesc = findViewById(R.id.detail_desc_info);

        String postimage = getIntent().getExtras().getString("image");
        Glide.with(this).load(postimage).into(imgPost);

        String postTitle = getIntent().getExtras().getString("title");
        txtPostTitle.setText(postTitle);

        String postDesc = getIntent().getExtras().getString("desc");
        txtPostDesc.setText(postDesc);
    }
}
