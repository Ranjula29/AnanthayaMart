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

import com.example.app2.ui.ViewHolder.OrderViewHolder;
import com.example.app2.ui.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class DeliveryStatusActivity extends AppCompatActivity {

    private DatabaseReference orderref;
    private RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    private Button update,delete;
/////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_status);

        orderref = FirebaseDatabase.getInstance().getReference().child("Order");


        recyclerView =findViewById(R.id.recycler_menu3);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    protected void onStart() {
        super.onStart();


        FirebaseRecyclerOptions<Order> options =
                new FirebaseRecyclerOptions.Builder<Order>()
                        .setQuery(orderref,Order.class)
                        .build();


        FirebaseRecyclerAdapter<Order, OrderViewHolder>adapter =
                new FirebaseRecyclerAdapter<Order, OrderViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull final Order model) {

                        holder.txtorderproduct.setText(model.getOrderproduct());
                        holder.txtordercode.setText(model.getOrderid());
                        holder.txtaddress.setText(model.getAddress());
                        holder.txtphone.setText(model.getContact());
                        holder.txtquanty.setText(model.getQuantity());
                        holder.txtamount.setText(model.getAmount());
                        holder.txtnote.setText(model.getDelivery_note());

                        update =(Button) findViewById(R.id.orderupdate);
                        holder.update.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(DeliveryStatusActivity.this, OrdereditActivity.class);
                                intent.putExtra("orderid", model.getOrderid());
                                startActivity(intent);
                            }
                        });

                         delete = (Button) findViewById(R.id.orderremove);

                         holder.delete.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View view) {
                                 orderref.child(model.getOrderid()).removeValue()
                                         .addOnCompleteListener(new OnCompleteListener<Void>() {
                                             @Override
                                             public void onComplete(@NonNull Task<Void> task) {
                                                 if (task.isSuccessful()){
                                                     Toast.makeText(DeliveryStatusActivity.this, "Your order was Deleted!", Toast.LENGTH_SHORT).show();
                                                     Intent intent = new Intent(DeliveryStatusActivity.this, DeliveryStatusActivity.class);
                                                     startActivity(intent);
                                                 }

                                             }
                                         });
                             }
                         });





                    }

                    @NonNull
                    @Override
                    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.delivery_status_layout,parent,false);
                        OrderViewHolder holder =new OrderViewHolder(view);
                        return  holder;
                    }
                };




        recyclerView.setAdapter(adapter);
        adapter.startListening();
    }


}

