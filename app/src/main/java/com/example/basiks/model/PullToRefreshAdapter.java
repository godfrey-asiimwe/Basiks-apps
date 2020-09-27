package com.example.basiks.model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.basiks.R;

import java.util.List;

public class PullToRefreshAdapter extends RecyclerView.Adapter<PullToRefreshAdapter.ViewHolder> {

    private Context context;
    private List<Product> pdtList;

    public PullToRefreshAdapter(Context context, List<Product> pdtList) {

        this.context = context;
        this.pdtList = pdtList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.pull_to_refresh_recyclerview_item_layout,viewGroup,false);
        return new PullToRefreshAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Product pdtObj = pdtList.get(position);

        String pdtName = pdtObj.getProductName();
        String weight = pdtObj.getWeight();
        String price = pdtObj.getPrice();

        holder.pdtNameTv.setText(pdtName);
        holder.weightTv.setText(weight);
        holder.priceTv.setText(price);

    }

    @Override
    public int getItemCount() {
        return pdtList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pdtNameTv,weightTv,priceTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pdtNameTv = itemView.findViewById(R.id.pdtNameTv);
            weightTv = itemView.findViewById(R.id.weightTv);
            priceTv = itemView.findViewById(R.id.priceTv);

        }
    }
}
