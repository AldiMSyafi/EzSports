package com.aldimsyafi.ezsport.Fragment;

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

import com.aldimsyafi.ezsport.Adapter.imageAdapter;
import com.aldimsyafi.ezsport.HomeActivity;
import com.aldimsyafi.ezsport.R;
import com.aldimsyafi.ezsport.Upload_Info;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class Article extends Fragment {
        private RecyclerView mRecyclerView;
        private imageAdapter mAdapter;
        private ProgressBar mProgressCircle;

        private DatabaseReference mDatabaseRef;
        private StorageReference mStorageRef;
        private List<Upload_Info> mUploads1;

    public Article() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_images, container, false);
        ((HomeActivity)getActivity()).setActionBarTitle("Article");

        mProgressCircle = (ProgressBar) view.findViewById(R.id.progress_circle);
        mRecyclerView =  view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mRecyclerView.setHasFixedSize(true);


        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Article");
        return view;
         }
         public void onStart()
    {
        super.onStart();
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUploads1 = new ArrayList<>();
             for(DataSnapshot postSnapshot : dataSnapshot.getChildren()){

               Upload_Info upload = postSnapshot.getValue(com.aldimsyafi.ezsport.Upload_Info.class);
                mUploads1.add(upload);
            }
                mAdapter = new imageAdapter(getActivity(),mUploads1);
                mRecyclerView.setAdapter(mAdapter);

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
