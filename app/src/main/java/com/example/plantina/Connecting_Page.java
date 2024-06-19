package com.example.plantina;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Connecting_Page extends AppCompatActivity{
private ProgressBar progressBar;
private TextView progressText;
private TextView connectingText;
private  TextView connectedText, manualdataentry;
private EditText sensorvalues;
Button next;
int i = 0;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connecting_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Sensor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Sensor");

        progressBar = findViewById(R.id.progress_bar);
        progressText = findViewById(R.id.progress_text);
        connectingText = findViewById(R.id.connecting);
        connectedText = findViewById(R.id.connected);

        next = findViewById(R.id.nextbutton);
        manualdataentry = findViewById(R.id.manualdataentry);


        sensorvalues = findViewById(R.id.sensoridvalue);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String id = sensorvalues.getText().toString();
                Query query = databaseReference.orderByChild("sensorid").equalTo(id);
                query.addValueEventListener(new ValueEventListener() {
                    String email;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getChildrenCount() == 0) {
                            Toast.makeText(Connecting_Page.this, "Sorry wrong id! Please check again", Toast.LENGTH_SHORT).show();
                        } else {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                email = "" + ds.child("email").getValue();
                            }
                            if (email.equals(user.getEmail())) {

                                final String SensorValue= sensorvalues.getText().toString().trim();
                                if(TextUtils.isEmpty(SensorValue)){
                                    sensorvalues.setError("Sensor ID Required");
                                    sensorvalues.requestFocus();
                                    return;
                                }else{

                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            if(i<=100){
                                                progressText.setText(i + "%");
                                                progressBar.setProgress(i);
                                                i++;
                                                handler.postDelayed(this, 80);
                                                if(i<100) {
                                                    connectingText.setText("connecting");
                                                }
                                                if(i==100){
                                                    connectingText.setText(null);
                                                    connectedText.setText("Connected");

                                                    Intent intent =new Intent(getApplicationContext(), Plant_Details_Page.class);
                                                    intent.putExtra("ID", sensorvalues.getText().toString());

                                                    startActivity(intent);

                                                }
                                            }else{
                                                connectingText.setText(null);
                                                handler.removeCallbacks(this);

                                            }

                                        }
                                    },180);
                                }
                            } else {
                                Toast.makeText(Connecting_Page.this, "Sorry please check your sensor id", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(Connecting_Page.this, ""+databaseError, Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });


        manualdataentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sensorvalues.setText(null);
                startActivity(new Intent(Connecting_Page.this,Manual_Page.class));
            }
        });
    }

//    @Override
//    public void onClick(View v) {
//        switch (v.getId())
//        {
//            case R.id.nextbutton :
//                startActivity(new Intent(this, Soil_Details_Page.class));
//                finish();
//                break;
//
//        }
//    }
}
