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


import org.matheclipse.parser.client.eval.DoubleEvaluator;
import org.w3c.dom.Text;

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
                setSupportActionBar((Toolbar)findViewById(R.id.my_toolbar));
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
            case R.id.buttonX2:
                addButtonValueToTextView("^2");
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

    private void summarize() {
        TextView textView = ((TextView) findViewById(R.id.result_textView));

        try {
            double result = engine.evaluate(((TextView) findViewById(R.id.textView)).getText().toString());
            textView.setText(String.valueOf(result));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), R.string.invalid_operation, Toast.LENGTH_LONG).show();
        }
    }

    private void clearTextView() {
        ((TextView) findViewById(R.id.textView)).setText("0");
        ((TextView) findViewById(R.id.result_textView)).setText("0");
    }

}
