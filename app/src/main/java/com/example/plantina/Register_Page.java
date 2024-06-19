package com.example.plantina;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class Register_Page  extends AppCompatActivity implements View.OnClickListener {
    private EditText txt_fullname, txt_username, txt_email, txt_age, txt_password, txt_confirm_password;
    ProgressDialog progressDialog;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Register");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_fullname = findViewById(R.id.fullname);
        txt_username = findViewById(R.id.username);
        txt_email = findViewById(R.id.email);
        txt_age = findViewById(R.id.age);
        txt_password = findViewById(R.id.password);
        txt_confirm_password = findViewById(R.id.confirm_password);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registering User...");

        findViewById(R.id.signUp_Button).setOnClickListener(this);
        findViewById(R.id.sign_in).setOnClickListener(this);
    }

    private void regiterUser() {
        final String name = txt_fullname.getText().toString().trim();
        final String username = txt_username.getText().toString().trim();
        final String email = txt_email.getText().toString().trim();
        final String age = txt_age.getText().toString().trim();
        final String password = txt_password.getText().toString().trim();
        String matchpassword = txt_confirm_password.getText().toString().trim();

        if (name.isEmpty()) {
            txt_fullname.setError("Please Enter Your Full Name");
            txt_fullname.requestFocus();
            return;
        }
        else if (email.isEmpty()) {
            txt_username.setError("Please Enter Your Username");
            txt_username.requestFocus();
            return;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            txt_email.setError("Invalid Email");
            txt_email.requestFocus();
            return;
        }
        else if (age.isEmpty()) {
            txt_age.setError("Please Enter Your Age");
            txt_age.requestFocus();
            return;
        }
        else if (password.isEmpty()) {
            txt_password.setError("Please Enter a Password");
            txt_password.requestFocus();
            return;
        }
        else if (password.length() < 6) {
            txt_password.setError("Password Length should be at least 7 characters");
            txt_password.requestFocus();
            return;
        }
        else if (matchpassword.isEmpty()) {
            txt_confirm_password.setError("Please enter your password here to confirm");
            txt_confirm_password.requestFocus();
            return;
        }
        else if (!password.equals(matchpassword)) {
            txt_confirm_password.setError("Passwords are not matching");
            txt_confirm_password.requestFocus();
            return;
        }else {
            progressDialog.show();

            progressDialog.dismiss();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                FirebaseUser user = mAuth.getCurrentUser();

                                String uid = user.getUid();

                                HashMap<Object, String> hashMap = new HashMap<>();
                                hashMap.put("name", name);
                                hashMap.put("username", username);
                                hashMap.put("email", email);
                                hashMap.put("age", age);
                                hashMap.put("password", password);
                                hashMap.put("UserId", uid);

                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        progressDialog.dismiss();
                                        // progressBar.setVisibility(View.GONE);
                                        if (task.isSuccessful()) {
                                            Toast.makeText(Register_Page.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                            finish();
                                        }

                                    }

                                });
                                startActivity(new Intent(Register_Page.this, Login_Page.class));
                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Register_Page.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }


                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //dismiss the progress bar and show the error message
                    progressDialog.dismiss();
                    Toast.makeText(Register_Page.this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.signUp_Button:
                regiterUser();
                break;

            case R.id.sign_in:
                startActivity(new Intent(Register_Page.this,Login_Page.class));
                finish();
                break;
        }
    }
}
