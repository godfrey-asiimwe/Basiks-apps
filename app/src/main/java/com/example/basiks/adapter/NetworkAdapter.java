package com.example.basiks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.basiks.R;
import com.example.basiks.model.NetWork;

import java.util.Collections;
import java.util.List;

public class NetworkAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private LayoutInflater inflater;
    List<NetWork> data= Collections.emptyList();
    NetWork current;
    int currentPos=0;

    public NetworkAdapter(Context context, List<NetWork> data){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.content_net_work, parent,false);
        NetworkAdapter.MyHolder holder=new NetworkAdapter.MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        NetworkAdapter.MyHolder myHolder= (NetworkAdapter.MyHolder) holder;
        NetWork current=data.get(position);

        myHolder.firtname.setText(current.getFirstname());
        myHolder.lastname.setText(current.getLastname());
        myHolder.email.setText(current.getEmail());
        myHolder.phone.setText(current.getPhone());

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        TextView firtname;
        TextView lastname;
        TextView email;
        TextView phone;

        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            firtname= (TextView) itemView.findViewById(R.id.firstname);
            lastname= (TextView) itemView.findViewById(R.id.lastname);
            email = (TextView) itemView.findViewById(R.id.email);
            phone = (TextView) itemView.findViewById(R.id.phone);
        }
    }

}
