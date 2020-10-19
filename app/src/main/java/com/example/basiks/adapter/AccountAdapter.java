package com.example.basiks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.basiks.R;
import com.example.basiks.model.Account;

import java.util.Collections;
import java.util.List;

public class AccountAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<Account> data= Collections.emptyList();
    Account current;
    int currentPos=0;

    public AccountAdapter (Context context,List<Account> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.content_accounts, parent,false);
        AccountAdapter.MyHolder holder=new AccountAdapter.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        AccountAdapter.MyHolder myHolder= (AccountAdapter.MyHolder) holder;
        Account current=data.get(position);

        myHolder.amount.setText(current.getAmount());
        myHolder.productname.setText(current.getName());
        myHolder.date.setText(current.getDate());
        myHolder.stockist.setText(current.getStockist());
        myHolder.billNo.setText(current.getBill_no());
        myHolder.status.setText(current.getStatus());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView amount;
        TextView productname;
        TextView date;
        TextView status;
        TextView stockist;
        TextView billNo;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            amount= (TextView) itemView.findViewById(R.id.amount);
            productname= (TextView) itemView.findViewById(R.id.productname);
            date = (TextView) itemView.findViewById(R.id.date);
            status = (TextView) itemView.findViewById(R.id.status);
            stockist = (TextView) itemView.findViewById(R.id.stockist);
            billNo = (TextView) itemView.findViewById(R.id.billNo);
        }
    }
}
