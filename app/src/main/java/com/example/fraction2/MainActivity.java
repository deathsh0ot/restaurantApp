package com.example.fraction2;

import androidx.appcompat.app.AlertDialog;
import  androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    TextView textView;

    Button button;

    Button logOUTBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editTextNumberSigned);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);

        logOUTBtn = (Button) findViewById(R.id.logOutBtn);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String input = editText.getText().toString();
                if(TextUtils.isEmpty(input)){
                    Toast.makeText(MainActivity.this,"donner un numero :)",Toast.LENGTH_LONG).show();

                }
                else if(Integer.parseInt(input) <0){
                    Toast.makeText(MainActivity.this,"donner un numero positif :)",Toast.LENGTH_LONG).show();

                }
                else{
                    int fac = Integer.parseInt(input);
                    textView.setText(""+ String.valueOf(factorielle(fac)));
                }

            }
        });

        logOUTBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                startActivity( new Intent(MainActivity.this, LogInScreen.class));
            }
        });
    }
    private double factorielle (double fac){

       if(fac<2){
            return 1;
        }
        else{
            return fac * factorielle(fac-1);
        }
    }
}