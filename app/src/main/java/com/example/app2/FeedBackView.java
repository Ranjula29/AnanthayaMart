package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class FeedBackView extends AppCompatActivity {

    RatingBar ratingBar;
    TextView uName , scale , feedBack;
    Button editFeedBtn,backProfileBtn;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_view);

        ratingBar = (RatingBar)findViewById(R.id.ratingBarView);
        uName = (TextView) findViewById(R.id.userNameView);
        scale = (TextView) findViewById(R.id.ratingScale);
        feedBack = (TextView) findViewById(R.id.feedbackView);
        editFeedBtn = (Button) findViewById(R.id.feedbackEditBtn);
        backProfileBtn= (Button) findViewById(R.id.proBtn);

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        userID = mAuth.getCurrentUser().getUid();


        editFeedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedBackView.this,EditFeedback.class));
            }
        });

        DocumentReference documentReference = fstore.collection("feedback").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                uName.setText(value.getString("UserName"));
                scale.setText(value.getString("RatingScale"));
                feedBack.setText(value.getString("feedBack"));
                ratingBar.setRating(Float.valueOf(value.getString("RatingBar")));



            }
        });
        backProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FeedBackView.this,ProfileActivity.class));
            }
        });
    }
}