package com.example.app2;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CategoryActivity extends AppCompatActivity {

    private ImageView cake, flower, hampers, laptops;
    private  ImageView foods, child, watches, electric,home;
    private Button productMn, productIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);



        cake = (ImageView) findViewById(R.id.admin_cake);
        flower = (ImageView) findViewById(R.id.admin_flower);
        hampers = (ImageView) findViewById(R.id.admin_hampers);
        laptops = (ImageView) findViewById(R.id.admin_laptop);
        foods = (ImageView) findViewById(R.id.admin_food);
        child = (ImageView) findViewById(R.id.admin_baby);
        watches = (ImageView) findViewById(R.id.watches);
        electric = (ImageView) findViewById(R.id.admin_electric);
        productMn =(Button) findViewById(R.id.productmnBtn);
        productIn =(Button)  findViewById(R.id.invenbtn);
        home = (ImageView) findViewById(R.id.homeadd) ;



        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AdminDashboard.class);
                startActivity(intent);
            }
        });


        productMn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,manageProduct.class);
                startActivity(intent);
            }
        });

        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","cake");
                startActivity(intent);
            }
        });

        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","flower");
                startActivity(intent);

            }
        });


        hampers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","hampers");
                startActivity(intent);

            }
        });



        laptops.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","laptops");
                startActivity(intent);

            }
        });


        foods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","foods");
                startActivity(intent);

            }
        });


        child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","child");
                startActivity(intent);

            }
        });



        watches.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","watches");
                startActivity(intent);

            }
        });

        electric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CategoryActivity.this,AddProduct.class);
                intent.putExtra("category","electric");
                startActivity(intent);

            }
        });
    }



}