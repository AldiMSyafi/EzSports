package com.example.ezsport.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ezsport.Adapter.ImageAdapter_info;
import com.example.ezsport.HomeActivity;
import com.example.ezsport.R;
import com.example.ezsport.Upload_Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Csgo extends Fragment {
    private RecyclerView mRecyclerView1;
    private ImageAdapter_info mAdapter;
    private ProgressBar mProgressCircle;

    private DatabaseReference mDatabaseRef;
    private StorageReference mStorageRef;
    private List<Upload_Info> mUploads;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_images_csgo, container, false);
        ((HomeActivity)getActivity()).setActionBarTitle("Csgo");
        mProgressCircle = (ProgressBar) view.findViewById(R.id.progress_circle2);
        mRecyclerView1 =  view.findViewById(R.id.recycler_view2);
        mRecyclerView1.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView1.setHasFixedSize(true);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Csgo");

        return view;
    }
    public void onStart() {

        super.onStart();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads = new ArrayList<>();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

                    Upload_Info upload = postSnapshot.getValue(com.example.ezsport.Upload_Info.class);
                    mUploads.add(upload);
                }
                mAdapter = new ImageAdapter_info(getActivity(),mUploads);
                mRecyclerView1.setAdapter(mAdapter);

                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                mProgressCircle.setVisibility(View.INVISIBLE);
            }
        });
    }
}
