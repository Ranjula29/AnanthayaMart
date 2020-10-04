package com.example.app2.ui.ViewHolder;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app2.ItemClickListner;
import com.example.app2.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtorderproduct, txtordercode,txtaddress,txtphone,txtamount,txtquanty,txtnote;
    public ItemClickListner listner;
    public Button update,delete;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);

        txtnote = (TextView) itemView.findViewById(R.id.delivery_note);
        txtorderproduct = (TextView) itemView.findViewById(R.id.delivery_productname);
        txtordercode = (TextView) itemView.findViewById(R.id.delivery_orderid);
        txtaddress = (TextView) itemView.findViewById(R.id.delivery_address);
        txtphone = (TextView) itemView.findViewById(R.id.delivery_phone);
        txtamount = (TextView) itemView.findViewById(R.id.delivery_amount);
        txtquanty = (TextView) itemView.findViewById(R.id.delivery_quty);
        update = (Button) itemView.findViewById(R.id.orderupdate);
        delete = (Button) itemView.findViewById(R.id.orderremove);
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
