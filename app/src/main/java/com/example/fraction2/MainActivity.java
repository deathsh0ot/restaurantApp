package com.example.fraction2;

import androidx.appcompat.app.AlertDialog;
import  androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editText;

    TextView textView;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = (EditText) findViewById(R.id.editTextNumberSigned);

        textView = (TextView) findViewById(R.id.textView);

        button = (Button) findViewById(R.id.button);

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