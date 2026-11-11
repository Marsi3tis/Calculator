package com.example.calculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private double firstNumber = 0;
    private boolean startNewNumber = false;
    private String operation = "";
    private double memoryValue = 0;

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

        for (int buttonId : numberButtons) {
            Button button = findViewById(buttonId);
            button.setOnClickListener(v -> {
                String number = button.getText().toString();
                String current = textViewResult.getText().toString();

                if(current.equals("0") || startNewNumber) {
                    textViewResult.setText(number);
                    startNewNumber = false;
                }
                else{
                    textViewResult.setText(current + number);
                }
            });
        }
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonSubtract = findViewById(R.id.buttonSubtract);
        Button buttonMultiply = findViewById(R.id.buttonMultiply);
        Button buttonDivide = findViewById(R.id.buttonDivide);
        Button buttonEquals = findViewById(R.id.buttonEquals);

        buttonAdd.setOnClickListener(v -> selectOperation("+"));
        buttonSubtract.setOnClickListener(v -> selectOperation("-"));
        buttonMultiply.setOnClickListener(v -> selectOperation("*"));
        buttonDivide.setOnClickListener(v -> selectOperation("/"));

        buttonEquals.setOnClickListener(v -> {
            if(operation.isEmpty() || startNewNumber) {
                return;
            }
            double secondNumber = Double.parseDouble(
                    textViewResult.getText().toString()
            );
            double result;

            switch (operation) {
                case "+":
                    result = firstNumber + secondNumber;
                    break;
                case "-":
                    result = firstNumber - secondNumber;
                    break;
                case "*":
                    result = firstNumber * secondNumber;
                    break;
                case "/":
                    if (secondNumber == 0) {
                        textViewResult.setText("Klaida");
                        startNewNumber = true;
                        operation = "";
                        return;
                    }
                    result = firstNumber / secondNumber;
                    break;
                default:
                    return;
            }

            textViewResult.setText(formatResult(result));

            startNewNumber = true;
            operation = "";
        });
        Button buttonSqrt = findViewById(R.id.buttonSqrt);
        buttonSqrt.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                return;
            }
            if(!operation.isEmpty() && startNewNumber){
                return;
            }
            double number = Double.parseDouble(current);

            if (number < 0) {
                textViewResult.setText("Klaida");
                firstNumber = 0;
                operation = "";
                startNewNumber = true;
                return;
            }

            double result = Math.sqrt(number);

            textViewResult.setText(formatResult(result));

            startNewNumber = operation.isEmpty();
        });

        Button buttonClear = findViewById(R.id.buttonClear);
        Button buttonCE = findViewById(R.id.buttonCE);
        Button buttonBackspace = findViewById(R.id.buttonBackspace);
        Button buttonSign = findViewById(R.id.buttonSign);

        buttonClear.setOnClickListener(v -> {
            textViewResult.setText("0");
            firstNumber = 0;
            operation = "";
            startNewNumber = true;
        });

        buttonCE.setOnClickListener(v -> {
            textViewResult.setText("0");
            startNewNumber = false;
        });

        buttonBackspace.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                textViewResult.setText("0");
                firstNumber = 0;
                operation = "";
                startNewNumber = true;
                return;
            }

            if (startNewNumber && !operation.isEmpty()) {
                return;
            }

            if (current.length() > 1) {
                current = current.substring(0, current.length() - 1);
            }
            else{
                current = "0";
            }

            if (current.equals("-")) {
                current = "0";
            }

            textViewResult.setText(current);
            startNewNumber = false;
        });

        buttonSign.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                return;
            }

            if (startNewNumber && !operation.isEmpty()) {
                return;
            }

            if (current.equals("0")) {
                return;
            }

            if (current.startsWith("-")) {
                current = current.substring(1);
            }
            else{
                current = "-" + current;
            }
            textViewResult.setText(current);
            startNewNumber = false;
        });

        Button buttonDecimal = findViewById(R.id.buttonDecimal);
        buttonDecimal.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (startNewNumber || current.equals("Klaida")) {
                textViewResult.setText("0.");
                startNewNumber = false;
                return;
            }

            if (!current.contains(".")) {
                textViewResult.setText(current + ".");
            }
        });

        Button buttonPercent = findViewById(R.id.buttonPercent);
        buttonPercent.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                return;
            }

            if (startNewNumber && !operation.isEmpty()) {
                return;
            }
            double number = Double.parseDouble(current);
            double result;

            if (operation.equals("+") || operation.equals("-")) {
                result = firstNumber * number / 100;
            } else {
                result = number / 100;
            }

            textViewResult.setText(formatResult(result));

            startNewNumber = operation.isEmpty();
        });

        Button buttonReciprocal = findViewById(R.id.buttonReciprocal);
        buttonReciprocal.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                return;
            }

            if (startNewNumber && !operation.isEmpty()) {
                return;
            }
            double number = Double.parseDouble(current);

            if (number == 0) {
                textViewResult.setText("Klaida");
                startNewNumber = true;
                operation = "";
                return;
            }
            double result = 1 / number;

            textViewResult.setText(formatResult(result));

            startNewNumber = operation.isEmpty();
        });

        Button buttonMC = findViewById(R.id.buttonMC);
        Button buttonMR = findViewById(R.id.buttonMR);
        Button buttonMS = findViewById(R.id.buttonMS);
        Button buttonMPlus = findViewById(R.id.buttonMPlus);
        Button buttonMMinus = findViewById(R.id.buttonMMinus);
        buttonMC.setOnClickListener(v -> {
            memoryValue = 0;
        });

        buttonMR.setOnClickListener(v -> {
            textViewResult.setText(formatResult(memoryValue));
            startNewNumber = operation.isEmpty();
        });

        buttonMS.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                return;
            }
            memoryValue = Double.parseDouble(current);
            startNewNumber = operation.isEmpty();
        });

        buttonMPlus.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                return;
            }
            double number = Double.parseDouble(current);
            memoryValue += number;
            startNewNumber = operation.isEmpty();
        });

        buttonMMinus.setOnClickListener(v -> {
            String current = textViewResult.getText().toString();
            if (current.equals("Klaida")) {
                return;
            }
            double number = Double.parseDouble(current);
            memoryValue -= number;
            startNewNumber = operation.isEmpty();
        });


    }

    private void selectOperation(String selectedOperation) {
        String current = textViewResult.getText().toString();
        if (current.equals("Klaida")) {
            return;
        }
        double number = Double.parseDouble(current);

        if (!operation.isEmpty() && !startNewNumber) {
            double result = 0;
            switch (operation) {
                case "+":
                    result = firstNumber + number;
                    break;
                case "-":
                    result = firstNumber - number;
                    break;

                case "*":
                    result = firstNumber * number;
                    break;

                case "/":
                    if (number == 0) {
                        textViewResult.setText("Klaida");
                        operation = "";
                        firstNumber = 0;
                        startNewNumber = true;
                        return;
                    }
                    result = firstNumber / number;
                    break;

            }
            firstNumber = result;

            textViewResult.setText(formatResult(result));

        } else if (operation.isEmpty()) {
            firstNumber = number;
        }

        operation = selectedOperation;
        startNewNumber = true;
    }
    private String formatResult(double number) {
        if(Double.isNaN(number) || Double.isInfinite(number)){
            return "Klaida";
        }
        BigDecimal result = BigDecimal.valueOf(number).round(new MathContext(12, RoundingMode.HALF_UP)).stripTrailingZeros();
        return result.toPlainString();
    }
}