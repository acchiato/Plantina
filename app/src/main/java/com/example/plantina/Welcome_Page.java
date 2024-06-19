package com.example.plantina;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Welcome_Page extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_page);

        findViewById(R.id.signUp_Button).setOnClickListener(this);
        findViewById(R.id.signIn_Button).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {

            case R.id.signUp_Button:

                startActivity(new Intent(Welcome_Page.this,Register_Page.class));break;


            case R.id.signIn_Button:
                startActivity(new Intent(Welcome_Page.this,Login_Page.class));break;

        }
    }
}