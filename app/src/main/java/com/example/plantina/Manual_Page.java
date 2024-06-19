package com.example.plantina;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.google.firebase.storage.FirebaseStorage;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Manual_Page extends AppCompatActivity{
    private EditText txt_ph, txt_moisture, txt_temprature, txt_sensoridmanual;
    Button confirm;


    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manual_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sensor Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Sensor");
        //databaseReference = FirebaseDatabase.getInstance().getReference("Sensor");

        txt_ph = findViewById(R.id.phvalue);
        txt_moisture = findViewById(R.id.moisturevalue);
        txt_temprature = findViewById(R.id.tempratureValue);
        txt_sensoridmanual = findViewById(R.id.sensoridmanual);

        confirm = findViewById(R.id.confirmButton);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ManualDataSave();
            }
        });

    }

    private void ManualDataSave() {
        final String ph = txt_ph.getText().toString().trim();
        final String moisture = txt_moisture.getText().toString().trim();
        final String temperature = txt_temprature.getText().toString().trim();
        final String sensorid = txt_sensoridmanual.getText().toString().trim();

        if (ph.isEmpty()) {
            txt_ph.setError("Please Enter pH value");
            txt_ph.requestFocus();
            return;
        }
        else if (moisture.isEmpty()) {
            txt_moisture.setError("Please Enter Moisture Value");
            txt_moisture.requestFocus();
            return;
        }
        else if (temperature.isEmpty()) {
            txt_temprature.setError("Please Enter Temprature");
            txt_temprature.requestFocus();
            return;
        }
        else if (sensorid.isEmpty()) {
            txt_sensoridmanual.setError("Please Enter ID Number for Sensor");
            txt_sensoridmanual.requestFocus();
            return;
        }
        else {

            final String data=txt_sensoridmanual.getText().toString();
            Query query = databaseReference.orderByChild("sensorid").equalTo(data);

            query.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.getChildrenCount()>0){
                        Toast.makeText(Manual_Page.this, "Please select different id", Toast.LENGTH_SHORT).show();
                    }else{

                        String name = user.getEmail();

                        Sensor sensor = new Sensor(ph, moisture, temperature,data,name);

                        databaseReference.child(sensorid).setValue(sensor);

                        Toast.makeText(Manual_Page.this, "Values confirmed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Manual_Page.this,Connecting_Page.class));
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(Manual_Page.this, ""+databaseError, Toast.LENGTH_SHORT).show();

                }
            });

        }
    }
}
