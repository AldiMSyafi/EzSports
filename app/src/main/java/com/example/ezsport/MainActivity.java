package com.example.ezsport;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ezsport.Fragment.Info;

/*
 10116065
 Aldi Muhamamad Syafi
 AKB-2 / IF-2

 CHANGELOG
 membuat splash screen 05-07-2019 8.00 */
public class MainActivity extends AppCompatActivity {


    private int waktu_loading=2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
