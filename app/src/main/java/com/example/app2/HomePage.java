package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePage extends AppCompatActivity {


    private ImageView cake, flower, hampers, laptops;
    private  ImageView foods, child, watches, electric;
    ViewFlipper v_flip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.nevigation);
        bottomNavigationView.setSelectedItemId(R.id.bottom_home);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.bottom_home:

                    case R.id.bottom_logout:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        finish();
                        overridePendingTransition(0,0);
                        return;
                }
            }
        });





        cake = (ImageView) findViewById(R.id.admin_cake);
        flower = (ImageView) findViewById(R.id.admin_flower);
        hampers = (ImageView) findViewById(R.id.admin_hampers);
        laptops = (ImageView) findViewById(R.id.admin_laptop);
        foods = (ImageView) findViewById(R.id.admin_food);
        child = (ImageView) findViewById(R.id.admin_baby);
        watches = (ImageView) findViewById(R.id.watches);
        electric = (ImageView) findViewById(R.id.admin_electric);


        flower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, flower.class);
                startActivity(intent);
            }
        });


        cake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomePage.this, cake.class);
                startActivity(intent);
            }
        });









        int image[] = {R.drawable.finalcover, R.drawable.c2};

        v_flip = findViewById(R.id.v_flip);

       /* for (int i = 0; i < image.length; i++){
            flipperImage(image[i]);
        } */

        for (int images: image) {
            flipperImage(images);

        }




    }




    public void flipperImage(int image){
        ImageView imageView = new ImageView(this);
        imageView.setBackgroundResource(image);
        v_flip.addView(imageView);
        v_flip.setFlipInterval(4000);
        v_flip.setAutoStart(true);

        v_flip.setInAnimation(this, android.R.anim.slide_in_left);
        v_flip.setOutAnimation(this, android.R.anim.slide_out_right);


    }

}
