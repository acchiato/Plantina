package com.example.plantina;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Soil_Details_Page extends AppCompatActivity {
    private RecyclerView recyclerView;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Query firebase;
    ProgressDialog progressDialog;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.soil_details_page);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Soil Suggestions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Soils");

        bundle = getIntent().getExtras();
        String predict = bundle.getString("Prediction");

        firebase = databaseReference.orderByChild("plant").equalTo(predict);

        databaseReference.keepSynced(true);

        progressDialog.show();
        recyclerView = findViewById(R.id.myrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Soil_Model, MyViewHolder> firebaseRecyclerAdapter= new FirebaseRecyclerAdapter<Soil_Model, MyViewHolder>
                (Soil_Model.class, R.layout.recyclerview_helper,MyViewHolder.class,firebase) {

            protected void populateViewHolder(MyViewHolder myViewHolder, Soil_Model model, int i) {
                myViewHolder.setName(model.getName());
                myViewHolder.setPh(model.getPh());
                myViewHolder.setTemperature(model.getTemperature());
                myViewHolder.setNote(model.getNote());
                progressDialog.dismiss();


            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            view=itemView;
        }
        public void setName(String name)
        {
            TextView trackno = view.findViewById(R.id.soilname);
            trackno.setText(name);
        }
        public void setPh(String ph)
        {
            TextView from = view.findViewById(R.id.phnameval);
            from.setText(ph);
        }
        public void setTemperature(String temperature)
        {
            TextView to = view.findViewById(R.id.temperaturenameval);
            to.setText(temperature);
        }
        public void setNote(String note)
        {
            TextView sheduledate = view.findViewById(R.id.description);
            sheduledate.setText(note);
        }
    }
}
