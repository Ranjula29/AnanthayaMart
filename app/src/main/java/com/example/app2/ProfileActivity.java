package com.example.app2;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE =101;
    public static final String TAG = "TAG";
    ImageView profileImage;
    TextView firstName,lastName,address,phone,email;
    public static TextView feedbackUser,viewfeedbackText;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userId;
    Button changeProImage , resetPassLocal,resendCodeBtn;
    Button btnLogingout;
    ProgressBar progressBar;
    StorageReference storageReference;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        firstName = (TextView) findViewById(R.id.profileFname);
        lastName = (TextView)findViewById(R.id.profileLname);
        address = (TextView)findViewById(R.id.profileAddress);
        phone = (TextView) findViewById(R.id.profilePhone);
        email = (TextView)findViewById(R.id.profileEmail);
        profileImage = (ImageView) findViewById(R.id.profileImage);
        changeProImage = (Button) findViewById(R.id.changeProfile);
        resetPassLocal = (Button)findViewById(R.id.resetPasswordLocal);
        btnLogingout = (Button)findViewById(R.id.btnLogout);
        resendCodeBtn = (Button)findViewById(R.id.resendCode);
        feedbackUser = (TextView)findViewById(R.id.feedBack);
        viewfeedbackText = (TextView)findViewById(R.id.viewFeedText);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        user = mAuth.getCurrentUser();

        if (!user.isEmailVerified()){

            resendCodeBtn.setVisibility(View.VISIBLE);

            resendCodeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(ProfileActivity.this, "Verification email has been sent", Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                            Log.d(TAG,"OnFailure : Email not sent"+e.getMessage());
                        }
                    });

                }
            });

        }

        storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });

        DocumentReference documentReference = fstore.collection("users").document(userId);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                firstName.setText(value.getString("firstName"));
                lastName.setText(value.getString("lastName"));
                address.setText(value.getString("address"));
                phone.setText(value.getString("phone"));
                email.setText(value.getString("email"));
            }
        });

        progressBar=findViewById(R.id.progressBar);


        changeProImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),EditProfile.class);
                intent.putExtra("fname",firstName.getText().toString());
                intent.putExtra("lname",lastName.getText().toString());
                intent.putExtra("phone",phone.getText().toString());
                intent.putExtra("email",email.getText().toString());
                intent.putExtra("address",address.getText().toString());
                startActivity(intent);
            }
        });

        resetPassLocal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText resetPassword = new EditText(v.getContext());

                final  AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter the new Password > 6 charachters long");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String newPassword = resetPassword.getText().toString();

                        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(ProfileActivity.this, "Your password reset successfully", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ProfileActivity.this, "Password Reset failed", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passwordResetDialog.create().show();
            }
        });

        btnLogingout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(ProfileActivity.this,MainActivity.class));
            }
        });

        feedbackUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this,Feedback.class));

            }
        });

        viewfeedbackText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(ProfileActivity.this,FeedBackView.class));

            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this,MainActivity.class));
        }

    }

}