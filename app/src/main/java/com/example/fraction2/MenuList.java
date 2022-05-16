package com.example.fraction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter myAdapter;
    FloatingActionButton fBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        recyclerView = findViewById(R.id.menuList);
        database = FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app").getReference("Menu");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseRecyclerOptions<Menu> options =
                new FirebaseRecyclerOptions.Builder<Menu>()
                        .setQuery(FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Menu"), Menu.class)
                        .build();
        myAdapter = new Adapter(options);
        recyclerView.setAdapter(myAdapter);
        fBtn= (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddMenuItem.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        myAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        myAdapter.stopListening();
    }
}