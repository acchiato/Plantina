package com.example.plantina;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class Myaccount_Page extends AppCompatActivity {

    TextView name,username,email,age;
    Button newsbutton;

    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

   // ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccount_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("My Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

       // progressDialog.show();

        name = findViewById(R.id.namedisplayval);
        username = findViewById(R.id.usernamedisplayval);
        email = findViewById(R.id.emaildisplayval);
        age = findViewById(R.id.agedisplayval);
        newsbutton = findViewById(R.id.newsbutton);

        Query query = databaseReference.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    String Name = "" + ds.child("name").getValue();
                    String Username = "" + ds.child("username").getValue();
                    String Email = "" + ds.child("email").getValue();
                    String Age = "" + ds.child("age").getValue();

                    //progressDialog.dismiss();

                    name.setText(""+Name);
                    username.setText(""+Username);
                    email.setText(""+Email);
                    age.setText(""+Age);

                    Toast.makeText(Myaccount_Page.this, "Successful", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Myaccount_Page.this, ""+databaseError, Toast.LENGTH_SHORT).show();
            }
        });

        newsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Myaccount_Page.this,News_Add_Page.class ));
            }
        });

    }
}
