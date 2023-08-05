package com.berry.groceryapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.berry.groceryapp.model.StockItemData;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private final ArrayList<StockItemData> dataList;

    public MyAdapter(ArrayList<StockItemData> dataList) {
        this.dataList = dataList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StockItemData stockItemData = dataList.get(position);
        holder.tvItemCode.setText(Integer.toString(stockItemData.getItemCode()));
        holder.tvItemName.setText(stockItemData.getItemName());
        holder.tvQtyStock.setText(Integer.toString(stockItemData.getQtyStock()));
        holder.tvPrice.setText(Float.toString(stockItemData.getPrice()));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        MaterialTextView tvItemCode;
        MaterialTextView tvItemName;
        MaterialTextView tvQtyStock;
        MaterialTextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItemCode = itemView.findViewById(R.id.tvItemCode);
            tvItemName = itemView.findViewById(R.id.tvItemName);
            tvQtyStock = itemView.findViewById(R.id.tvQtyStock);
            tvPrice = itemView.findViewById(R.id.tvPrice);
        }
    }

}
