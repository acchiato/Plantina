package com.example.plantina;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import io.paperdb.Paper;

public class Login_Page extends AppCompatActivity implements View.OnClickListener {
    EditText txt_loginemail, txt_loginpassword;
    TextView RecoverPassoword;
    CheckBox checkBoxRemember;

    FirebaseAuth mAuth;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Login");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mAuth = FirebaseAuth.getInstance();
        txt_loginemail=findViewById(R.id.login_email);
        txt_loginpassword= findViewById(R.id.login_password);
        RecoverPassoword = findViewById(R.id.forgetpassword);

        progressDialog = new ProgressDialog(this);

        findViewById(R.id.txt_register).setOnClickListener(this);
        findViewById(R.id.login_Button).setOnClickListener(this);
        checkBoxRemember =findViewById(R.id.remember);
        Paper.init(this);

        RecoverPassoword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowRecoverPasswordTemplate();
            }
        });

    }

    private void ShowRecoverPasswordTemplate() {
        //alert dialog

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Recover");

        LinearLayout linearLayout = new LinearLayout(this);

        final EditText loginemail = new EditText(this);
        loginemail.setHint("Email");
        loginemail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);

        loginemail.setMaxEms(16);

        linearLayout.addView(loginemail);
        linearLayout.setPadding(10,10,10,10);

        builder.setView(linearLayout);

        //buttons recover
        builder.setPositiveButton("Recover", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //input email
                String email = loginemail.getText().toString().trim();
                BeginRecoveryEmail(email);
            }
        });
        //buttons cancel
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //dismiss dialog
                dialogInterface.dismiss();
            }
        });
        builder.create().show();
    }

    private void BeginRecoveryEmail(String email) {
        progressDialog.setMessage("Sending Email...");

        progressDialog.show();

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful())
                        {
                            Toast.makeText(Login_Page.this, "Email Sent", Toast.LENGTH_SHORT).show();
                        }else
                        {
                            Toast.makeText(Login_Page.this, "Failed....", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.show();
                //get and show error message
                Toast.makeText(Login_Page.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void CustomerLogin()
    {
        final String email= txt_loginemail.getText().toString().trim();
        final String password= txt_loginpassword.getText().toString().trim();

        if (checkBoxRemember.isChecked())
        {
            Paper.book().write(Prevalent.UserEmail, email);
            Paper.book().write(Prevalent.UserPassword, password);

        }

        if(TextUtils.isEmpty(email))
        {
            txt_loginemail.setError("Email Required");
            txt_loginemail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            txt_loginemail.setError("Enter a valid Email");
            txt_loginemail.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(password))
        {
            txt_loginpassword.setError("Password Required");
            txt_loginpassword.requestFocus();
            return;
        }

        if(password.length()<6)
        {
            txt_loginpassword.setError("Password should be atleast 6 characters long ");
            txt_loginpassword.requestFocus();
            return;
        }


        progressDialog.setMessage("Loging...");

        progressDialog.show();
        //progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressDialog.dismiss();
                //progressBar.setVisibility(View.GONE);


                if(task.isSuccessful())
                {
                    Toast.makeText(Login_Page.this,"Login Successful",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(Login_Page.this,Home_Page.class));
                }else{
                    Toast.makeText(Login_Page.this,"Your Email or Password is incorrect, Please try again",Toast.LENGTH_LONG).show();
                }
            }
        });

    }
        @Override
    public void onClick(View v) {
            switch (v.getId())
            {
                case R.id.login_Button :
                    CustomerLogin();
                    break;

                case R.id.txt_register :
                    startActivity(new Intent(this, Register_Page.class));
                    finish();
                    break;

            }
    }

}
