package com.example.app2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import javax.microedition.khronos.egl.EGLDisplay;

public class OrdereditActivity extends AppCompatActivity {

///
    private EditText addressedit,phoneedit,noteedit;
    private Button updateButton2;
    private String OrderId ="";
    private DatabaseReference reforderedit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderedit);


        OrderId = getIntent().getStringExtra("orderid");


        addressedit=(EditText) findViewById(R.id.order_editaddress);
        phoneedit=(EditText) findViewById(R.id.order_editphone);
        noteedit=(EditText) findViewById(R.id.order_editnote);
        updateButton2 = (Button) findViewById(R.id.update_buttonorder);


        getOrderinfo(OrderId);


       updateButton2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               updateorderInfo();
           }
       });

    }

    private void updateorderInfo() {

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Order");
        HashMap<String,Object> productmap = new HashMap<>();
        productmap.put("address",addressedit.getText().toString());
        productmap.put("contact",phoneedit.getText().toString());
        productmap.put("delivery_note",noteedit.getText().toString());
        ref.child(OrderId).updateChildren(productmap);


        startActivity(new Intent(OrdereditActivity.this, DeliveryStatusActivity.class));
        Toast.makeText(OrdereditActivity.this, "successfully ", Toast.LENGTH_SHORT).show();
        finish();
    }



    private void updateOnlyProductInfo() {



    }





    private void getOrderinfo(String orderId) {

        DatabaseReference ordered = FirebaseDatabase.getInstance().getReference().child("Order");
        ordered.child(OrderId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists())
                {
                   Order order = snapshot.getValue(Order.class);
                    addressedit.setText(order.getAddress());
                    phoneedit.setText(order.getContact());
                    noteedit.setText(order.getDelivery_note());


                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }


}