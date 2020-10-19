package com.example.basiks.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import com.example.basiks.R;

public class DashBoardAdapter  extends RecyclerView.Adapter<DashBoardAdapter.ViewHolder> {

    private List<DashBoardModel> myListList;
    private Context ct;
    private onImageClick onImageClick;

    public DashBoardAdapter(List<DashBoardModel> myListList, Context ct,onImageClick onImageClick) {
        this.myListList = myListList;
        this.ct = ct;
        this.onImageClick=onImageClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.content_dash_board,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DashBoardModel myList=myListList.get(position);
        holder.imageView.setImageDrawable(ct.getResources().getDrawable(myList.getImage()));

    }

    @Override
    public int getItemCount() {
        return myListList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=(ImageView)itemView.findViewById(R.id.myimage);
        }

        @Override
        public void onClick(View view) {
            onImageClick.onImageClicked(getAdapterPosition());
        }
    }

    public interface onImageClick{
        void onImageClicked(int position);
    }

}
