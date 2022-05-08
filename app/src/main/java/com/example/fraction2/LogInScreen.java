package com.example.fraction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogInScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_screen);
        mAuth = FirebaseAuth.getInstance();

         email = findViewById(R.id.email);
         password = findViewById(R.id.password);
        TextView register = findViewById(R.id.registerText);
        Button loginBtn =  findViewById(R.id.loginButton);

        //adminLogin (Me :) )
        loginBtn.setOnClickListener(view -> {
           if (email.getText().toString().equals("DeathSh0t")&& password.getText().toString().equals("Change123")){
               Toast.makeText(LogInScreen.this,"Sup", Toast.LENGTH_SHORT).show();
           }
           else {
               Toast.makeText(LogInScreen.this, "Woah there buddy", Toast.LENGTH_SHORT).show();
           }
            verify();
        });
        register.setOnClickListener(view -> {
            //creating intent
            Intent intent = new Intent(LogInScreen.this, RegisterScreen.class);
            startActivity(intent);
        });

    }
    private void verify(){
        String emailInput= email.getText().toString().trim();
        String passwordInput = password.getText().toString();

        mAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent1 = new Intent(LogInScreen.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
                else{
                    Toast.makeText(LogInScreen.this,"Please verify your email and password",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}