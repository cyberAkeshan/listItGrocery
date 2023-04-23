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

import com.example.listitgrocery.Adapter.AdapterRecyclerGroceryList;
import com.example.listitgrocery.Grocery;
import com.example.listitgrocery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    FirebaseAuth auth;
    FirebaseUser user;
    Button logoutBtn;
    TextView userTextView;
    private ArrayList<Grocery> groceryArrayList;
    private RecyclerView recyclerView;
    private AdapterRecyclerGroceryList.rClickListener clickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        FloatingActionButton addButton = findViewById(R.id.floatingActionButton);
        FloatingActionButton settingsButton = findViewById(R.id.floatingActionButton2);

        recyclerView = findViewById(R.id.r);
        groceryArrayList = new ArrayList<>();

        settingsButton.setOnClickListener(view -> {
        });

        addButton.setOnClickListener(view -> {
            final EditText input = new EditText(MainActivity.this);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Neue Liste");
            builder.setMessage("Name eingeben");
            builder.setView(input);
            builder.setPositiveButton("Erstellen", (dialog, whichButton) -> {
                if(input.getText().toString().isEmpty()==false){
                    setList(input.getText().toString());
                    updateLayout();
                }
            });
            builder.setNegativeButton("Abbrechen", (dialog, whichButton) -> {
                // Do nothing.
            });
            builder.show();
        });
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
            startActivity(intent);
        };
    }
}