package ru.spbau.farutin.homework08;

import org.jetbrains.annotations.NotNull;

/**
 * Calculator - console application which transforms expression into
 * Reverse Polish notation and calculates its value.
 */
public class Calculator {
    private Stack<Character> operators;
    private Stack<Double> operands;

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("expected format:");
            System.out.println("gradle run -Pargs=\"expr\"");
            return;
        }

        Calculator calculator = new Calculator(new Stack<>(), new Stack<>());
        String input = args[0];

        String transformed = calculator.reversePolishNotation(input);
        double answer = calculator.calculate(transformed);

        System.out.println(input + "=" + answer);
    }

    /**
     * Constructs Calculator with empty stacks.
     * @param stack1 stack to store operators in
     * @param stack2 stack to store operands in
     */
    public Calculator(@NotNull Stack<Character> stack1, @NotNull Stack<Double> stack2) {
        operators = stack1;
        operands = stack2;
    }

    /**
     * Transforms expression which consists of
     * one-digit values and +, -, *, /, (, )
     * into Reverse Polish notation.
     * @param input expression
     * @return expression in RPN
     */
    public String reversePolishNotation(@NotNull String input) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char token = input.charAt(i);

            if ('0' <= token && token <= '9') {
                output.append(token);
            } else if (token == '(') {
                operators.push(token);
            } else if (token == ')') {
                while (operators.top() != '(') {
                    output.append(operators.pop());
                }

                operators.pop();
            } else {
                int currentPriority = priority(token);

                while (!operators.empty() && priority(operators.top()) >= currentPriority) {
                    output.append(operators.pop());
                }

                operators.push(token);
            }
        }

        while (!operators.empty()) {
            output.append(operators.pop());
        }

        return output.toString();
    }

    /**
     * Calculates value of an expression in Reverse Polish notation
     * which consists of one-digit values and +, -, *, /, (, )
     * @param input expression in RPN
     * @return expression value
     */
    public double calculate(@NotNull String input) {
        for (int i = 0; i < input.length(); i++) {
            char token = input.charAt(i);

            if ('0' <= token && token <= '9') {
                operands.push((double)(token - '0'));
            } else {
                double b = operands.pop();
                double a = operands.pop();
                operands.push(apply(token, a, b));
            }
        }

        return operands.top();
    }

    /**
     * Matches operator with its priority.
     * @param operator operator to match
     * @return priority of the given operator
     */
    private int priority(char operator) {
        switch (operator) {
            case '(':
                return 0;
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            default:
                return 3;
        }
    }

    /**
     * Applies operator to arguments.
     * @param operator operator to apply
     * @param a first argument
     * @param b second argument
     * @return result
     */
    private double apply(char operator, double a, double b) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            default:
                return a / b;
        }
    }
}
