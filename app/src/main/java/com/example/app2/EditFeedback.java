package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import java.util.HashMap;
import java.util.Map;

public class EditFeedback extends AppCompatActivity {

    RatingBar edit_ratingBar;
    TextView edit_RatingScale;
    EditText edit_Feedback,edit_UserName;
    Button edit_FeedbackBtn,delete_feedback;

    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    String userID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_feedback);

        edit_ratingBar = (RatingBar)findViewById(R.id.editRatingBar);
        edit_RatingScale = (TextView) findViewById(R.id.editRatingScale);
        edit_Feedback = (EditText) findViewById(R.id.EditFeedback);
        edit_UserName = (EditText) findViewById(R.id.editUname);
        edit_FeedbackBtn = (Button) findViewById(R.id.updateFeddbackBtn);
        delete_feedback = (Button) findViewById(R.id.deleteFeddbackBtn);
        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        userID = mAuth.getCurrentUser().getUid();


        firebaseUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();


        Intent data = getIntent();
        String userName = data.getStringExtra("UserName");
        String ratingScale = data.getStringExtra("RatingScale");
        String feedBack = data.getStringExtra("feedBack");
        String ratingBar  = data.getStringExtra("RatingBar");

        edit_ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                edit_RatingScale.setText(String.valueOf(v));
                switch ((int) ratingBar.getRating()) {
                    case 1:
                        edit_RatingScale.setText("Very bad");
                        break;
                    case 2:
                        edit_RatingScale.setText("Need some improvement");
                        break;
                    case 3:
                        edit_RatingScale.setText("Good");
                        break;
                    case 4:
                        edit_RatingScale.setText("Great");
                        break;
                    case 5:
                        edit_RatingScale.setText("Awesome. I love it");
                        break;
                    default:
                        edit_RatingScale.setText("");
                }
            }
        });



        edit_FeedbackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_UserName.getText().toString().isEmpty() || edit_Feedback.getText().toString().isEmpty()
                        || edit_RatingScale.getText().toString().isEmpty() || String.valueOf(edit_ratingBar.getRating()).isEmpty() ){

                    Toast.makeText(EditFeedback.this,"One or many fields are Empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                        DocumentReference documentReference = fstore.collection("feedback").document(firebaseUser.getUid());
                        Map<String,Object> edited = new HashMap<>();


                        edited.put("UserName",edit_UserName.getText().toString());
                        edited.put("RatingScale",edit_RatingScale.getText().toString());
                        edited.put("feedBack",edit_Feedback.getText().toString());
                        edited.put("RatingBar",String.valueOf(edit_ratingBar.getRating()));


                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {


                            @Override
                            public void onSuccess(Void aVoid) {

                                Toast.makeText(EditFeedback.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditFeedback.this,ProfileActivity.class));

                                finish();
                            }
                        });


                    }

        });
        DocumentReference documentReference = fstore.collection("feedback").document(userID);

        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                edit_UserName.setText(value.getString("UserName"));
                edit_RatingScale.setText(value.getString("RatingScale"));
                edit_Feedback.setText(value.getString("feedBack"));
                edit_ratingBar.setRating(Float.valueOf(value.getString("RatingBar")));



            }
        });


        delete_feedback.setOnClickListener(new View.OnClickListener() {
            @Override//
            public void onClick(View v) {

                fstore.collection("feedback").document().delete();
                Toast.makeText(EditFeedback.this, "Feedback Deleted", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(EditFeedback.this,Feedback.class));

            }
        });


    }
}