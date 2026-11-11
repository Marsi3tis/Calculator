package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private double firstNumber = 0;
    private boolean startNewNumber = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.textViewResult);
        int[] numberButtons = {
                R.id.button0,
                R.id.button1,
                R.id.button2,
                R.id.button3,
                R.id.button4,
                R.id.button5,
                R.id.button6,
                R.id.button7,
                R.id.button8,
                R.id.button9,
        };

        for(int buttonId : numberButtons) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(v -> {
               String number = button.getText().toString();
               String current = textViewResult.getText().toString();

               if (current.equals("0") || startNewNumber) {
                   textViewResult.setText(number);
                   startNewNumber = false;
               }
               else {
                   textViewResult.setText(current + number);
               }
            });
        }
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonEquals = findViewById(R.id.buttonEquals);

        buttonAdd.setOnClickListener(v -> {
            firstNumber = Double.parseDouble(textViewResult.getText().toString());
            startNewNumber = true;
        });

        buttonEquals.setOnClickListener(v -> {
            double secondNumber = Double.parseDouble(textViewResult.getText().toString());
            double result = firstNumber + secondNumber;
            if (result == (long) result){
                textViewResult.setText(String.valueOf((long) result));
            }
            startNewNumber = true;
        });
    }
}