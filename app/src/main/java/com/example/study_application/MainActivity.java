package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    //text variables
    EditText mEmail;
    EditText mPassword;
    String mEmail_string,mPassword_string;

    //button variables
    Button login_button;

    //firebase variables
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button = findViewById(R.id.login_button);
        login_button.setTransformationMethod(null);
        mEmail = findViewById(R.id.emailAddressText);
        mPassword = findViewById(R.id.passwordText);
        mAuth = FirebaseAuth.getInstance();

    }

    public void signIn(View v){
        attemptLogin();
    }

    private void attemptLogin(){
        mEmail_string = mEmail.getText().toString();
        mPassword_string = mPassword.getText().toString();

        if ((!isEmailValid(mEmail_string) || (!isPasswordValid(mPassword_string)))){
            mPassword.setError("password is to short try again");
            mEmail.requestFocus();
        } else {
            loginFirebaseUser();
        }
    }

    private boolean isEmailValid(String email){
        return email.contains("@") && email.length()>3;
    }

    private boolean isPasswordValid(String password){
        return password.length()>6;
    }

    private void loginFirebaseUser(){
        Intent intent = new Intent(this, HomeScreen.class);
        mAuth.signInWithEmailAndPassword(mEmail_string,mPassword_string).addOnCompleteListener(task -> {

            if (!task.isSuccessful()){
                Log.d("firebase", "User login failed");
            } else {
                Log.d("firebase", "User login Complete");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
            }
        });
    }


    public void toRegisterScreen(View aView){
        Intent intent = new Intent(this, RegisterScreen.class);
        finish();
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in_bottom, R.anim.slide_out_top);
    }
}