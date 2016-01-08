package com.example.jim.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.matheclipse.parser.client.eval.DoubleEvaluator;


public class CalculatorActivity extends AppCompatActivity {
    DoubleEvaluator engine = new DoubleEvaluator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

    }

    public void clickButton(View view) {
        switch (view.getId()) {
            case R.id.button0:
                addButtonValueToTextView("0");
                break;
            case R.id.button1:
                addButtonValueToTextView("1");
                break;
            case R.id.button2:
                addButtonValueToTextView("2");
                break;
            case R.id.button3:
                addButtonValueToTextView("3");
                break;
            case R.id.button4:
                addButtonValueToTextView("4");
                break;
            case R.id.button5:
                addButtonValueToTextView("5");
                break;
            case R.id.button6:
                addButtonValueToTextView("6");
                break;
            case R.id.button7:
                addButtonValueToTextView("7");
                break;
            case R.id.button8:
                addButtonValueToTextView("8");
                break;
            case R.id.button9:
                addButtonValueToTextView("9");
                break;
            case R.id.buttonMultiply:
                addButtonValueToTextView("*");
                break;
            case R.id.buttonDivide:
                addButtonValueToTextView("/");
                break;
            case R.id.buttonPlus:
                addButtonValueToTextView("+");
                break;
            case R.id.buttonMinus:
                addButtonValueToTextView("-");
                break;
            case R.id.buttonPeriod:
                addButtonValueToTextView(".");
                break;
            case R.id.buttonOpeningParenthesis:
                addButtonValueToTextView("(");
                break;
            case R.id.buttonClosingParenthesis:
                addButtonValueToTextView(")");
                break;
            case R.id.buttonEquals:
                summarize();
                break;
            case R.id.buttonReset:
                clearTextView();
                break;
            case R.id.buttonBack:
                removeLastDigit();
                break;
        }
    }

    private void removeLastDigit(){
        TextView textView = ((TextView)findViewById(R.id.textView));
        String text = textView.getText().toString();
        if(textView.getText().length() > 1){
            textView.setText(text.substring(0, text.length()-1));
        }else {
            clearTextView();
        }
    }

    private void addButtonValueToTextView(String s) {
        TextView textView = ((TextView)findViewById(R.id.textView));
        boolean endsWithOperator = textView.getText().toString().endsWith("*") || textView.getText().toString().endsWith("/") ||
                textView.getText().toString().endsWith("-") || textView.getText().toString().endsWith("+");
        switch(s){
            case "*":
                if(!endsWithOperator)
                    textView.setText(textView.getText() + s);
                break;
            case "/":
                if(!endsWithOperator)
                    textView.setText(textView.getText() + s);
                break;
            case "-":
                textView.setText(textView.getText() + s);
                break;
            case "+":
                if(!endsWithOperator)
                    textView.setText(textView.getText() + s);
                break;
            default:
                if(!textView.getText().toString().equals("0")){
                    textView.setText(textView.getText() + s);
                }else {
                    textView.setText(s);
                }
                break;
        }

    }
    private void summarize() {
        TextView textView = ((TextView)findViewById(R.id.textView));

        try{
            double result = engine.evaluate(((TextView)findViewById(R.id.textView)).getText().toString());
            textView.setText(result + "");
        }catch(Exception e) {
            Toast.makeText(getApplicationContext(),"Invalid operation", Toast.LENGTH_LONG).show();
        }
    }
    private void clearTextView() {
        ((TextView)findViewById(R.id.textView)).setText("0");
    }
}
