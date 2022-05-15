package com.example.fraction2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText password;
    private EditText passwordConfirm;
    private EditText email;
    private EditText username;
    FirebaseDatabase database = FirebaseDatabase.getInstance("https://onemproject-d7804-default-rtdb.europe-west1.firebasedatabase.app");
    DatabaseReference myRef = database.getReference("Users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        mAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.newEmail);
        username = findViewById(R.id.username);
        password = findViewById(R.id.newPassword);
        passwordConfirm = findViewById(R.id.confirmPassword);
        Button registerButton = findViewById(R.id.RegisterButton);
        registerButton.setOnClickListener(view -> {
            checkErrors();
        });
        Log.d("why","potato");
        Log.d("why", String.valueOf(FirebaseDatabase.getInstance().getReference()));
    }
    private void checkErrors(){
        String newEmail = email.getText().toString().trim();
        String userName = username.getText().toString().trim();
        String pass  = password.getText().toString();
        String pass2 = passwordConfirm.getText().toString().trim();

        if(newEmail.isEmpty() && pass.isEmpty() ){
            Toast.makeText(RegisterScreen.this,"Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(newEmail.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(newEmail).matches())){
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }
        if(userName.isEmpty()){
            username.setError("Please enter a username");
            username.requestFocus();
            return;
        }
        if (pass.isEmpty() || pass.length()<6){
            password.setError("Password must contain 6 characters or more!");
            password.requestFocus();
            return;

        }

        if (!(pass.equals(pass2))){
            Toast.makeText(RegisterScreen.this,"Please make sure that the passwords match", Toast.LENGTH_SHORT).show();
            return;
        }
        mAuth.createUserWithEmailAndPassword(newEmail,pass).addOnCompleteListener(this, task -> {
            if (task.isSuccessful()) {
                User user = new User(userName,newEmail);
                myRef.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                        .addOnCompleteListener(task1 -> {
                            if (task1.isSuccessful()){
                                Toast.makeText(RegisterScreen.this,"registration successful",Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(RegisterScreen.this,"registration failed", Toast.LENGTH_LONG).show();
                            }
                        });
            } else {
            }
        });
    }
}