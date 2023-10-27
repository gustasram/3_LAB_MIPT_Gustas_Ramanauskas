package com.example.pratestavimas3lab;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView calculatorScreen;
    private StringBuilder currentInput = new StringBuilder();
    private double result = 0;
    private char currentOperator = ' ';
    private DecimalFormat decimalFormat = new DecimalFormat("#,##0.########");

    boolean isCalculatedValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calculatorScreen = findViewById(R.id.calculatorScreen);
        isCalculatedValue = false;
    }

    public void onButtonClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        Log.d("Calculator", "Which Button is Clicked: " + buttonText); // Error Handling

        if (buttonText.matches("[0-9.]")) {
            if (isCalculatedValue) {
                currentInput.setLength(0);
                isCalculatedValue = false;
            }
            currentInput.append(buttonText);
            calculatorScreen.setText(currentInput.toString());
        } else if (buttonText.matches("[+\\-*/]")) {
            isCalculatedValue = false;
            if (currentInput.length() > 0) {
                if (currentInput.charAt(currentInput.length() - 1) == ' ') {
                    // Last operator replacement
                    currentInput.setCharAt(currentInput.length() - 2, buttonText.charAt(0));
                } else {
                    currentInput.append(" " + buttonText + " ");
                }
            }
            calculatorScreen.setText(currentInput.toString());
        } else if (buttonText.equals("=")) {
            calcEquals();
            updateDisplay();
        } else if (buttonText.equals("CE")) {
            clearAll();
            updateDisplay();
        } else if (buttonText.equals("DEL")) {
            deleteLastCharacter();
        } else if (buttonText.equals("√")) {
            calcSquareRoot();
            updateDisplay();
        } else if (buttonText.equals(".")) {
            if (!currentInput.toString().contains(".")) {
                currentInput.append(buttonText);
                calculatorScreen.setText(currentInput.toString());
            }
        } else if (buttonText.equals("+/-")) {
            changeSign();
        }
    }



    // Calculator Screen's updating
    private void updateDisplay() {
        String formattedText = decimalFormat.format(result);
        calculatorScreen.setText(formattedText);
    }
    /*
    private void processOperator(char operator) {
        if (currentOperator != ' ') {
            processEquals();
        }
        result = Double.parseDouble(currentInput.toString());
        currentOperator = operator;
        currentInput.setLength(0);
    }
     */

    private void calcEquals() {
        if (currentInput.length() > 0) {
            String expression = currentInput.toString();

            // Giving the numbers and operators "space", so it doesn't look cramped :)
            String[] parts = expression.split(" ");
            if (parts.length != 3) {
                // Invalid expression handling (seems like it doesn't work)
                currentInput.setLength(0);
                calculatorScreen.setText("Error");
                return;
            }

            try {
                double num1 = Double.parseDouble(parts[0]);
                char operator = parts[1].charAt(0);
                double num2 = Double.parseDouble(parts[2]);

                // Calculations based on the operator clicked
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        isCalculatedValue = true;
                        break;
                    case '-':
                        result = num1 - num2;
                        isCalculatedValue = true;
                        break;
                    case '*':
                        result = num1 * num2;
                        isCalculatedValue = true;
                        break;
                    case '/':
                        if (num2 != 0) {
                            result = num1 / num2;
                            isCalculatedValue = true;
                        } else {
                            // Handling division by 0 (seems like it doesn't work)
                            currentInput.setLength(0);
                            calculatorScreen.setText("Error");
                            return;
                        }
                        break;
                    default:
                        // Handle invalid operator
                        currentInput.setLength(0);
                        calculatorScreen.setText("Error");
                        return;
                }

                // Display result
                updateDisplay();

                // currentInput reset code
                currentOperator = ' ';
                currentInput.setLength(0);
                currentInput.append(result);
            } catch (NumberFormatException e) {
                // Invalid number error handling (seems like it doesn't work)
                currentInput.setLength(0);
                calculatorScreen.setText("Error");
            }
        }
    }



    // Clear all function
    private void clearAll() {
        currentInput.setLength(0);
        result = 0;
        currentOperator = ' ';
    }
    // Last Character calculating function
    private void deleteLastCharacter() {
        if (currentInput.length() > 0) {
            Log.d("Calculator", "Before Delete: " + currentInput.toString()); // Error Handling
            currentInput.deleteCharAt(currentInput.length() - 1);
            calculatorScreen.setText(currentInput.toString());
            Log.d("Calculator", "After Delete: " + currentInput.toString()); // Error Handling
        }
    }
    // Square Root calculating function
    private void calcSquareRoot() {
        if (currentInput.length() > 0) {
            try {
                double value = Double.parseDouble(currentInput.toString());
                result = Math.sqrt(value);
                currentInput.setLength(0);
                currentInput.append(result);
                isCalculatedValue = true;
            } catch (NumberFormatException e) {
                // Handle invalid number format
                currentInput.setLength(0);
                calculatorScreen.setText("Error");
            }
        }
    }
    // Changing the number from positive to negative and vice versa
    private void changeSign() {
        if (currentInput.length() > 0) {
            try {
                double currentValue = Double.parseDouble(currentInput.toString());
                double newValue = -currentValue;

                if (Math.floor(newValue) == newValue) {
                    currentInput.setLength(0);
                    currentInput.append(Integer.toString((int) newValue));
                } else {
                    currentInput.setLength(0);
                    currentInput.append(Double.toString(newValue));
                }

                calculatorScreen.setText(currentInput.toString());
            } catch (NumberFormatException e) {
                // Handle invalid number format
                currentInput.setLength(0);
                calculatorScreen.setText("Error");
            }
        }
    }

}