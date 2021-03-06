package com.project.reloaded.zeus;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupPage extends AppCompatActivity implements View.OnClickListener{


   private Button buttonRegister;
   private EditText editTextEmail;
   private EditText editTextPassword;

   private FirebaseAuth firebaseAuth;
   private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN );//for running the activities in full screen mode

        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.Email2);
        editTextPassword = (EditText) findViewById(R.id.psswrd);

        buttonRegister = (Button) findViewById(R.id.btn);


        buttonRegister.setOnClickListener(this);
    }


    private void sendEmailVerification(){
        user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(SignupPage.this, "Email Sent check mail",Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(SignupPage.this,"Email not found",Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }

    private void registerUser(){
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            //email is empty
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show();

            return;
        }

        if(TextUtils.isEmpty(password)){
            //Password is empty
            Toast.makeText(this,"Please enter the password", Toast.LENGTH_LONG).show();

            return;
        }
        if(password.length()<=6){
            Toast.makeText(this,"Password Too Short, enter minimum 6 characters",Toast.LENGTH_LONG).show();
            finish();
        }

        firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(SignupPage.this,"Registered successfully",Toast.LENGTH_SHORT).show();
                    sendEmailVerification();
                }
                else{
                    Toast.makeText(SignupPage.this, "Please type a valid email ID",Toast.LENGTH_SHORT).show();
                }
            }
        }


        );



    }


    @Override
    public void onClick(View view) {
        if(view == buttonRegister){
            registerUser();
        }


    }
}
