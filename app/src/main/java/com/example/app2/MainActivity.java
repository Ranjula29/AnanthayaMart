package com.example.app2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button welcome;
    private Button welcome2;
    private Button welcome3;
    private Button welcome4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        welcome=(Button) findViewById(R.id.welcome_button);



        welcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AdminDashboard.class);
                startActivity(intent);
            }
        });

        welcome2=(Button) findViewById(R.id.welcome_button2);

        welcome2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HomePage.class);
                startActivity(intent);
            }
        });

        welcome4=(Button) findViewById(R.id.welcome_button4);

        welcome4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeliveryStatusActivity.class);
                startActivity(intent);
            }
        });
    }
}