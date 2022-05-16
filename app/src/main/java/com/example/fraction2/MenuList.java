package com.example.fraction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuList extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    Adapter myAdapter;
    ArrayList<Menu> list;
    FloatingActionButton fBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_list);

        recyclerView = findViewById(R.id.menuList);
        database = FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app").getReference("Menu");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new Adapter(this,list);
        recyclerView.setAdapter(myAdapter);
        fBtn= (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddMenuItem.class));
            }
        });
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Menu menu = dataSnapshot.getValue(Menu.class);
                    list.add(menu);

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}