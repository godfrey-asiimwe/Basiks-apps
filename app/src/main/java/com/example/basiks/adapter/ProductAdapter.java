package com.example.basiks.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.basiks.model.BProduct;

import java.util.Collections;
import java.util.List;
import com.example.basiks.R;

public class ProductAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private LayoutInflater inflater;
    List<BProduct> data= Collections.emptyList();
    BProduct current;
    int currentPos=0;
    private ProductAdapterListener listener;

    // create constructor to innitilize context and data sent from MainActivity
    public ProductAdapter(Context context, List<BProduct> data, ProductAdapterListener listener){
        this.context=context;
        inflater= LayoutInflater.from(context);
        this.data=data;
        this.listener=listener;
    }

    // Inflate the layout when viewholder created
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.container_product, parent,false);
        MyHolder holder=new MyHolder(view);
        return holder;
    }

    // Bind data
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        // Get current position of item in recyclerview to bind data and assign values from list
        MyHolder myHolder= (MyHolder) holder;
        BProduct current=data.get(position);

        myHolder.textProductName.setText(current.getName());
        myHolder.info.setText(current.getInfo());
        myHolder.category.setText(current.getCategory());
        myHolder.textPrice.setText(current.getPrice());
        myHolder.textPrice.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        // load image into imageview using glide
        Glide.with(context).load("http://demo.basiksservices.com/admin/upload/" + current.getImage())
                .placeholder(R.drawable.bg_circle)
                .error(R.drawable.bg_circle)
                .into(myHolder.ivProduct);

    }

    // return total item from List
    @Override
    public int getItemCount() {
        return data.size();
    }


     class MyHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        TextView textProductName;
        ImageView ivProduct;
        TextView info;
        TextView category;
        TextView textPrice;


        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            textProductName= (TextView) itemView.findViewById(R.id.textProductName);
            ivProduct= (ImageView) itemView.findViewById(R.id.ivProduct);
            info = (TextView) itemView.findViewById(R.id.info);
            category = (TextView) itemView.findViewById(R.id.category);
            textPrice = (TextView) itemView.findViewById(R.id.textPrice);

            itemView.setOnClickListener(this);


        }

         @Override
         public void onClick(View view) {
            listener.onItemClicked(getAdapterPosition());
         }
     }

    public interface ProductAdapterListener{
        void onItemClicked(int position);
    }
}
