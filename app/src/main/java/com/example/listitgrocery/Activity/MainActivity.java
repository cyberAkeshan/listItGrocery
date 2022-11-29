package com.example.listitgrocery.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.listitgrocery.Adapter.AdapterRecycler;
import com.example.listitgrocery.Grocery;
import com.example.listitgrocery.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    private ArrayList<Grocery> gList;
    private RecyclerView recyclerView;
    private AdapterRecycler.rClickListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton addButton = findViewById(R.id.floatingActionButton);
        FloatingActionButton settingsButton = findViewById(R.id.floatingActionButton2);

        recyclerView = findViewById(R.id.r);
        gList = new ArrayList<>();
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClick();
            }
        });
        addButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View view) {
                final EditText input = new EditText(MainActivity.this);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Neue Liste");
                builder.setMessage("Name eingeben");
                builder.setView(input);
                builder.setPositiveButton("Erstellen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        if(input.getText().toString().isEmpty()==false){
                            setList(input.getText().toString());
                            updateLayout();
                        }
                    }
                });
                builder.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // Do nothing.
                    }
                });
                builder.show();
            }
        });

    }

    private void updateLayout() {
        //onItemClick();
        listener = (v, pos) -> {
            Intent intent = new Intent(getApplicationContext(),GrocerylistActivity.class);
            intent.putExtra("HEADER",gList.get(pos).getName());
            startActivity(intent);
        };
        AdapterRecycler adapter = new AdapterRecycler(gList,listener);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

    }

    private void setList(String text) {
            gList.add(new Grocery(text));
    }



    public void onItemClick() {



        /*listener = (v, pos) -> {
            Intent intent = new Intent(getApplicationContext(),GrocerylistActivity.class);
            intent.putExtra("HEADER",gList.get(pos).getName());
            startActivity(intent);
        };*/
    }
}