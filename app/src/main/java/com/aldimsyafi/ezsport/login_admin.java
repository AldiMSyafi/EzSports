package com.aldimsyafi.ezsport;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login_admin extends AppCompatActivity {
    private EditText userMail,userPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private Intent HomeActivity;
    private ProgressBar loginprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        userMail = findViewById(R.id.login_mail);
        userPassword = findViewById(R.id.login_password);
        btnLogin = findViewById(R.id.login_btn);
        loginprogress = findViewById(R.id.login_progress);
        mAuth = FirebaseAuth.getInstance();
        HomeActivity = new Intent(this,Menu_Utama_Admin.class);

        loginprogress.setVisibility(View.INVISIBLE);

        //fungsi tombol
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginprogress.setVisibility(View.INVISIBLE);
                btnLogin.setVisibility(View.VISIBLE);

                final String mail = userMail.getText().toString();
                final String password = userPassword.getText().toString();

                //validasi login
                if(mail.isEmpty()||password.isEmpty()){
                    showMessage("Lengkapi Semua Kolom");
                }
                else {

                    signin(mail,password);
                }
            }
        });

    }

    private void signin(String mail, String password) {

        //login menggunakan email dan password dari firebase
        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {


                if (task.isSuccessful()){

                    loginprogress.setVisibility(View.INVISIBLE);
                    btnLogin.setVisibility(View.VISIBLE);
                    updateUI();
                }
                else
                    showMessage(task.getException().getMessage());

            }
        });

    }

    private void updateUI() {

                startActivity(HomeActivity);
                finish();

    }

    private void showMessage(String text) {
        Toast.makeText(getApplicationContext(),text,Toast.LENGTH_LONG).show();
    }

    //agar user langsung login ketika masuk
    /*@Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= mAuth.getCurrentUser();

        if (user !=null){
            updateUI();
        }
    }*/
}
