package com.example.fraction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddMenuItem extends AppCompatActivity {

    EditText name, price, url;
    Button submit, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_menu_item);
        url = findViewById(R.id.URL);
        price = findViewById(R.id.price);
        name = findViewById(R.id.Name);
        submit = findViewById(R.id.submitBtn);
        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),MenuList.class));
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insert();
            }

            private void insert() {

                Map<String,Object> map=new HashMap<>();
                map.put("Image",url.getText().toString());
                map.put("Name",name.getText().toString());
                map.put("price",price.getText().toString());
                FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Menu").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                name.setText("");
                                url.setText("");
                                price.setText("");
                                Toast.makeText(getApplicationContext(),"new menu Item added :D",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(),"operation Failed",Toast.LENGTH_LONG).show();
                            }
                        });
            }
        });


    }
}