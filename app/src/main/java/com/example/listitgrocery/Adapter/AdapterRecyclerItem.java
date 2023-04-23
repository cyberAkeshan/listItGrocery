package com.example.listitgrocery.Adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listitgrocery.GroceryItem;
import com.example.listitgrocery.Grocery;
import com.example.listitgrocery.R;

import java.util.ArrayList;

public class AdapterRecyclerItem extends RecyclerView.Adapter<AdapterRecyclerItem.ViewHolder>{
   private ArrayList<GroceryItem> itemArrayList;
    public AdapterRecyclerItem(ArrayList<GroceryItem> itemArrayList){
        this.itemArrayList= itemArrayList;
    }
    @NonNull
    @Override
    public AdapterRecyclerItem.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_itemrow, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerItem.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.getItem().setText(itemArrayList.get(position).getItemName());
        holder.getItem().setOnCheckedChangeListener((compoundButton, b) -> itemArrayList.get(position).setChecked(b));
        holder.getItem().setChecked(itemArrayList.get(position).isChecked());
    }

    @Override
    public int getItemCount() {
        return itemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox item;
        public ViewHolder(View view) {
            super(view);
            item = (CheckBox) view.findViewById(R.id.itemName);
        }
        public CheckBox getItem() {
            return item;
        }
    }
}
