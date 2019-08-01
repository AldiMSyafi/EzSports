package com.aldimsyafi.ezsport;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

/*
 10116065
 Aldi Muhamamad Syafi
 AKB-2 / IF-2

 CHANGELOG
 membuat splash screen 05-07-2019 8.00 */
public class MainActivity extends AppCompatActivity {


    //waktu lamanya loading splash screen 2000=2detik
    private int waktu_loading=2000;
    private Window mWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //memanggil splash screen dari layout
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWindow = getWindow();
        mWindow.getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        //menampilkan dan langsung menuju ke HomeActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {


                Intent home=new Intent(MainActivity.this, HomeActivity.class);
                startActivity(home);
                finish();

            }
        },waktu_loading);

    }
}
