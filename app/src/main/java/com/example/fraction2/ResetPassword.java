package com.example.fraction2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private EditText emailText;
    private Button resetBtn;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        emailText =(EditText) findViewById(R.id.recoveryEmail);
        resetBtn = (Button) findViewById(R.id.RecoveryBtn);

        auth = FirebaseAuth.getInstance();

        resetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword();
            }
        });

    }

    private void resetPassword() {
        String email = emailText.getText().toString().trim();
        if(email.isEmpty() || (!Patterns.EMAIL_ADDRESS.matcher(email).matches())){
            emailText.setError("Please enter a valid email!");
            emailText.requestFocus();
            return;
        }

        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ResetPassword.this,"The reset email was successfully sent", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(ResetPassword.this,"An error occured please try again", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}