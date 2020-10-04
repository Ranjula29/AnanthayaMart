package com.example.app2.ui.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.app2.ItemClickListner;
import com.example.app2.R;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtProductName, txtProductPrice,txtProductName2;
    public ImageView imageView,imageView2;
    public ItemClickListner listner;
    public Button update,details;
    public Button remove;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);


        imageView = (ImageView) itemView.findViewById(R.id.cake_image);
        txtProductName = (TextView) itemView.findViewById(R.id.product);
        txtProductPrice = (TextView) itemView.findViewById(R.id.productprice);
        imageView2 = (ImageView) itemView.findViewById(R.id.cake_image1);
        txtProductName2 = (TextView) itemView.findViewById(R.id.product1);
        update = (Button) itemView.findViewById(R.id.update);
        details =(Button) itemView.findViewById(R.id.details);
        remove = (Button)itemView.findViewById(R.id.remove);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view) {
        listner.onClick(view,getAdapterPosition(), false);

    }
}