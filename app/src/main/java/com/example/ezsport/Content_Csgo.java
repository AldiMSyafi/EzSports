package com.example.ezsport;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class Content_Csgo extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton mSelectImage_csgo;
    private EditText mPostTitle_csgo;
    private EditText mPostDesc_csgo;
    private Button mSubmitBtn_csgo;
    private StorageReference mStorageRef_csgo;
    private DatabaseReference mDatabaseref_csgo;
    private ProgressBar mProgressBar_csgo;
    private StorageTask mUploadTask_csgo;
    private Uri mImageUri = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content__csgo);

        mSelectImage_csgo = findViewById(R.id.imageSelect_csgo);
        mPostTitle_csgo = findViewById(R.id.titleField_csgo);
        mPostDesc_csgo = findViewById(R.id.descField_csgo);
        mProgressBar_csgo = findViewById(R.id.progressBar2);
        mSubmitBtn_csgo = findViewById(R.id.submitButton_csgo);

        mStorageRef_csgo = FirebaseStorage.getInstance().getReference("Csgo");
        mDatabaseref_csgo = FirebaseDatabase.getInstance().getReference("Csgo");

        mSelectImage_csgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });
        mSubmitBtn_csgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask_csgo != null && mUploadTask_csgo.isInProgress()) {
                    Toast.makeText(Content_Csgo.this, "sedang upload", Toast.LENGTH_SHORT).show();
                } else {
                    uploadfile();
                }
            }
        });
    }

    private void uploadfile() {
        if (mImageUri != null) {
            final StorageReference fileReferences = mStorageRef_csgo.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask_csgo = fileReferences.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> urlTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!urlTask.isSuccessful());
                            Uri downloadurl = urlTask.getResult();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar_csgo.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Content_Csgo.this, "Upload berhasil", Toast.LENGTH_LONG).show();
                            Upload_Info upload = new Upload_Info(mPostTitle_csgo.getText().toString(),
                                    mPostDesc_csgo.getText().toString(), downloadurl.toString());
                            String uploadid = mDatabaseref_csgo.push().getKey();
                            mDatabaseref_csgo.child(uploadid).setValue(upload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Content_Csgo.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar_csgo.setProgress((int) progress);
                        }
                    });

        }
        else {
            Toast.makeText(Content_Csgo.this, "File tidak di temukan", Toast.LENGTH_SHORT).show();

        }

    }





    private void openfilechooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
}
private String getFileExtension(Uri uri) {

        ContentResolver cR2 = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR2.getType(uri));
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Picasso.get().load(mImageUri).fit().into(mSelectImage_csgo);
        }
    }

    }
