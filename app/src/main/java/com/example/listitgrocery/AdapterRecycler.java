package com.example.listitgrocery;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterRecycler extends RecyclerView.Adapter<AdapterRecycler.ViewHolder> {
    public AdapterRecycler(ArrayList<Grocery> groceryArrayList){
        this.groceryArrayList= groceryArrayList;
    }
    ArrayList<Grocery> groceryArrayList = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.layout_recyclerlistview, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterRecycler.ViewHolder holder, int position) {
        holder.getTitle().setText(groceryArrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return groceryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.listname);
        }
        public TextView getTitle() {
            return title;
        }
    }
}