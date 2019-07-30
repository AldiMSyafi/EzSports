package com.example.ezsport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu_Utama_Admin extends AppCompatActivity {
    private Button btnlogout,btnartikel,btninfo;
    private Intent HomeActivity;
    private Intent Conten_Artikel;
    private Intent Content_Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu__utama__admin);
        btnlogout = findViewById(R.id.btn_logout);
        btnartikel = findViewById(R.id.btn_artikel);
        btninfo = findViewById(R.id.btn_info);

        Conten_Artikel = new Intent(this,Content_Artikel.class);
        Content_Info = new Intent(this,Content_Info.class);
        HomeActivity = new  Intent (this,HomeActivity.class);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(HomeActivity);
            }
        });
        btnartikel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Conten_Artikel);
            }
        });
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Content_Info);
            }
        });

    }
}
