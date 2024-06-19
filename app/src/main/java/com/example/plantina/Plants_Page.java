package com.example.plantina;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class Plants_Page extends AppCompatActivity {
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
        setContentView(R.layout.plants_page);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Plants Suggestions");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Plants");

        //firebase = databaseReference.orderByChild("Name").equalTo("Ambarella");

        databaseReference.keepSynced(true);

        //bundle = getIntent().getExtras();
        String soilname =getIntent().getStringExtra("Soil");
        firebase = databaseReference.orderByChild("soil_name").equalTo(soilname);

        progressDialog.show();
        recyclerView = findViewById(R.id.myplantrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Plant_Model, MyPlantViewHolder> firebaseRecyclerAdapterplant= new FirebaseRecyclerAdapter<Plant_Model, MyPlantViewHolder>
                (Plant_Model.class, R.layout.recycler_image,MyPlantViewHolder.class,firebase) {

            protected void populateViewHolder(MyPlantViewHolder myViewHolder, Plant_Model model, int i) {
               myViewHolder.setImage(getApplicationContext(),model.getImage());
                myViewHolder.setName(model.getName());
                progressDialog.dismiss();

            }
        };

        recyclerView.setAdapter(firebaseRecyclerAdapterplant);
    }

    public  static  class MyPlantViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public MyPlantViewHolder(View itemView)
        {
            super(itemView);
            view=itemView;
        }
        public void setImage(Context ctx, String Image)
        {
            ImageView PlantImage = view.findViewById(R.id.plantimage);
            Picasso.get().load(Image).into(PlantImage);
        }
        public void setName(String Name)
        {
            TextView PlantName = view.findViewById(R.id.plantname);
            PlantName.setText(Name);
        }

    }

    }
