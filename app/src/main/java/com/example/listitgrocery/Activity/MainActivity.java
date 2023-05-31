package com.example.listitgrocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listitgrocery.Adapter.AdapterRecyclerGroceryList;
import com.example.listitgrocery.Grocery;
import com.example.listitgrocery.GroceryItem;
import com.example.listitgrocery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{
    DatabaseReference mDatabase;
    FirebaseAuth auth;
    FirebaseUser user;
    Button logoutBtn;
    TextView userTextView;
    private boolean forList=false;
    private ArrayList<Grocery> groceryArrayList;
    private ArrayList<Grocery> copyArrayList;


    private RecyclerView recyclerView;
    private AdapterRecyclerGroceryList.rClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        user=auth.getCurrentUser();
        logoutBtn=findViewById(R.id.logoutBtn);
        userTextView=findViewById(R.id.userTextView);
        if(user==null){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        }else{
            userTextView.setText(user.getEmail());
        }
        logoutBtn.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();
        });

        FloatingActionButton addButton = findViewById(R.id.floatingActionButton);
        FloatingActionButton settingsButton = findViewById(R.id.floatingActionButton2);

        recyclerView = findViewById(R.id.r);


        if(Objects.equals(mDatabase.child("users").child(user.getUid()).getKey(), user.getUid())){
            DatabaseReference listRef= mDatabase.child("users").child(user.getUid());
            listRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {

                        ArrayList<Grocery> groceries = new ArrayList<>();
                        DataSnapshot itemsSnapshot = dataSnapshot;
                        for (DataSnapshot itemSnapshot : itemsSnapshot.getChildren()) {
                            String listName = itemSnapshot.getKey();
                            Grocery grocery = new Grocery(listName);
                            groceries.add(grocery);
                        }
                        groceryArrayList=groceries;
                        copyArrayList=groceryArrayList;
                        forList=true;
                        updateLayout();
                        System.out.println("Items: " + groceries);
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
            groceryArrayList = new ArrayList<>();
        }
        ArrayList<String> names= new ArrayList<>();

        settingsButton.setOnClickListener(view -> {
        });

        addButton.setOnClickListener(view -> {
            final EditText input = new EditText(MainActivity.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Neue Liste");
            builder.setMessage("Name eingeben");
            builder.setView(input);
            builder.setPositiveButton("Erstellen", (dialog, whichButton) -> {
                if(names.contains(input.getText().toString())){
                    Toast.makeText(MainActivity.this, "Error! Name schon vorhanden!",
                            Toast.LENGTH_SHORT).show();
                }
                if(!input.getText().toString().isEmpty() &&!names.contains(input.getText().toString())){
                    names.add(input.getText().toString());
                    Grocery newListName = new Grocery(input.getText().toString());
                    if(forList==true){
                        groceryArrayList=copyArrayList;
                    }else{
                        groceryArrayList=new ArrayList<>();
                    }groceryArrayList.add(newListName);
                    save(input.getText().toString());
                    updateLayout();
                }
            });
            builder.setNegativeButton("Abbrechen", (dialog, whichButton) -> {
                // Do nothing.
            });
            builder.show();
        });
    }

    private void save(String listName){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Grocery grocery = new Grocery(listName);
        mDatabase.child("users").child(user.getUid()).child(listName).setValue(grocery);
    }
    private void updateLayout() {
        onItemClick();
        AdapterRecyclerGroceryList adapter = new AdapterRecyclerGroceryList(groceryArrayList, clickListener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void setList(String text) {
            groceryArrayList.add(new Grocery(text));
    }

    public void onItemClick() {
        clickListener = (v, pos) -> {
            Intent intent = new Intent(getApplicationContext(),GrocerylistActivity.class);
            intent.putExtra("HEADER", groceryArrayList.get(pos).getName());
            intent.putExtra("uid",user.getUid());
            startActivity(intent);
        };
    }
}