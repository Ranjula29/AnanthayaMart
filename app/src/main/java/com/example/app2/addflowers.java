package com.example.app2;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class addflowers extends AppCompatActivity {

    private String CategoryName, Description, Price, Pname, saveCurrentdate, saveCrrenttime;
    private Button addProduct;
    private EditText inputName, inputDescription, inputPrice;
    private ImageView inputImage;
    private static final int GalleryPick = 1;
    private Uri imageuri;
    private String ProductRandomKey, downloadImageUrl;
    private StorageReference ProductImagesRef;
    private DatabaseReference ProductRef;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_flower);


        CategoryName = getIntent().getExtras().get("category").toString();
        ProductImagesRef = FirebaseStorage.getInstance().getReference().child("Product Image");
        ProductRef = FirebaseDatabase.getInstance().getReference().child("Products/flower");

        Toast.makeText(this, CategoryName, Toast.LENGTH_SHORT).show();

        addProduct = (Button) findViewById(R.id.add_buttonf);
        inputImage = (ImageView) findViewById(R.id.add_imagef);
        inputName = (EditText) findViewById(R.id.product_namef);
        inputDescription = (EditText) findViewById(R.id.product_Descriptionf);
        inputPrice = (EditText) findViewById(R.id.product_Pricef);


        inputImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                OpenGallery();

            }
        });

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ValidateProductData();
            }
        });
    }



    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GalleryPick && resultCode ==RESULT_OK && data!= null ){

            imageuri = data.getData();
            inputImage.setImageURI(imageuri);
        }

    }


    private void  ValidateProductData(){

        Description = inputDescription.getText().toString();
        Price = inputPrice.getText().toString();
        Pname = inputName.getText().toString();


        if (imageuri == null){
            Toast.makeText(this, "Product Image is Mandatory", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description)){
            Toast.makeText(this, "Please Write Product Description", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Price)){
            Toast.makeText(this, "Please Write Product Price", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(Pname)){
            Toast.makeText(this, "Please Write Product Price", Toast.LENGTH_SHORT).show();
        }

        else {
            StoreProductinformation();
        }
    }

    private void StoreProductinformation() {

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd yyyy");
        saveCurrentdate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCrrenttime = currentTime.format(calendar.getTime());


        ProductRandomKey = saveCurrentdate + saveCrrenttime;



        final StorageReference filePath = ProductImagesRef.child(imageuri.getLastPathSegment() + ProductRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(imageuri);

        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(addflowers.this, "Error" + message, Toast.LENGTH_SHORT).show();

            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(addflowers.this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show();

                Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();

                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful())
                        {
                            downloadImageUrl =task.getResult().toString();
                            Toast.makeText(addflowers.this, "Product Image Url Saved ", Toast.LENGTH_SHORT).show();
                            SaveProductInforToDatabase();
                        }

                    }
                });

            }
        });




    }


    private void SaveProductInforToDatabase(){

        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("pid",ProductRandomKey);
        productMap.put("date",saveCurrentdate);
        productMap.put("time",saveCrrenttime);
        productMap.put("description",Description);
        productMap.put("image",downloadImageUrl);
        productMap.put("category",CategoryName);
        productMap.put("price",Price);
        productMap.put("pname",Pname);

        ProductRef.child(ProductRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(addflowers.this,CategoryActivity.class);
                            startActivity(intent);
                            Toast.makeText(addflowers.this, "Product is Added Successfully..", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            String message = task.getException().toString();
                            Toast.makeText(addflowers.this, "Error:" + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}