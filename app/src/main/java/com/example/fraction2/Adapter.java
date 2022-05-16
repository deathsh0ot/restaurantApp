package com.example.fraction2;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.net.Uri;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends FirebaseRecyclerAdapter<Menu,Adapter.MyViewHolder> {

    public Adapter(@NonNull FirebaseRecyclerOptions<Menu> options) {
        super(options);

    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, @SuppressLint("RecyclerView") final int position, @NonNull final Menu menu1) {
        holder.name.setText(menu1.getName());
        holder.price.setText(menu1.getPrice());
        Picasso.get().load(menu1.getImage()).into(holder.image);
        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.name.getContext()).setContentHolder(new ViewHolder(R.layout.edit_menu))
                        .setExpanded(true,600).create();

                View myview =dialogPlus.getHolderView();
                final EditText url = myview.findViewById(R.id.uURL);
                final EditText uname = myview.findViewById(R.id.uName);
                final EditText uprice = myview.findViewById(R.id.uPrice);
                 Button submit = myview.findViewById(R.id.submit);

                uname.setText(menu1.getName());
                uprice.setText(menu1.getPrice());
                url.setText(menu1.getImage());

                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("Image", url.getText().toString());
                        map.put("Name", uname.getText().toString());
                        map.put("price", uprice.getText().toString());
                        FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app")
                                .getReference().child("Menu")
                                .child(getRef(position).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        dialogPlus.dismiss();
                                    }
                                });


                    }
                });


            }
        });
        holder.deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.image.getContext());
                builder.setTitle("Are you sure that you want to delete this item");
                builder.setMessage("Confirm?");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app").getReference().child("Menu")
                                .child(getRef(position).getKey()).removeValue();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                builder.show();
            }
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView name, price;
        ImageView image,deleteItem, editItem;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.itemName);
            price = itemView.findViewById(R.id.itemPrice);
            image = itemView.findViewById(R.id.itemImage);
            deleteItem = itemView.findViewById(R.id.deleteIcon);
            editItem = itemView.findViewById(R.id.editIcon);
        }
    }
}
