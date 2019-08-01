package com.aldimsyafi.ezsport.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.aldimsyafi.ezsport.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Admin extends Fragment{
    public static final String TAG = Admin.class.getSimpleName();
    public static String KEY_FRG = "msg_fragment";
    private EditText userMail,userPassword;
    private Button btnlogin;
    private FirebaseAuth mAuth;
    private Intent intent;
    private ProgressBar loginprogress;



    @Nullable
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_admin, container, false);



            userMail = (EditText) view.findViewById(R.id.login_mail);
            userPassword = (EditText) view.findViewById(R.id.login_password);
            btnlogin = (Button) view.findViewById(R.id.login_btn);
            loginprogress = (ProgressBar) view.findViewById(R.id.login_progress);
            mAuth = FirebaseAuth.getInstance();

        //validasi login
            View focusview = null;
            boolean cancleLogin = false;
             String userlogin = userMail.getText().toString();
             String userpass = userPassword.getText().toString();
             if(TextUtils.isEmpty(userlogin)){
                userMail.setError(getString(R.string.user_login));
                focusview = userMail;
                cancleLogin = true;
        }
        if(TextUtils.isEmpty(userpass)){
            userPassword.setError(getString(R.string.user_password));
            focusview = userMail;
            cancleLogin = true;
        }
        if (cancleLogin){
            focusview.requestFocus();

        }


        loginprogress.setVisibility(View.INVISIBLE);


            btnlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    loginprogress.setVisibility(View.VISIBLE);
                    btnlogin.setVisibility(View.INVISIBLE);

                    //fungsi button pindah fragment admin ke fragment tampilan admin

                    Bundle mBundle = new Bundle();




                    Tampilan_admin tampil = new Tampilan_admin();
                    Fragment fragment= new Tampilan_admin();
                    FragmentManager mFragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()
                            .replace(R.id.fragment_container,tampil,Tampilan_admin.class.getSimpleName());
                    fragmentTransaction.commit();
                    tampil.setArguments(mBundle);





                }

            });

        return inflater.inflate(R.layout.fragment_admin, container, false);



    }

    private boolean isValidPhone(String email){
        String EMAIL_PATERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATERN);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }





    private void signin(String mail,String password){

        mAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()){
                    loginprogress.setVisibility(View.INVISIBLE);
                    btnlogin.setVisibility(View.VISIBLE);
                    updateUI();
                }

            }
        });
    }
    private void updateUI(){


    }

    private void showMessage(String text){

        Toast.makeText(getContext(),text,Toast.LENGTH_LONG).show();


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

    }
}
