package com.example.plantina;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;

import io.paperdb.Paper;

public class Home_Page extends AppCompatActivity implements View.OnClickListener {
    CardView sensordata, myaccount, takephoto, news, aboutus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Home");

        findViewById(R.id.sensordata).setOnClickListener(this);
        findViewById(R.id.myaccount).setOnClickListener(this);
        findViewById(R.id.takephoto).setOnClickListener(this);
        findViewById(R.id.newssection).setOnClickListener(this);
        //findViewById(R.id.aboutus).setOnClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id= item.getItemId();
//        if(id==R.id.logout){
//            logout();
//        }
        switch (item.getItemId())
        {
            case R.id.logout :
                FirebaseAuth.getInstance().signOut();
                Home_Page.this.finish();
                Paper.book().destroy();
                Intent i = new Intent(Home_Page.this, Login_Page.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
        //return true;
    }

//    private void logout() {
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(Home_Page.this, Login_Page.class);
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.sensordata :
                startActivity(new Intent(this, Connecting_Page.class));
                break;

            case R.id.myaccount :
                startActivity(new Intent(this, Myaccount_Page.class));
                break;

            case R.id.takephoto :
                startActivity(new Intent(this, Image_Capture_Details_Page.class));
                break;
            case R.id.newssection :
                startActivity(new Intent(this, News_Detail_Page.class));
                break;
//            case R.id.aboutus :
//                startActivity(new Intent(this, Register_Page.class));
//                break;

        }
    }
}
