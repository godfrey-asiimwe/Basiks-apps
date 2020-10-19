package com.example.basiks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basiks.R;
import com.example.basiks.model.Payment;

import java.util.Collections;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    List<Payment> data= Collections.emptyList();
    Payment current;
    int currentPos=0;

    public PaymentsAdapter(Context context, List<Payment> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.content_payments, parent,false);
        PaymentsAdapter.MyHolder holder=new PaymentsAdapter.MyHolder(view);
        return holder;
    }


    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        PaymentsAdapter.MyHolder myHolder= (PaymentsAdapter.MyHolder) holder;
        Payment current=data.get(position);

        myHolder.amount.setText(current.getAmount());
        myHolder.receiver.setText(current.getReceiver());
        myHolder.payment_date.setText(current.getDate());

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


    class MyHolder extends RecyclerView.ViewHolder{

        TextView amount;
        TextView receiver;
        TextView payment_date;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            amount= (TextView) itemView.findViewById(R.id.amount);
            receiver= (TextView) itemView.findViewById(R.id.receiver);
            payment_date = (TextView) itemView.findViewById(R.id.payment_date);
        }
    }
}
