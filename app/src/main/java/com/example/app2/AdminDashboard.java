package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDashboard extends AppCompatActivity {

    private Button productadding, productedit,viewhome,manageinventory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);



        productadding =(Button) findViewById(R.id.addingProduct);
        productedit =(Button)  findViewById(R.id.managingProduct);
        viewhome =(Button) findViewById(R.id.vistHome);
        manageinventory =(Button)  findViewById(R.id.managinginventory);




        productadding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this,CategoryActivity.class);
                startActivity(intent);
            }
        });

        productedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this,manageProduct.class);
                startActivity(intent);
            }
        });

        viewhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminDashboard.this,HomePage.class);
                startActivity(intent);
            }
        });


    }


}