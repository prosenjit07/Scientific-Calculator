package com.android.scientificcalculator;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {
    TextView primaryViewObj, secondaryViewObj;
    Button numberButtonObj;
    boolean clearPrimaryView = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        primaryViewObj = findViewById(R.id.primaryViewID);
        secondaryViewObj = findViewById(R.id.secondaryViewID);
    }

    public void numberButtonClicked(View view) {
        numberButtonObj = findViewById(view.getId());

        String tempValue = primaryViewObj.getText().toString();
        String buttonText = numberButtonObj.getText().toString();

        if (clearPrimaryView) {
            tempValue = "0";
            clearPrimaryView = false;
        }

        if (tempValue.equals("0")) {
            if (buttonText.equals(".")) {
                primaryViewObj.setText("0" + buttonText);
            } else {
                primaryViewObj.setText(buttonText);
            }
        } else {
            primaryViewObj.setText(tempValue + buttonText);
        }
    }

    public void backspaceButtonClicked(View view) {
        String tempValue = primaryViewObj.getText().toString();

        if (secondaryViewObj.getText().toString().contains("=")) {
            secondaryViewObj.setText("");
        } else {
            if (tempValue.length() > 1) {
                primaryViewObj.setText(tempValue.substring(0, tempValue.length() - 1));
            } else {
                primaryViewObj.setText("0");
            }
        }
    }

    public void clearButtonClicked(View view) {
        primaryViewObj.setText("0");
        secondaryViewObj.setText("");
    }

    public void clearEntryButtonClicked(View view) {
        String tempValue = secondaryViewObj.getText().toString();

        if (tempValue.contains("=")) {
            secondaryViewObj.setText("");
        }
        primaryViewObj.setText("0");
    }

    public void operatorButtonClicked(View view) {
        numberButtonObj = findViewById(view.getId());

        String tempValue = primaryViewObj.getText().toString();
        if (tempValue.endsWith(".")) {
            tempValue = tempValue.substring(0, tempValue.length() - 1);
        }
        String buttonText = numberButtonObj.getText().toString();

        if (!buttonText.equals("=")) {
            secondaryViewObj.setText(tempValue + " " + buttonText + " ");
        } else {
            String tempValue2 = secondaryViewObj.getText().toString();

            if (!tempValue2.contains("=")) {
                secondaryViewObj.setText(tempValue2 + tempValue + " " + buttonText);

                double num1 = Double.parseDouble(tempValue);
                double num2 = Double.parseDouble(tempValue2.substring(0, tempValue2.indexOf(" ")));
                double result = 0;

                if (tempValue2.contains("+")) {
                    result = num1 + num2;
                } else if (tempValue2.contains("–")) {
                    result = num1 - num2;
                } else if (tempValue2.contains("×")) {
                    result = num1 * num2;
                } else if (tempValue2.contains("÷")) {
                    result = num1 / num2;
                }

                String res = String.valueOf(result);

                // Limit result to 9 digits.
                res = res.substring(0, Math.min(res.length(), 9));

                // Remove trailing zeros from result. e.g: 5.001000 -> 5.001
                res = res.contains(".") ? res.replaceAll("0*$", "").replaceAll("\\.$", "") : res;

                primaryViewObj.setText(res);
            }
        }
        clearPrimaryView = true;
    }
}