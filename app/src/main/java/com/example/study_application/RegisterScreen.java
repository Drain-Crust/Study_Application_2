package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterScreen extends AppCompatActivity {

    //variables
    private EditText mConfirmPasswordView,mPasswordView,mEmailView;

    //firebase instance variables
    private FirebaseAuth mAuth;

    //variables
    String email,password;

    //the next screen
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        mEmailView = findViewById(R.id.registerEmail);
        mPasswordView = findViewById(R.id.registerPassword);
        mConfirmPasswordView = findViewById(R.id.registerConfirmation);
        mAuth = FirebaseAuth.getInstance();

        intent = new Intent(this, MainActivity.class);

        // Keyboard sign in action
        mConfirmPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> {
            if (id == 200 || id == EditorInfo.IME_NULL) {
                attemptRegistration();
                return true;
            }
            return false;
        });
    }


    public void signUp(View v) {
        attemptRegistration();
    }

    private void attemptRegistration(){
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();

        if (isEmailValid(email) || isPasswordValid(password)){
            if (isEmailValid(email)){
                mEmailView.setError("password is to short try again");
            }
            if (isPasswordValid(password)){
                mPasswordView.setError("password is to short try again");
            }
        } else {
            createFirebaseUser();
        }
    }

    private boolean isEmailValid(String email){
        return !email.contains("@") || email.length() <= 3;
    }

    private boolean isPasswordValid(String password){
        String confirmPassword = mConfirmPasswordView.getText().toString();
        return !confirmPassword.equals(password) || password.length() <= 6;
    }

    private void createFirebaseUser(){
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, task -> {
            if (!task.isSuccessful()){
                Log.d("firebase", "User creation failed");
                Toast.makeText(this, "User already registered", Toast.LENGTH_SHORT).show();
            } else {
                Log.d("firebase", "createUser onComplete");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_top, R.anim.slide_out_bottom);
            }
        });
    }



}