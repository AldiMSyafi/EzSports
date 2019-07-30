package com.example.ezsport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.TextAppearanceSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ezsport.Fragment.Admin;
import com.example.ezsport.Fragment.Apex;
import com.example.ezsport.Fragment.Article;
import com.example.ezsport.Fragment.Csgo;
import com.example.ezsport.Fragment.Dota;
import com.example.ezsport.Fragment.Info;

/*
 10116065
 Aldi Muhamamad Syafi
 AKB-2 / IF-2

 CHANGELOG
 membuat splash screen 05-07-2019 8.00
 Membuat tampilan navigation view 06-07-2019 10.00
 membuat tampilan menu utama 06-07-2019 13.00
 membuat tampilan info tournament 06-07-2019 14.00
 membuat tampilan article 06-07-2019 14.30
 membuat tampilan article 07-07-2019 12.30
 membuat tampilan article 07-07-2019 13.00
 membuat tampilan article 07-07-2019 14.00


 */

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    private DrawerLayout drawer;

    //menampilkan navigation view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.draw_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();

                if (savedInstanceState == null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new Article()).commit();
        navigationView.setCheckedItem(R.id.nav_article);

                }
                //merubah warna dan ukuran title di navigation bar
       NavigationView navview = (NavigationView) findViewById(R.id.nav_view);
        Menu menu = navview.getMenu();
        MenuItem news= menu.findItem(R.id.news);
        SpannableString s = new SpannableString(news.getTitle());
        s.setSpan(new TextAppearanceSpan(this, R.style.txtapp),0,s.length(),0);
        news.setTitle(s);
        navview.setNavigationItemSelectedListener(this);

        NavigationView navview2 = (NavigationView) findViewById(R.id.nav_view);
        Menu menu2 = navview2.getMenu();
        MenuItem gamenews= menu.findItem(R.id.gamenews);
        SpannableString s2 = new SpannableString(gamenews.getTitle());
        s2.setSpan(new TextAppearanceSpan(this, R.style.txtapp),0,s2.length(),0);
        gamenews.setTitle(s2);
        navview2.setNavigationItemSelectedListener(this);

        NavigationView navview3 = (NavigationView) findViewById(R.id.nav_view);
        Menu menu3 = navview3.getMenu();
        MenuItem ezsports = menu.findItem(R.id.ezsports);
        SpannableString s3 = new SpannableString(ezsports.getTitle());
        s3.setSpan(new TextAppearanceSpan(this, R.style.txtapp),0,s3.length(),0);
        ezsports.setTitle(s3);
        navview3.setNavigationItemSelectedListener(this);

    }

    //Menghubungkan tiap fragment yang ada di navigation bar
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_info_tour:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Info()).commit();
                break;

            case R.id.nav_article:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Article()).commit();
                break;

            case R.id.nav_dota:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Dota()).commit();
                break;

            case R.id.nav_csgo:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Csgo()).commit();
                break;

            case R.id.nav_apex:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Apex()).commit();
                break;
            case R.id.nav_admin:
                Intent l= new Intent(HomeActivity.this,login_admin.class);
                startActivity(l);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    //fungsi menutup navigation view
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();
    }
    }


}
