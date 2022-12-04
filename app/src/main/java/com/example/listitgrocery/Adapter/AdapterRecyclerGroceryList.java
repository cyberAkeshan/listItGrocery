package com.example.listitgrocery.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listitgrocery.Grocery;
import com.example.listitgrocery.R;

import java.util.ArrayList;

public class AdapterRecyclerGroceryList extends RecyclerView.Adapter<AdapterRecyclerGroceryList.ViewHolder> {

    private  rClickListener listener;
    private ArrayList<Grocery> groceryArrayList;

    public AdapterRecyclerGroceryList(ArrayList<Grocery> groceryArrayList, rClickListener listener){
        this.listener = listener;
        this.groceryArrayList= groceryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_grocerylistrow, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecyclerGroceryList.ViewHolder holder, int position) {
        holder.getTitle().setText(groceryArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return groceryArrayList.size();
    }

    public interface rClickListener{
        void onClick(View v, int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.listname);
            title.setOnClickListener(view1 -> listener.onClick(view1, getAdapterPosition()));
        }
        public TextView getTitle() {
            return title;
        }
    }


}