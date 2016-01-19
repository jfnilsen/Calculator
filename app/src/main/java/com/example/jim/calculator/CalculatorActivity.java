package com.example.jim.calculator;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import org.matheclipse.parser.client.eval.ComplexEvaluator;
import org.matheclipse.parser.client.eval.DoubleEvaluator;
import org.w3c.dom.Text;

import java.lang.reflect.Field;


public class CalculatorActivity extends AppCompatActivity {
    ComplexEvaluator engine = new ComplexEvaluator();

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /**
             *TODO: make all these settings actually do something useful.
             */
            case R.id.action_settings:
                Toast.makeText(getApplicationContext(),R.string.action_settings, Toast.LENGTH_LONG).show();

                return true;

            case R.id.science_calc_setting:
                Toast.makeText(getApplicationContext(),R.string.science_calculator, Toast.LENGTH_LONG).show();
                setContentView(R.layout.activity_scientific_calculator);
                setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
                return true;

            case R.id.normal_calc_setting:
                Toast.makeText(getApplicationContext(),R.string.normal_calculator, Toast.LENGTH_LONG).show();
                setContentView(R.layout.activity_calculator);
                setSupportActionBar((Toolbar) findViewById(R.id.my_toolbar));
                return true;

            case R.id.unit_calculator:
                Toast.makeText(getApplicationContext(),R.string.unit_calculator, Toast.LENGTH_LONG).show();
                return true;

            case R.id.exit_button:
                this.finish();
                return true;

            case R.id.about:
                AlertDialog alertDialog = new AlertDialog.Builder(CalculatorActivity.this).create();
                alertDialog.setTitle(R.string.about);
                alertDialog.setMessage(getString(R.string.about_message));
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
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
            case R.id.buttonSin:
                addButtonValueToTextView("Sin[");
                break;
            case R.id.buttonCos:
                addButtonValueToTextView("Cos[");
                break;
            case R.id.buttonTan:
                addButtonValueToTextView("Tan[");
                break;
            case R.id.buttonLog:
                addButtonValueToTextView("Log[");
                break;
            case R.id.buttonX2:
                addButtonValueToTextView("^2");
                break;
            case R.id.buttonSqrt:
                addButtonValueToTextView("Sqrt[");
                break;
            case R.id.buttonExp:
                addButtonValueToTextView("Exp[");
                break;
            case R.id.buttonFactorial:
                addButtonValueToTextView("!");
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
                textView.append(s);
                break;
            case "/":
                textView.append(s);
                break;
            case "-":
                textView.append(s);
                break;
            case "+":
                textView.append(s);
                break;
            default:
                if (!textView.getText().toString().equals("0")) {
                    textView.append(s);
                } else {
                    textView.setText(s);
                }
                break;
        }

    }

    private double factorial(String numberAsString){
        int n = Integer.parseInt(numberAsString);
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }


    private void summarize() {
        TextView resultView = ((TextView) findViewById(R.id.result_textView));
        String expression = ((TextView) findViewById(R.id.textView)).getText().toString();
        String[] expressionParts = expression.split("!");

        if(expressionParts.length > 1){
            for(int i = 0; i < (expressionParts.length - 1); i++){
                String[] subParts = expressionParts[i].split("[+[/]-*]");
                double result = factorial(subParts[subParts.length-1]);
                System.out.println();
            }
        }
        try {
            String result = ComplexEvaluator.toString(engine.evaluate(expression));
            resultView.setText(String.valueOf(result));
        } catch (Exception e) {
            Log.d("Errorlog" ,e.getMessage());
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), R.string.invalid_operation, Toast.LENGTH_LONG).show();
        }
    }

    private void clearTextView() {
        ((TextView) findViewById(R.id.textView)).setText("0");
        ((TextView) findViewById(R.id.result_textView)).setText("0");
    }

}
