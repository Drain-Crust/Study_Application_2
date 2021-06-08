package com.example.study_application;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    int RC_SIGN_IN = 0; //declear variable

    //button variables
    SignInButton login_button;
    GoogleSignInClient mGoogleSignInClient;

    //firebase variables
    FirebaseAuth mAuth;

    //different intents to go to different pages
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login_button = findViewById(R.id.login_button);
        mAuth = FirebaseAuth.getInstance();

        intent = new Intent(this, HomeScreen.class);

        login_button.setOnClickListener(v -> {
            if (v.getId() == R.id.login_button) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)// these lines are from google, they directed the integration
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    public void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();//intent from google client
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) { //check if RC_SIGN_IN is correct
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult();
        }
    }

    private void handleSignInResult() {

        // Signed in successfully, show authenticated UI.
        //updateUI(account); replaced
        startActivity(intent);
    }
}
