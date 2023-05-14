package com.example.listitgrocery.Activity;

import android.annotation.SuppressLint;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.listitgrocery.Adapter.AdapterRecyclerGroceryList;
import com.example.listitgrocery.Adapter.AdapterRecyclerItem;
import com.example.listitgrocery.GroceryItem;
import com.example.listitgrocery.Grocery;
import com.example.listitgrocery.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GrocerylistActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;
// ...
    private ArrayList<GroceryItem> iList;
    private RecyclerView recyclerView;
    String title;
    String uid;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        uid=getIntent().getStringExtra("uid");
        title = getIntent().getStringExtra("HEADER");
        if(mDatabase.child("users").child(uid).child(title).getKey()==title){
            DatabaseReference listRef= mDatabase.child("users").child(uid).child(title);
            listRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String name = dataSnapshot.child("name").getValue(String.class); // "liste1"

                        ArrayList<GroceryItem> items = new ArrayList<>();
                        DataSnapshot itemsSnapshot = dataSnapshot.child("items");
                        for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                            boolean checked = itemSnapshot.child("checked").getValue(Boolean.class);
                            String itemName = itemSnapshot.child("itemName").getValue(String.class);
                            GroceryItem groceryItem = new GroceryItem(itemName, checked);
                            items.add(groceryItem);
                        }
                        iList=items;
                        updateLayout();
                        System.out.println("Name: " + name);
                        System.out.println("Items: " + items);
                    } else {
                        System.out.println("Liste not found in the database.");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("Error reading list data from database: " + databaseError.getMessage());
                }
            });
        }else{
            iList = new ArrayList<>();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocerylist);
        TextView header = findViewById(R.id.nameTextView);
        header.setText(title);

        Button add = findViewById(R.id.button);
        recyclerView = findViewById(R.id.rItems);

        EditText inputText = findViewById(R.id.editItemName);

        add.setOnClickListener(view -> {
            if(!inputText.getText().toString().isEmpty()){
                setList(inputText.getText());
                updateLayout();
                save();
            }
            inputText.setText("");
        });
    }
    public void save(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Gson gson = new Gson();
        Grocery grocery = new Grocery(title);
        grocery.setItems(iList);
        String json = gson.toJson(grocery);
        Log.d("CREATION",json);
        mDatabase.child("users").child(uid).child(title).setValue(grocery);
    }

    @Override
    public void onBackPressed() {
        save();
        super.onBackPressed();
    }

    private void updateLayout() {
        AdapterRecyclerItem adapter = new AdapterRecyclerItem(iList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }
    private void setList(Editable text) {
        iList.add(new GroceryItem(text.toString(),false));
        //Toast.makeText(GrocerylistActivity.this, s, Toast.LENGTH_SHORT).show();
    }
}
