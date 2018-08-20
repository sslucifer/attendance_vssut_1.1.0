package com.example.austine.login;

import android.app.ActionBar;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.logging.LogRecord;

public class Main2Activity extends AppCompatActivity {


    private Button logoutbtn,dialogbtn;
    private TextView nameofstud, t1, t2, t3, t4, t5, p1, p2, p3, p4, p5, a1, a2, a3, a4, a5,alert;
    private TextView sb1,sb2,sb3,sb4,sb5,ludate;
    private ProgressDialog progress;
    private float cal1,cal2,cal3,cal4,cal5;
    private String percentage, user, uname;
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference mref = database.getReference();
    UserInformation uinfo = new UserInformation();

    final Context context = this;

    private String udate;
    private String rname;
    private String rTsub1;
    private String rTsub2;
    private String rTsub3;
    private String rTsub4;
    private String rTsub5;
    private String rAsub1;
    private String rAsub2;
    private String rAsub3;
    private String rAsub4;
    private String rAsub5;

    private String sbt1;
    private String sbt2;
    private String sbt3;
    private String sbt4;
    private String sbt5;

    private static final String TAG = "ViewDatabase";

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data);


        Log.d(TAG,"Showing Data");


        logoutbtn = (Button) findViewById(R.id.logout);
        dialogbtn=(Button)findViewById(R.id.dialog);


        nameofstud = (TextView) findViewById(R.id.name);
        t1 = (TextView) findViewById(R.id.st1);
        t2 = (TextView) findViewById(R.id.st2);
        t3 = (TextView) findViewById(R.id.st3);
        t4 = (TextView) findViewById(R.id.st4);
        t5 = (TextView) findViewById(R.id.st5);

        p1 = (TextView) findViewById(R.id.sp1);
        p2 = (TextView) findViewById(R.id.sp2);
        p3 = (TextView) findViewById(R.id.sp3);
        p4 = (TextView) findViewById(R.id.sp4);
        p5 = (TextView) findViewById(R.id.sp5);

        sb1= (TextView) findViewById(R.id.s1);
        sb2= (TextView) findViewById(R.id.s2);
        sb3= (TextView) findViewById(R.id.s3);
        sb4= (TextView) findViewById(R.id.s4);
        sb5= (TextView) findViewById(R.id.s5);

        ludate= (TextView) findViewById(R.id.ldate);

        alert = (TextView) findViewById(R.id.alert);

        a1 = (TextView) findViewById(R.id.sa1);
        a2 = (TextView) findViewById(R.id.sa2);
        a3 = (TextView) findViewById(R.id.sa3);
        a4 = (TextView) findViewById(R.id.sa4);
        a5 = (TextView) findViewById(R.id.sa5);

        getSupportActionBar().setTitle("Signed In");


        progress = new ProgressDialog(this);


        mAuth = FirebaseAuth.getInstance();

        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    progress.setMessage("Logging Out User....");
                    progress.show();
                    finish();
                    startActivity(new Intent(Main2Activity.this, MainActivity.class));
                }
            }
        };


        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progress.setMessage("Logging Out User....");
                progress.show();
                mAuth.signOut();
                finish();
                Log.d(TAG,"Going To First Activity");

                startActivity(new Intent(Main2Activity.this,MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
                finish();

            }
        });
        Log.d(TAG,"Retriving User Data");
        user = mAuth.getUid();

        mref.child("Attendence").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                //for (DataSnapshot ds : dataSnapshot.getChildren())
                {

                    Log.d(TAG,"User Data Retrived");



                     udate= dataSnapshot.child("UDate").getValue(String.class);

                     rAsub1= dataSnapshot.child(user).child("Asub1").getValue(String.class);
                     rAsub2= dataSnapshot.child(user).child("Asub2").getValue(String.class);
                     rAsub3= dataSnapshot.child(user).child("Asub3").getValue(String.class);
                     rAsub4= dataSnapshot.child(user).child("Asub4").getValue(String.class);
                     rAsub5= dataSnapshot.child(user).child("Asub5").getValue(String.class);
                     rname= dataSnapshot.child(user).child("Name").getValue(String.class);
                     rTsub1= dataSnapshot.child("Total").child("Tsub1").getValue(String.class);
                     rTsub2= dataSnapshot.child("Total").child("Tsub2").getValue(String.class);
                     rTsub3= dataSnapshot.child("Total").child("Tsub3").getValue(String.class);
                     rTsub4= dataSnapshot.child("Total").child("Tsub4").getValue(String.class);
                    progress.setMessage("Loading Data..");
                    progress.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            progress.hide();
                        }
                    }, 1000);
                     rTsub5= dataSnapshot.child("Total").child("Tsub5").getValue(String.class);
                     sbt1= dataSnapshot.child("Subject").child("sub1").getValue(String.class);
                     sbt2= dataSnapshot.child("Subject").child("sub2").getValue(String.class);
                     sbt3= dataSnapshot.child("Subject").child("sub3").getValue(String.class);
                     sbt4= dataSnapshot.child("Subject").child("sub4").getValue(String.class);
                     sbt5= dataSnapshot.child("Subject").child("sub5").getValue(String.class);



                }
                Log.d(TAG,"Name: "+rname);
                Log.d(TAG,"Asub1: "+rAsub1);
                Log.d(TAG,"Asub2: "+rAsub2);
                Log.d(TAG,"Asub3: "+rAsub3);
                Log.d(TAG,"Asub4: "+rAsub4);
                Log.d(TAG,"Asub5: "+rAsub5);
                Log.d(TAG,"Tsub1: "+rTsub1);
                Log.d(TAG,"Tsub2: "+rTsub2);
                Log.d(TAG,"Tsub3: "+rTsub3);
                Log.d(TAG,"Tsub4: "+rTsub4);
                Log.d(TAG,"Tsub5: "+rTsub5);



                Log.d(TAG,"Displaying User Data");

                ludate.setText("Last Updated: "+udate);

                sb1.setText(sbt1);
                sb2.setText(sbt2);
                sb3.setText(sbt3);
                sb4.setText(sbt4);
                sb5.setText(sbt5);
                float dif;
                nameofstud.setText(rname);
                t1.setText(rTsub1);
                dif=Float.parseFloat(rTsub1)-Float.parseFloat(rAsub1);
                if(dif<10)
                    p1.setText(String.format("0"+"%.0f",dif));
                else
                    p1.setText(String.format("%.0f",dif));
                cal1 = (dif /Float.parseFloat(rTsub1)) * 100;
                Log.d(TAG,String.format("Cal_1: %.2f",cal1));
                a1.setText(String.format("%.2f",cal1));
                t2.setText(rTsub2);
                dif=Float.parseFloat(rTsub2)-Float.parseFloat(rAsub2);
                if(dif<10)
                    p2.setText(String.format("0"+"%.0f",dif));
                else
                    p2.setText(String.format("%.0f",dif));
                cal2 = (dif /Float.parseFloat(rTsub2)) * 100;
                Log.d(TAG,String.format("Cal_2: %.2f",cal2));
                a2.setText(String.format("%.2f",cal2));
                t3.setText(rTsub3);
                dif=Float.parseFloat(rTsub3)-Float.parseFloat(rAsub3);
                if(dif<10)
                    p3.setText(String.format("0"+"%.0f",dif));
                else
                    p3.setText(String.format("%.0f",dif));
                cal3 = (dif /Float.parseFloat(rTsub3)) * 100;
                Log.d(TAG,String.format("Cal_3: %.2f",cal3));
                a3.setText(String.format("%.2f",cal3));
                t4.setText(rTsub4);
                dif=Float.parseFloat(rTsub4)-Float.parseFloat(rAsub4);
                if(dif<10)
                    p4.setText(String.format("0"+"%.0f",dif));
                else
                    p4.setText(String.format("%.0f",dif));
                cal4 = (dif /Float.parseFloat(rTsub4)) * 100;
                Log.d(TAG,String.format("Cal_4: %.2f",cal4));
                a4.setText(String.format("%.2f",cal4));
                t5.setText(rTsub5);
                dif=Float.parseFloat(rTsub5)-Float.parseFloat(rAsub5);
                if(dif<10)
                    p5.setText(String.format("0"+"%.0f",dif));
                else
                    p5.setText(String.format("%.0f",dif));
                cal5 = (dif /Float.parseFloat(rTsub5)) * 100;
                Log.d(TAG,String.format("Cal_5: %.2f",cal5));
                a5.setText(String.format("%.2f",cal5));
                if(cal1<=75.00||cal2<=75.00||cal3<=75.00||cal4<=75.00||cal5<=75.00)
                {
                    alert.setText("Alert!!! Atendence Shortage");
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dialogbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                dia testDialog = new dia();
                testDialog.setRetainInstance(true);
                testDialog.show(fm, "fragment_name");
            }
        });

    }
}



