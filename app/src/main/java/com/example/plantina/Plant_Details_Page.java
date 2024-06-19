package com.example.plantina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Plant_Details_Page extends AppCompatActivity {
    ViewPager viewPager;
    List<Plant_Model> models;

    TextView moisture,ph,temperature,moisturedata,phdata,temperaturedata, bestimagetext, predictedsoil;
    ImageView bestimage;
    Button soilpredictedbutton;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference, databaseReference1;

    Bundle bundle;
    TextView seeall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plant_details_page);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Plant Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Sensor");

        databaseReference1 = FirebaseDatabase.getInstance().getReference().child("Plants");

        moisture = findViewById(R.id.moisture);
        moisturedata = findViewById(R.id.moisturedata);
        ph = findViewById(R.id.ph);
        phdata = findViewById(R.id.phdata);
        temperature = findViewById(R.id.temperature);
        temperaturedata = findViewById(R.id.temperaturedata);
        soilpredictedbutton=findViewById(R.id.soilpredictionbutton);

        bestimage =  findViewById(R.id.bestimage);
        bestimagetext =  findViewById(R.id.bestimagetext);

        seeall = findViewById(R.id.textView3);

        bundle = getIntent().getExtras();
        final String soil = bundle.getString("Result");


        seeall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Plant_Details_Page.this, Plants_Page.class);
                intent.putExtra("Soil", soil);
                startActivity(intent);

            }
        });

        soilpredictedbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Plant_Details_Page.this, Soil_Prediction.class));
            }
        });

        bundle = getIntent().getExtras();
        String data = bundle.getString("ID");

            Query query = databaseReference.orderByChild("sensorid").equalTo(data);
        bundle.clear();
            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        String Moisture = "" + ds.child("moisture").getValue();
                        String pH = "" + ds.child("ph").getValue();
                        String Temperature = "" + ds.child("temprature").getValue();

                        moisture.setText("Moisture");
                        moisturedata.setText(""+Moisture);
                        ph.setText("pH");
                        phdata.setText(""+pH);
                        temperature.setText("Temperature ");
                        temperaturedata.setText(""+Temperature);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Plant_Details_Page.this, ""+databaseError, Toast.LENGTH_SHORT).show();
                }
            });


        //Query query2 = databaseReference1.orderByChild("Name");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds2 : dataSnapshot.getChildren()) {
                    String Image = "" + ds2.child("Image").getValue();
                    Picasso.get().load(Image).into(bestimage);
                    String Name = "" + ds2.child("Name").getValue();

                        bestimagetext.setText(""+Name);

                    Toast.makeText(Plant_Details_Page.this, "Successful", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Plant_Details_Page.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });
    }
    }
