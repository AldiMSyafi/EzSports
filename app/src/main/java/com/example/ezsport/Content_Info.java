package com.example.ezsport;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Content_Info extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton mSelectImage_info;
    private EditText mPostTitle_info;
    private EditText mPostDesc_info;
    private Button mSubmitBtn_info;
    private StorageReference mStorageRef_info;
    private DatabaseReference mDatabaseref_info;
    private ProgressBar mProgressBar_info;
    private StorageTask mUploadTask_info;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content__info);

        mSelectImage_info = findViewById(R.id.imageSelect_info);
        mPostTitle_info = findViewById(R.id.titleField_info);
        mPostDesc_info = findViewById(R.id.descField_info);
        mProgressBar_info = findViewById(R.id.progressBar_info);
        mSubmitBtn_info = findViewById(R.id.submitButton_info);

        mStorageRef_info = FirebaseStorage.getInstance().getReference("info_tournament");
        mDatabaseref_info = FirebaseDatabase.getInstance().getReference("info_tournament");

        mSelectImage_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });

        mSubmitBtn_info.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask_info != null && mUploadTask_info.isInProgress()) {
                    Toast.makeText(Content_Info.this, "sedang upload", Toast.LENGTH_SHORT).show();
                } else {
                    uploadfile();
                }
            }
        });
    }

    private void uploadfile() {
        if (mImageUri != null) {
            final StorageReference fileReferences = mStorageRef_info.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask_info = fileReferences.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar_info.setProgress(0);
                        }
                    }, 500);
                    Toast.makeText(Content_Info.this, "Upload berhasil", Toast.LENGTH_LONG).show();
                    Upload_Info upload = new Upload_Info(mPostTitle_info.getText().toString(),
                            mPostDesc_info.getText().toString(), taskSnapshot.getStorage().getDownloadUrl().toString());
                    String uploadid = mDatabaseref_info.push().getKey();
                    mDatabaseref_info.child(uploadid).setValue(upload);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Content_Info.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar_info.setProgress((int) progress);
                        }
                    });

        }
        else {
            Toast.makeText(Content_Info.this, "File tidak di temukan", Toast.LENGTH_SHORT).show();

        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR2 = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR2.getType(uri));
    }

    private void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).fit().into(mSelectImage_info);
        }
    }
}
