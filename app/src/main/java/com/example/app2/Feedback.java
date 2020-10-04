package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Feedback extends AppCompatActivity {

    public static final String TAG = "TAG";
    RatingBar mRatingBar;
    TextView mRatingScale;
    EditText mFeedback,mUserName;
    Button mSendFeedback;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

         mRatingBar = (RatingBar) findViewById(R.id.ratingBar);
         mRatingScale = (TextView) findViewById(R.id.tvRatingScale);
         mFeedback = (EditText) findViewById(R.id.feedbackEdit);
         mSendFeedback = (Button) findViewById(R.id.btnSubmit);
         mUserName = (EditText) findViewById(R.id.userName);
         mAuth = FirebaseAuth.getInstance();
         fstore = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();



        mRatingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                mRatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        mRatingScale.setText("Very bad");
                        break;
                    case 2:
                        mRatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        mRatingScale.setText("Good");
                        break;
                    case 4:
                        mRatingScale.setText("Great");
                        break;
                    case 5:
                        mRatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        mRatingScale.setText("");
                }
            }
        });

        

        mSendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (mFeedback.getText().toString().isEmpty() || mRatingScale.getText().toString().isEmpty()
                            || mUserName.getText().toString().isEmpty() || String.valueOf(mRatingBar.getRating()).isEmpty() ){


                    Toast.makeText(Feedback.this, "One or more fields are empty", Toast.LENGTH_LONG).show();
                    return;
                }

                else {
                    final String ratingScale = mRatingScale.getText().toString().trim();
                    final String feedBckText = mFeedback.getText().toString().trim();

                    final String UserName  = mUserName.getText().toString();
                    final String ratingBar = String.valueOf(mRatingBar.getRating());



                    DocumentReference documentReference = fstore.collection("feedback").document(userID);
                    Map<String , Object> fuser = new HashMap<>();
                    fuser.put("UserName",UserName);
                    fuser.put("RatingScale",ratingScale);
                    fuser.put("feedBack",feedBckText);
                    fuser.put("RatingBar",ratingBar);

                    documentReference.set(fuser).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {

                            Log.d(TAG, "onSuccess: Feedback is created for"+ userID);
                            ProfileActivity.feedbackUser.setVisibility(View.INVISIBLE);
                            ProfileActivity.viewfeedbackText.setVisibility(View.VISIBLE);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: user profile not created"+ e.toString());
                        }
                    })
                    ;

                    startActivity(new Intent(Feedback.this,ProfileActivity.class));
                    Toast.makeText(Feedback.this, "Feed Back Sent! Thank you for your support !", Toast.LENGTH_SHORT).show();



                }

            }
        });

        if (!fstore.collection("feedback").getId().isEmpty() ){
            ProfileActivity.feedbackUser.setVisibility(View.INVISIBLE);
            ProfileActivity.viewfeedbackText.setVisibility(View.VISIBLE);
        }



    }
}