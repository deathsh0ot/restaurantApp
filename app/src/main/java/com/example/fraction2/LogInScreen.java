package com.example.fraction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        TextView forgotPass = findViewById(R.id.forgot);
        Button loginBtn =  findViewById(R.id.loginButton);

        //adminLogin (Me :) )
        loginBtn.setOnClickListener(view -> {
           if (email.getText().toString().equals("DeathSh0t")&& password.getText().toString().equals("Change123")){
               Toast.makeText(LogInScreen.this,"Sup", Toast.LENGTH_SHORT).show();
           }
            verify();
        });
        register.setOnClickListener(view -> {
            //creating intent
            Intent intent = new Intent(LogInScreen.this, RegisterScreen.class);
            startActivity(intent);
        });
        forgotPass.setOnClickListener(view -> {
            Intent intent = new Intent(LogInScreen.this,ResetPassword.class);
            startActivity(intent);
        });

    }

    private void verify(){
        String emailInput= email.getText().toString().trim();
        String passwordInput = password.getText().toString();

        if(emailInput.isEmpty() && passwordInput.isEmpty() ){
            Toast.makeText(LogInScreen.this,"Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if(emailInput.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches())){
            email.setError("Please enter a valid email!");
            email.requestFocus();
            return;
        }
        if (passwordInput.isEmpty() || passwordInput.length()<6){
            password.setError("Password must contain 6 characters or more!");
            password.requestFocus();
            return;

        }
        FirebaseAuth.AuthStateListener mAuthListener;
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public  void  onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth){
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(LogInScreen.this, UserProfile.class);
                    startActivity(intent);
                    finish();
                }
            }
        };
        mAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent intent1 = new Intent(LogInScreen.this, MenuList.class);
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