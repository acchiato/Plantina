package com.example.plantina;

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

public class News_Detail_Page extends AppCompatActivity{
//    ViewPager viewPager;
//    Adapter adapter;

    RecyclerView recyclerView;

    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;
    Query firebase;

    //Firebasedataloader firebasedataloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_display_page);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("News");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = getInstance();
        user = mAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("News");

        recyclerView = findViewById(R.id.mynewrecyclerview);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //firebase = databaseReference.orderByChild("plant").equalTo("Ambarella");

        databaseReference.keepSynced(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //databaseReference = FirebaseDatabase.getInstance().getReference().child("News");

        //firebase = databaseReference.orderByChild("heading").equalTo("Main heading");

        //firebasedataloader = this;
        //loadNews();

        //viewPager = (ViewPager)findViewById(R.id.viewpager);
        //viewPager.setPageTransformer(true,new transformer());
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<News_Model, MyViewHolder> firebaseNewRecyclerAdapter= new FirebaseRecyclerAdapter<News_Model, MyViewHolder>
                (News_Model.class, R.layout.news_card, MyViewHolder.class,databaseReference) {

            protected void populateViewHolder(MyViewHolder myViewHolder, News_Model model, int i) {
                myViewHolder.setDate(model.getDate());
                myViewHolder.setHeading(model.getHeading());
                myViewHolder.setDescription(model.getDescription());
            }
        };

        recyclerView.setAdapter(firebaseNewRecyclerAdapter);
    }

    public  static  class MyViewHolder extends RecyclerView.ViewHolder
    {
        View view;
        public MyViewHolder(View itemView)
        {
            super(itemView);
            view=itemView;
        }
        public void setDate(String date)
        {
            TextView Date = itemView.findViewById(R.id.datedataval);
            Date.setText(date);
        }
        public void setHeading(String heading)
        {
            TextView Head = itemView.findViewById(R.id.headingdataval);
            Head.setText(heading);
        }
        public void setDescription(String description)
        {
            TextView Decs = itemView.findViewById(R.id.desriptiondataval);
            Decs.setText(description);
        }
    }


    //    private void loadNews() {
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            List<News_Model> newslist = new ArrayList<>();
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot ds: dataSnapshot.getChildren())
//                    newslist.add(ds.getValue(News_Model.class));
//                firebasedataloader.onFirebaseLoadSuccess(newslist);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                firebasedataloader.onFirebaseLoadFailed(databaseError.getMessage());
//            }
//        });
//    }
//
//    @Override
//    public void onFirebaseLoadSuccess(List<News_Model> newslist) {
//        adapter = new Adapter(this, newslist);
//        viewPager.setAdapter(adapter);
//    }
//
//    @Override
//    public void onFirebaseLoadFailed(String message) {
//        Toast.makeText(this, ""+message, Toast.LENGTH_SHORT).show();
//    }
}
