package com.example.jim.calculator;


import java.util.ArrayDeque;
import java.util.StringTokenizer;
/**
 * Created by Jim on 06/09/2015.
 */
public class ExpressionProcessor {

    ArrayDeque<Double> operands = new ArrayDeque<>();
    ArrayDeque<String> operator = new ArrayDeque<>();
    StringTokenizer tokens;

    public double evaluateExpression(String expression) {
        tokens = new StringTokenizer(expression.replace(" ", ""), " +/*%-)(", true);
        receiveTokens();
        return operands.peek();

    }

    public void receiveTokens() {
        while (tokens.hasMoreTokens()) {
            String token = tokens.nextToken().trim();
            if (token.length() == 0) {
                continue;
            } else if (token.matches("[ +-]")) {
                while (!operator.isEmpty() && operator.peek().matches("[+/*-]")) {
                    processExpression();
                }

                operator.push(token);

            } else if (token.matches("[*/]")) {
                while (!operator.isEmpty() && operator.peek().matches("[*/]")) {
                    processExpression();
                }
                operator.push(token);
            } else if (token.matches("[(]")) {
                operator.push(token);
            } else if (token.matches("[)]")) {
                while (!operator.peek().matches("[(]")) {
                    processExpression();
                }

                operator.pop();
            } else {
                operands.push(Double.parseDouble(token));

            }
        }

        while (!operator.isEmpty()) {
            processExpression();
        }
    }


    public void processExpression() {
        double calculation;

        switch (operator.pop()) {
            case "+":
                calculation = (operands.pop() + operands.pop());
                operands.push(calculation);
                break;
            case "-":
                double operand1 = operands.pop();
                double operand2 = operands.pop();
                calculation = (operand2 - operand1);
                operands.push(calculation);
                break;
            case "/":
                double divisor = operands.pop();
                double dividend = operands.pop();
                calculation = (dividend / divisor);
                operands.push(calculation);
                break;
            case "*":
                calculation = (operands.pop() * operands.pop());
                operands.push(calculation);
                break;
        }
    }
}
