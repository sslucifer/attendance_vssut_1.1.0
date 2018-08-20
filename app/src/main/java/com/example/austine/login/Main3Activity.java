package com.example.austine.login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Main3Activity extends AppCompatActivity {
    AutoCompleteTextView euser;
    EditText puser;
    Button sign;
    String eval,pval;
    ProgressDialog progress;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        getSupportActionBar().setTitle("Sign Up");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAuth=FirebaseAuth.getInstance();
        progress = new ProgressDialog(this);
        euser=(AutoCompleteTextView)findViewById(R.id.email);
        puser=(EditText) findViewById(R.id.password);
        sign=(Button)findViewById(R.id.signup);
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });
    }
    private void registeruser()
    {
        eval=euser.getText().toString().trim();
        pval=puser.getText().toString().trim();
        if(eval.isEmpty())
        {
            euser.setError("Enter a valid Email ID");
            euser.requestFocus();

        }
        else if(pval.isEmpty())
        {
            puser.setError("Enter a valid Password");
            puser.requestFocus();

        }
        else {
            progress.setMessage("Registering User....");
            progress.show();
            mAuth.createUserWithEmailAndPassword(eval,pval).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        progress.dismiss();
                        finish();
                        startActivity(new Intent(Main3Activity.this,Main2Activity.class));
                        Toast.makeText(getApplicationContext(),"Database will be updated shortly!!",Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        progress.dismiss();
                        Toast.makeText(getApplicationContext(),"Registeration Failed",Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }
    }
}
