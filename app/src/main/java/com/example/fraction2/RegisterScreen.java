package com.example.fraction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterScreen extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText password;
    private EditText passwordConfirm;
    private EditText email;
    private EditText username;
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
        mAuth.createUserWithEmailAndPassword(newEmail,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(userName,newEmail);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(RegisterScreen.this,"registration successful",Toast.LENGTH_LONG).show();
                                    }
                                    else{
                                        Toast.makeText(RegisterScreen.this,"registration failed", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                } else {
                }
            }
        });
    }
}