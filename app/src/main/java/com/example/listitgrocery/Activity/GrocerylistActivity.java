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
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GrocerylistActivity extends AppCompatActivity {
    private ArrayList<GroceryItem> iList;
    private RecyclerView recyclerView;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        iList = new ArrayList<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocerylist);
        TextView header = findViewById(R.id.nameTextView);

        String headerString = getIntent().getStringExtra("HEADER");
        header.setText(headerString);

        Button add = findViewById(R.id.button);
        recyclerView = findViewById(R.id.rItems);

        EditText inputText = findViewById(R.id.editItemName);

        add.setOnClickListener(view -> {
            if(!inputText.getText().toString().isEmpty()){
                setList(inputText.getText());
                updateLayout();
            }
            inputText.setText("");
        });
    }

    @Override
    public void onBackPressed() {
        Gson gson = new Gson();
        String title = getIntent().getStringExtra("HEADER");
        Grocery grocery = new Grocery(title);
        grocery.setItems(iList);
        String json = gson.toJson(grocery);
        Log.d("CREATION",json);
        super.onBackPressed();
    }

    //Type type = new TypeToken<ArrayList<GroceryItem>>(){}.getType();
    //ArrayList<GroceryItem> testiList = gson.fromJson(json, type);
    //Zurückparsen!!!!!!!!!!!!!
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
