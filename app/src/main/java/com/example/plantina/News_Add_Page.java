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

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class News_Add_Page extends AppCompatActivity {
    private EditText heading,description;
    Button newsaddbutton;

    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase;
    FirebaseUser user;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_add_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Add News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("News");

        heading = findViewById(R.id.headingtxt);
        description = findViewById(R.id.editText);
        newsaddbutton = findViewById(R.id.newsaddbutton);

        newsaddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDataSave();
            }
        });

    }

    private void NewsDataSave() {
        final String Head = heading.getText().toString().trim();
        final String Description= description.getText().toString().trim();

        if (Head.isEmpty()) {
            heading.setError("Please Enter news heading value");
            heading.requestFocus();
            return;
        }
        else if (Description.isEmpty()) {
            description.setError("Please add description of the news");
            description.requestFocus();
            return;
        }
        else {
            String name = user.getEmail();
            String id = databaseReference.push().getKey();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            String currentDate = sdf.format(new Date());
            News_Model news = new News_Model(Head, Description, currentDate);

            databaseReference.child(id).setValue(news);

            Toast.makeText(News_Add_Page.this, "Values confirmed", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(News_Add_Page.this,Home_Page.class));

        }
    }
}
