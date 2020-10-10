package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText e_mail,pwd;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    Button btnAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        e_mail = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        findViewById(R.id.btnREg).setOnClickListener(this);
        findViewById(R.id.btnLog).setOnClickListener(this);
        findViewById(R.id.adminlogBtn).setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(this,HomePage.class));
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btnREg:

                finish();
                startActivity(new Intent(this, Signup.class));

                break;
            case R.id.btnLog:

                userLogin();

                break;

            case R.id.adminlogBtn:

                finish();
                startActivity(new Intent(this, AdminLoginActvity.class));


                break;



        }

    }



    private void userLogin() {

        String email = e_mail.getText().toString().trim();
        String password = pwd.getText().toString().trim();

        if(email.isEmpty()){
            e_mail.setError("Email is Required");
            e_mail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            e_mail.setError("Please Enter a valid Email");
            e_mail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            pwd.setError("Password is Required");
            e_mail.requestFocus();
            return;
        }
        if(password.length()<6){
            pwd.setError("Minimum length of password should be 6");
            e_mail.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);

                if (task.isSuccessful()){
                    finish();
                    Toast.makeText(getApplicationContext(),"User Logged In Successfully",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(getApplicationContext(),HomePage.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}