package com.example.ezsport.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ezsport.R;

public class Tampilan_admin extends Fragment {
    public static String KEY_FRG;
    public Tampilan_admin(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        String msg=getArguments().getString(KEY_FRG);
        if(msg != null && msg !=""){

        }

        return inflater.inflate(R.layout.fragment_tampilan_admin,container,false);

    }
}

