package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

public class HomePage extends AppCompatActivity {


    private ImageView cake, flower, hampers, laptops;
    private  ImageView foods, child, watches, electric;
    ViewFlipper v_flip;
    DrawerLayout drawerLayout;
    Button profileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawerLayout = findViewById(R.id.drawer_layout);


        cake = (ImageView) findViewById(R.id.admin_cake);
        flower = (ImageView) findViewById(R.id.admin_flower);
        hampers = (ImageView) findViewById(R.id.admin_hampers);
        laptops = (ImageView) findViewById(R.id.admin_laptop);
        foods = (ImageView) findViewById(R.id.admin_food);
        child = (ImageView) findViewById(R.id.admin_baby);
        watches = (ImageView) findViewById(R.id.watches);
        electric = (ImageView) findViewById(R.id.admin_electric);

        profileBtn = (Button)findViewById(R.id.btnPro);
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomePage.this,ProfileActivity.class));
            }
        });

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

    public void ClickMenu(View view){

        openDrawer(drawerLayout);
    }

    private static void openDrawer(DrawerLayout drawerLayout) {

        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){

        closeDrawer(drawerLayout);
    }

    private static void closeDrawer(DrawerLayout drawerLayout) {

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){

        recreate();
    }
    public void ClickDashboard(View view)
    {
        redirectActivity(this,Hampers.class);
    }

    public static void redirectActivity(Activity activity, Class aClass) {

        Intent intent = new Intent(activity,aClass);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();

        closeDrawer(drawerLayout);
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
