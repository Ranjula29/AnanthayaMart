package com.example.app2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import javax.microedition.khronos.egl.EGLDisplay;

public class ProductEditActivity extends AppCompatActivity {


    private EditText priceEdit,nameEdit,descriptionEdit;
    private ImageView imageEdit;
    private Button updateButton;
    private String ProductId ="";
    private String downloadImageUrl="";
    private Uri imageuri;
    private StorageReference storageimageref2;
    private StorageTask uploadTask;
    private String checker ="";
    private static final int GalleryPick = 1;
    private String myuri = "";
    private DatabaseReference ref2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_edit);


        ProductId = getIntent().getStringExtra("pid");
        storageimageref2 =  FirebaseStorage.getInstance().getReference().child("Product Image/cake");

        imageEdit=(ImageView) findViewById(R.id.edit_image);
        nameEdit=(EditText) findViewById(R.id.product_editname);
        priceEdit=(EditText) findViewById(R.id.product_editprice);
        descriptionEdit=(EditText) findViewById(R.id.product_editdescription);
        updateButton = (Button) findViewById(R.id.upadte_button);


      getproductinfo(ProductId);


      updateButton.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              if (checker.equals("clicked")){
                  saveinfo();

              }
              else
              {
                  updateOnlyProductInfo();
              }
          }
      });

      imageEdit.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              checker = "clicked";
              OpenGallery();
          }
      });


    }

    private void saveinfo() {

        if (TextUtils.isEmpty(nameEdit.getText().toString())){

            Toast.makeText(this, "Name is mandatory", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(priceEdit.getText().toString())){

            Toast.makeText(this, "price is mandatory", Toast.LENGTH_SHORT).show();
        }


        if (TextUtils.isEmpty(descriptionEdit.getText().toString())){

            Toast.makeText(this, "description is mandatory", Toast.LENGTH_SHORT).show();
        }
        else if(checker.equals("clicked")){

            uploadImage();

        }

    }

    private void updateOnlyProductInfo() {


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products/cake");
        HashMap<String,Object> productmap = new HashMap<>();
        productmap.put("pname",nameEdit.getText().toString());
        productmap.put("price",priceEdit.getText().toString());
        productmap.put("description",descriptionEdit.getText().toString());
        ref.child(ProductId).updateChildren(productmap);


        startActivity(new Intent(ProductEditActivity.this, UpdateActivity.class));
        Toast.makeText(ProductEditActivity.this, "successfully ", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GalleryPick && resultCode ==RESULT_OK && data!= null ){

            imageuri = data.getData();
            imageEdit.setImageURI(imageuri);
        }
        else
        {
            Toast.makeText(this, "Error, Try Again", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(ProductEditActivity.this, ProductEditActivity.class));
            finish();
        }
    }

    private void OpenGallery() {

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick );
    }


    private void uploadImage()
    {

        if (imageuri != null) {
            final StorageReference fileRef = storageimageref2.child(imageuri.getLastPathSegment() + ".jpg");

            final UploadTask uploadTask = fileRef.putFile(imageuri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    String message = e.toString();
                    Toast.makeText(ProductEditActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(ProductEditActivity.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                    Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }

                            downloadImageUrl = fileRef.getDownloadUrl().toString();
                            return fileRef.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                downloadImageUrl = task.getResult().toString();
                                Toast.makeText(ProductEditActivity.this, "Product Image Url Saved ", Toast.LENGTH_SHORT).show();
                                saveProductInfo();
                            }
                        }

                    });

                }
            });
        }
        else

        {
            Toast.makeText(this, "image is not selected", Toast.LENGTH_SHORT).show();
        }
    }

  private void saveProductInfo() {

       DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Products");
       HashMap<String,Object> productmap = new HashMap<>();
       productmap.put("pname",nameEdit.getText().toString());
       productmap.put("price",priceEdit.getText().toString());
       productmap.put("description",descriptionEdit.getText().toString());
       productmap.put("image",downloadImageUrl);
       ref.child(ProductId).updateChildren(productmap);


      startActivity(new Intent(ProductEditActivity.this, UpdateActivity.class));
      Toast.makeText(ProductEditActivity.this, "successfully ", Toast.LENGTH_SHORT).show();
      finish();
    }

    private void getproductinfo(String productId) {

        DatabaseReference Productref = FirebaseDatabase.getInstance().getReference().child("Products/cake");
        Productref.child(ProductId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                   Products products = snapshot.getValue(Products.class);
                    Picasso.get().load(products.getImage()).into(imageEdit);
                    nameEdit.setText(products.getPname());
                    priceEdit.setText(products.getPrice());
                    descriptionEdit.setText(products.getDescription());


                }



                   }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }


}