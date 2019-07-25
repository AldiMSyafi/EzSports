package com.example.ezsport.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.ezsport.R;

public class Article extends Fragment {
        private ImageButton article;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article,container,false);

        article =(ImageButton) view.findViewById(R.id.article);

        return view;
    }

    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        article.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gmmIntentUri = "https://www.revivaltv.id/tekuk-vitality-team-liquid-juara-esl-one-cologne-dan-intel-grand-slam/";
                Intent lokasi = new Intent(Intent.ACTION_VIEW);
                lokasi.setData(Uri.parse(gmmIntentUri));
                startActivity(lokasi);
            }
        }));
    }

    }
