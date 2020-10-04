package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductdetailsActivity extends AppCompatActivity {

    private String ProductID = "";
    private TextView productname,productprice,productdescription;
    private ImageView productimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);

        ProductID = getIntent().getStringExtra("pid");
        productimage = (ImageView) findViewById(R.id.productdetailimage);
        productdescription = (TextView) findViewById(R.id.detailsdescription);
        productname = (TextView) findViewById(R.id.productname1);
        productprice = (TextView)findViewById(R.id.pricedetails);

        getProductdetails(ProductID);
    }

    private void getProductdetails(String productID)
    {
        DatabaseReference productref = FirebaseDatabase.getInstance().getReference().child("Products/cake");
        productref.child(ProductID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                if (snapshot.exists()){
                    Products products = snapshot.getValue(Products.class);

                    productname.setText(products.getPname());
                    productprice.setText(products.getPrice());
                    productdescription.setText(products.getDescription());
                    Picasso.get().load(products.getImage()).into(productimage);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}