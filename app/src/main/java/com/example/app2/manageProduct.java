package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class manageProduct extends AppCompatActivity {

    private Button view1;
    private ImageView cakemn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_product);


        view1 =(Button)  findViewById(R.id.View);
        cakemn =(ImageView)  findViewById(R.id.admin_cakemp);




        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manageProduct.this,HomePage.class);
                startActivity(intent);
            }
        });

        cakemn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(manageProduct.this,UpdateActivity.class);
                startActivity(intent);
            }
        });
    }


}