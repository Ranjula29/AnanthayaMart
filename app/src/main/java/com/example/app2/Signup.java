package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Signup extends AppCompatActivity implements View.OnClickListener{


    public static final String TAG = "TAG";
    EditText fname,lname,contact,e_mail,address,pwd;

    ProgressBar progressBar;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fname = findViewById(R.id.first);
        lname = findViewById(R.id.last);
        contact = findViewById(R.id.phone);
        e_mail = findViewById(R.id.email);
        address = findViewById(R.id.address);
        pwd = findViewById(R.id.password);
        progressBar=findViewById(R.id.progressBar);
        findViewById(R.id.btnRegister).setOnClickListener(this);
        findViewById(R.id.btnlogin).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
    }

    @Override
    public void onClick(View view) {



        switch (view.getId()){
            case R.id.btnRegister:
                registeruser();
                break;

            case R.id.btnlogin:

                finish();
                startActivity(new Intent(this,MainActivity.class));

                break;
        }
    }

    private void registeruser() {

        final String email = e_mail.getText().toString().trim();
        final String password = pwd.getText().toString().trim();

        final String firstName  = fname.getText().toString();
        final String lastName = lname.getText().toString();
        final String phoneNum = contact.getText().toString();
        final String u_address = address.getText().toString();


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
        if(firstName.isEmpty()){
            fname.setError("First Name is Required");
            fname.requestFocus();
            return;
        }
        if(lastName.isEmpty()){
            lname.setError("Last Name is Required");
            lname.requestFocus();
            return;
        }
        if(phoneNum.isEmpty()){
            address.setError("Address is Required");
            address.requestFocus();
            return;
        }
        if(u_address.isEmpty()){
            pwd.setError("Password is Required");
            e_mail.requestFocus();
            return;
        }
    progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()){

                    //send email authentication

                    FirebaseUser fuser = mAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Signup.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            
                            Log.d(TAG,"OnFailure : Email not sent"+e.getMessage());
                        }
                    });


                    finish();
                    Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();

                    userID = mAuth.getCurrentUser().getUid();
                    DocumentReference documentReference = fstore.collection("users").document(userID);
                    Map<String , Object> user = new HashMap<>();
                    user.put("firstName",firstName);
                    user.put("lastName",lastName);
                    user.put("email",email);
                    user.put("phone",phoneNum);
                    user.put("address",u_address);
                    user.put("password",password);

                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "onSuccess: user profile is created for"+ userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: user profile not created"+ e.toString());
                        }
                    })
                    ;

                    startActivity(new Intent(Signup.this,HomePage.class));
                }
                else {

                    if (task.getException() instanceof FirebaseAuthUserCollisionException){
                    Toast.makeText(getApplicationContext(),"You are already registered ",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}