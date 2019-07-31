package com.example.ezsport;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class Content_Artikel extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageButton mSelectImage;
    private EditText mPostTitle;
    private EditText mPostDesc;
    private Button mSubmitBtn;
    private StorageReference mstorageRef;
    private DatabaseReference mDatabaseref;
    private ProgressBar mProgressBar;
    private StorageTask mUploadTask;
    private Uri mImageUri;

    //set layout yang akan di tampilakn
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content__artikel);

        //memanggil id
        mSelectImage = findViewById(R.id.imageSelect_artikel);
        mPostTitle = findViewById(R.id.titleField_artikel);
        mPostDesc = findViewById(R.id.descField_artikel);
        mProgressBar = findViewById(R.id.progressBar2);
        mSubmitBtn = findViewById(R.id.submitButton_artikel);

        mstorageRef = FirebaseStorage.getInstance().getReference("Article");
        mDatabaseref = FirebaseDatabase.getInstance().getReference("Article");


        mSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFilechoser();

            }
        });

        mSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Content_Artikel.this, "Sedang Mengupload Gambar", Toast.LENGTH_SHORT).show();
                } else {
                    startPosting();

                }


            }
        });
    }


    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));


    }

    //upload gambar,title,deskripsi ke database
    private void startPosting() {


        if (mImageUri != null) {
            StorageReference filereferences = mstorageRef.child(System.currentTimeMillis() + "." + getFileExtension(mImageUri));

            mUploadTask = filereferences.putFile(mImageUri)
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
                                //delay progress bar
                            }, 500);

                            //ngirim data ke realtime database firebase
                            Toast.makeText(Content_Artikel.this, "berhasil di simpan", Toast.LENGTH_LONG).show();
                            Upload_Info upload = new Upload_Info(mPostTitle.getText().toString(), mPostDesc.getText().toString(),
                            downloadurl.toString());

                            String uploadId = mDatabaseref.push().getKey();
                            mDatabaseref.child(uploadId).setValue(upload);

                    //saat gagal ada pemberitahuan gagal
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Content_Artikel.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                        //saat berhasil progress bar akan jalan
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            showMessage("isi semua field");
        }
    }

    private void showMessage(String message) {
        Toast.makeText(Content_Artikel.this, message, Toast.LENGTH_LONG).show();
    }

    //mengambil gambar di galeri
    private void openFilechoser() {


        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    //mengambil gambar dari storage hp
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            mImageUri = data.getData();

            Picasso.get().load(mImageUri).fit().into(mSelectImage);
            mSelectImage.setImageURI(mImageUri);
        }
    }
}


