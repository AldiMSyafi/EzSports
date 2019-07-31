package com.example.ezsport.Detail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ezsport.R;

public class Detail_Artikel extends AppCompatActivity {
    private ImageView imgPost;
    private TextView txtPostDesc,txtPostTitle;

    //get data dari adapter
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail__artikel);

        imgPost = findViewById(R.id.detail_img_artikel);

        txtPostTitle = findViewById(R.id.detail_title_artikel);
        txtPostDesc = findViewById(R.id.detail_desc_artikel);

        String postimage = getIntent().getExtras().getString("image");
        Glide.with(this).load(postimage).into(imgPost);

        String postTitle = getIntent().getExtras().getString("title");
        txtPostTitle.setText(postTitle);

        String postDesc = getIntent().getExtras().getString("desc");
        txtPostDesc.setText(postDesc);

    }

}
