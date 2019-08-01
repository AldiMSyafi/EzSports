package com.aldimsyafi.ezsport.Content;

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

import com.aldimsyafi.ezsport.R;
import com.aldimsyafi.ezsport.Upload_Info;
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
public class Content_Dota extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton mSelectImage_dota;
    private EditText mPostTitle_dota;
    private EditText mPostDesc_dota;
    private Button mSubmitBtn_dota;
    private StorageReference mstorageRef_dota;
    private DatabaseReference mDatabaseref_dota;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask_dota;
    private Uri mImageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content__dota);

        mSelectImage_dota = findViewById(R.id.imageSelect_dota);
        mPostTitle_dota = findViewById(R.id.titleField_dota);
        mPostDesc_dota = findViewById(R.id.descField_dota);
        mProgressBar = findViewById(R.id.progressBar2);
        mSubmitBtn_dota = findViewById(R.id.submitButton_dota);

        mstorageRef_dota = FirebaseStorage.getInstance().getReference("Dota");
        mDatabaseref_dota = FirebaseDatabase.getInstance().getReference("Dota");

        mSelectImage_dota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openfilechooser();
            }
        });
        mSubmitBtn_dota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask_dota != null && mUploadTask_dota.isInProgress()) {
                    Toast.makeText(Content_Dota.this, "sedang upload", Toast.LENGTH_SHORT).show();
                } else {
                    uploadfile();
                }
            }
        });

    }

    private void uploadfile() {
        if (mImageUri != null) {
            final StorageReference fileReferences = mstorageRef_dota.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            mUploadTask_dota = fileReferences.putFile(mImageUri)
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
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);
                            Toast.makeText(Content_Dota.this, "Upload berhasil", Toast.LENGTH_LONG).show();
                            Upload_Info upload = new Upload_Info(mPostTitle_dota.getText().toString(),
                                    mPostDesc_dota.getText().toString(), downloadurl.toString());
                            String uploadid = mDatabaseref_dota.push().getKey();
                            mDatabaseref_dota.child(uploadid).setValue(upload);

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Content_Dota.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });

        }
        else {
            Toast.makeText(Content_Dota.this, "File tidak di temukan", Toast.LENGTH_SHORT).show();

        }

    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR2 = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR2.getType(uri));
    }


    //mengambil gambar dari storage handpone
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
            Picasso.get().load(mImageUri).fit().into(mSelectImage_dota);
        }
    }
}
