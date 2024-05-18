package numerical;
import java.util.Stack;

public class NumericalMethodsMOSS {

    static double evaluatePostfix(String[] postfix, double val) {
        Stack<Double> stack = new Stack<>();
        for (String term : postfix) {
            if (term.equals("x")) {
                stack.push(val);
            } else if (term.matches("-?\\d+(\\.\\d+)?")) { // Matches numbers with optional negative sign and decimals
                stack.push(Double.valueOf(term));
            } else if (term.equals("+") || term.equals("-") || term.equals("*") || term.equals("/")) {
                double secondOperand = stack.pop();
                double firstOperand = stack.pop();
                switch (term) {
                    case "+":
                        stack.push(firstOperand + secondOperand);
                        break;
                    case "-":
                        stack.push(firstOperand - secondOperand);
                        break;
                    case "*":
                        stack.push(firstOperand * secondOperand);
                        break;
                    case "/":
                        stack.push(firstOperand / secondOperand);
                        break;
                }
            } else if (term.contains("x^")) {
                String[] parts = term.split("x\\^");
                double coeff = parts.length > 0 && !parts[0].isEmpty() ? Double.parseDouble(parts[0]) : 1;
                double power = parts.length > 1 ? Double.parseDouble(parts[1]) : 1;
                stack.push(coeff * Math.pow(val, power));
            }
        }
        return stack.pop();
    }

    static String[] infixToPostfix(String[] infix) {
        Stack<String> stack = new Stack<>();
        Stack<String> output = new Stack<>();
        for (String token : infix) {
            if (token.matches("-?\\d+(\\.\\d+)?") || token.equals("x") || token.contains("x^")) {
                output.push(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                while (!stack.isEmpty() && !stack.peek().equals("(")) {
                    output.push(stack.pop());
                }
                stack.pop();
            } else if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                while (!stack.isEmpty() && getPrecedence(token) <= getPrecedence(stack.peek())) {
                    output.push(stack.pop());
                }
                stack.push(token);
            }
        }
        while (!stack.isEmpty()) {
            output.push(stack.pop());
        }
        return output.toArray(String[]::new);
    }

    static int getPrecedence(String operator) {
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
        }
        return -1;
    }
    
    static void performCalculation(String str, double val, double threshold) {
        // Convert the input string to an array of tokens (numbers, operators, variables)
        String[] tokens = str.split("(?<=[-+*/()])|(?=[-+*/()])");
        // Convert infix to postfix
        String[] postfix = infixToPostfix(tokens);
        double result = 0;
        double newResult;
        double percentError;
        int iteration = 0;

        do {
            newResult = evaluatePostfix(postfix, val);
            percentError = Math.abs((newResult - result) / newResult);
            System.out.printf("Iteration %d: Result = %.10f, Error = %.10f\n", ++iteration, newResult, percentError);

            result = newResult;
            val = newResult;

        } while (percentError > threshold && iteration < 1000);

        System.out.printf("Final result after iterations: %.10f\n", newResult);
        System.out.printf("Error: %.10f\n", percentError);
    }
}

