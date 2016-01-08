package com.example.jim.calculator;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.matheclipse.parser.client.eval.DoubleEvaluator;

import java.lang.reflect.Field;


public class CalculatorActivity extends AppCompatActivity {
    DoubleEvaluator engine = new DoubleEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.buttonEquals:
                summarize();
                break;
            case R.id.buttonReset:
                clearTextView();
                break;
            case R.id.buttonBack:
                removeLastDigit();
                break;
            default:
                addButtonValueToTextView(((Button) findViewById(view.getId())).getText().toString());
                break;
        }
    }

    private void removeLastDigit() {
        TextView textView = ((TextView) findViewById(R.id.textView));
        String text = textView.getText().toString();
        if (textView.getText().length() > 1) {
            textView.setText(text.substring(0, text.length() - 1));
        } else {
            clearTextView();
        }
    }

    private void addButtonValueToTextView(String s) {
        TextView textView = ((TextView) findViewById(R.id.textView));

        switch (s) {
            case "*":
                textView.setText(textView.getText() + s);
                break;
            case "/":
                textView.setText(textView.getText() + s);
                break;
            case "-":
                textView.setText(textView.getText() + s);
                break;
            case "+":
                textView.setText(textView.getText() + s);
                break;
            default:
                if (!textView.getText().toString().equals("0")) {
                    textView.setText(textView.getText() + s);
                } else {
                    textView.setText(s);
                }
                break;
        }

    }

    private void summarize() {
        TextView textView = ((TextView) findViewById(R.id.textView));

        try {
            double result = engine.evaluate(((TextView) findViewById(R.id.textView)).getText().toString());
            textView.setText(result + "");
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.invalid_operation, Toast.LENGTH_LONG).show();
        }
    }

    private void clearTextView() {
        ((TextView) findViewById(R.id.textView)).setText("0");
    }

    public void exit(MenuItem item) {
        this.finish();
    }
}
