package com.example.austine.login;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity{

    //All declaration made here
    private EditText email,pass;
    private Button val,val2;
    private String eval,pval;
    private ProgressDialog progress;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    SignInButton signinbtn;
    private final static int RC_SIGN_IN=1;
    GoogleApiClient mGoogleApiClient;
    private static final String TAG = "MainActivity";


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
           Log.d(TAG,"MainActivity Started.");

           //Attachment with the Layout

            email=(EditText)findViewById(R.id.editText);
            pass=(EditText)findViewById(R.id.editText2);
            val=(Button)findViewById(R.id.login);
            val2=(Button)findViewById(R.id.signup_btn);

            progress=new ProgressDialog(this);

            //For Login Button Click
            val.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                   userlogin();

                }
            });

            //For Sign Up Button Click
            val2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    startActivity(new Intent(MainActivity.this,Main3Activity.class));
                }
            });



            //Request sent to the authenticator of database
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

            //Google Signin Button attached to the layout
            signinbtn=(SignInButton)findViewById(R.id.googlebtn);
            mAuth=FirebaseAuth.getInstance();

            mAuthListner=  new FirebaseAuth.AuthStateListener() {
                @Override
                public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                    if(firebaseAuth.getCurrentUser()!=null)
                    {
                        Log.d(TAG,"Going To Second Activity 2");

                        startActivity(new Intent(MainActivity.this,Main2Activity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                    }
                }
            };

            //Implementation of Google SignIn Button
             signinbtn.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     signIn();

                 }
             });

             mGoogleApiClient = new GoogleApiClient.Builder(this).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
             @Override
             public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                Toast.makeText(MainActivity.this,"Something Went Wrong",Toast.LENGTH_LONG).show();
             }
         }).addApi(Auth.GOOGLE_SIGN_IN_API,gso).build();


    }
    //Implementation of Logining in user using Login Button
    private void userlogin()
    {
        Log.d(TAG,"Login Screen");
        eval=email.getText().toString().trim();
        pval=pass.getText().toString().trim();
        if(eval.isEmpty())
        {
            email.setError("Enter a valid Email ID");
            email.requestFocus();

        }
        else if(pval.isEmpty())
        {
            pass.setError("Enter a valid Password");
            pass.requestFocus();

        }
        //Login using login button
        else {
            progress.setMessage("Logging In User....");
            progress.show();
            mAuth.signInWithEmailAndPassword(eval,pval).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()) {
                        Log.d(TAG, "Going To Second Activity 1");

                        startActivity(new Intent(MainActivity.this, Main2Activity.class)
                                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                        finish();
                    }
                    else
                    {
                        progress.dismiss();
                        FragmentManager fm = getFragmentManager();
                        auth_dia testDialog1 = new auth_dia();
                        testDialog1.setRetainInstance(true);
                        testDialog1.show(fm, "fragment_name");
                    }

                }
            });

        }

    }


    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
        progress.setMessage("Logging In User....");
        progress.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(MainActivity.this,"Auth went WRONG", Toast.LENGTH_SHORT).show();
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG","signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG","signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this,"Auth Failed",Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }
}
