package com.example.app2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.app2.ui.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class UpdateActivity extends AppCompatActivity {

    private DatabaseReference productref2;
    private RecyclerView recyclerView2;
    RecyclerView.LayoutManager layoutManager2;
    private Button update,remove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        productref2 = FirebaseDatabase.getInstance().getReference().child("Products/cake");


        recyclerView2 =findViewById(R.id.recycler_menu2);
        recyclerView2.setHasFixedSize(true);
        layoutManager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(layoutManager2);
    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Products> options =
                new FirebaseRecyclerOptions.Builder<Products>()
                        .setQuery(productref2,Products.class)
                        .build();


        FirebaseRecyclerAdapter<Products, ProductViewHolder> adapter =
                new FirebaseRecyclerAdapter<Products, ProductViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull final Products model)
                    {
                        holder.txtProductName2.setText(model.getPname());
                        Picasso.get().load(model.getImage()).into(holder.imageView2);

                       update = (Button) findViewById(R.id.update);


                       holder.update.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               Intent intent = new Intent(UpdateActivity.this, ProductEditActivity.class);
                               intent.putExtra("pid", model.getPid());
                               startActivity(intent);
                           }
                       });

                       remove = (Button) findViewById(R.id.remove);

                       holder.remove.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               productref2.child(model.getPid()).removeValue()
                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                           @Override
                                           public void onComplete(@NonNull Task<Void> task) {
                                               if (task.isSuccessful()){
                                                   Toast.makeText(UpdateActivity.this, "Product was Deleted!", Toast.LENGTH_SHORT).show();
                                                   Intent intent = new Intent(UpdateActivity.this, UpdateActivity.class);
                                                   startActivity(intent);
                                               }
                                           }
                                       });
                           }
                       });
                    }



                    @NonNull
                    @Override
                    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.update_product_layout,parent,false);
                        ProductViewHolder holder =new ProductViewHolder(view);
                        return holder;
                    }
                };


        recyclerView2.setAdapter(adapter);
        adapter.startListening();
    }


}