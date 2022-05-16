package com.example.fraction2;

import android.content.Context;
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

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;
    ArrayList<Menu> list;

    public Adapter(Context context, ArrayList<Menu> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Menu menu = list.get(position);
        holder.name.setText(menu.getName());
        holder.price.setText(menu.getPrice());
        Picasso.get().load(menu.getImage()).into(holder.image);
        holder.editItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogPlus dialogPlus = DialogPlus.newDialog(holder.image.getContext()).setContentHolder(new ViewHolder(R.layout.edit_menu)).setExpanded(true,1100).create();

                View myview =dialogPlus.getHeaderView();
                EditText url = myview.findViewById(R.id.uURL);
                EditText uname = myview.findViewById(R.id.uName);
                EditText uprice = myview.findViewById(R.id.uPrice);
                Button submit = myview.findViewById(R.id.submit);/*
                menu.setImage(url.toString());
                menu.setName(uname.toString());
                menu.setPrice(uprice.toString());*/
                uname.setText(menu.getName());
                uprice.setText(menu.getPrice());
                url.setText(menu.getImage());
                dialogPlus.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("url", url.getText().toString());
                        map.put("name", uname.getText().toString());
                        map.put("price", uprice.getText().toString());
                       // FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app").getReference("Menu").child(String.valueOf(getItemId(position))).updateChildren(map);


                    }
                });


            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
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
