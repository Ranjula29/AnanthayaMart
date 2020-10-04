package com.example.app2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileEditFname, profileEditLname, profileEditPhone, profileEditEmail, profileEditAddress;
    ImageView profileImageView;
    FirebaseAuth mAuth;
    FirebaseFirestore fstore;
    Button saveBtn,deleteAccount;
    FirebaseUser firebaseUser;
    StorageReference storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String firstName = data.getStringExtra("fname");
        String lastName = data.getStringExtra("lname");
        String phone = data.getStringExtra("phone");
        String email = data.getStringExtra("email");
        String address = data.getStringExtra("address");

        mAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();
        firebaseUser = mAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference();

        profileEditFname = findViewById(R.id.editFname);
        profileEditLname = findViewById(R.id.editLname);
        profileEditAddress = findViewById(R.id.editAddress);
        profileEditPhone = findViewById(R.id.editPhone);
        profileEditEmail = findViewById(R.id.editEmail);
        profileImageView = findViewById(R.id.profileImageView);
        saveBtn = findViewById(R.id.saveProfileInfo);

        deleteAccount =findViewById(R.id.deleteBtn);





        StorageReference profileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImageView);
            }
        });

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfile.this, "profileImage clicked", Toast.LENGTH_SHORT).show();
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                 startActivityForResult(openGalleryIntent , 1000);
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (profileEditFname.getText().toString().isEmpty() || profileEditLname.getText().toString().isEmpty()
                        || profileEditAddress.getText().toString().isEmpty() || profileEditPhone .getText().toString().isEmpty()
                        ||profileEditAddress .getText().toString().isEmpty()  ){

                    Toast.makeText(EditProfile.this,"One or many fields are Empty",Toast.LENGTH_SHORT).show();
                    return;
                }
                final String email = profileEditEmail.getText().toString();
                firebaseUser.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference documentReference = fstore.collection("users").document(firebaseUser.getUid());
                        Map<String,Object> edited = new HashMap<>();

                        edited.put("email",email);
                        edited.put("firstName",profileEditFname.getText().toString());
                        edited.put("lastName",profileEditLname.getText().toString());
                        edited.put("phone",profileEditPhone.getText().toString());
                        edited.put("address",profileEditAddress.getText().toString());

                        documentReference.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EditProfile.this, "Data Updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditProfile.this,ProfileActivity.class));
                                finish();
                            }
                        });




                        Toast.makeText(EditProfile.this,"Email changed",Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EditProfile.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                })
            ;}
        });

        profileEditFname.setText(firstName);
        profileEditLname.setText(lastName);
        profileEditPhone.setText(phone);
        profileEditEmail.setText(email);
        profileEditAddress.setText(address);



        Log.d(TAG,"oncreate : "+firstName+" "+lastName+" "+phone+" "+email+" "+address);


        deleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        fstore.collection("users").document(mAuth.getCurrentUser().getUid()).delete();
                        finish();
                        startActivity(new Intent(EditProfile.this,Signup.class));
                        Toast.makeText(EditProfile.this, "Account Deleted", Toast.LENGTH_SHORT).show();

                    }
                });



            }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //in fragment class callback

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000){

            if (resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();
                // profileImage.setImageURI(imageUri);

                uploadImageToFirebaseStorage(imageUri);

            }
        }


    }


    private void uploadImageToFirebaseStorage(Uri imageUri) {

        //upload image to firebase storage
        final StorageReference fileRef = storageReference.child("users/"+mAuth.getCurrentUser().getUid()+"/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditProfile.this,"Failed",Toast.LENGTH_SHORT).show();
            }
        });

    }

}