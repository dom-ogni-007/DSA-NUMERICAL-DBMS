/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package simpsonsrule;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mariuszgromada.math.mxparser.*;
import simpsonsrule.SimpsonsRule;

public class SimpsonsRuleGUI extends JFrame {

    private JTextField startField;
    private JTextField endField;
    private JTextField segmentsField;
    private JComboBox<String> functionComboBox;
    private JTextField customFunctionField;
    private JTextArea resultArea;

    public SimpsonsRuleGUI() {
        setTitle("Simpson's Rule Integration");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Lower Limit:"));
        startField = new JTextField();
        inputPanel.add(startField);

        inputPanel.add(new JLabel("Upper Limit:"));
        endField = new JTextField();
        inputPanel.add(endField);

        inputPanel.add(new JLabel("Number of Subintervals:"));
        segmentsField = new JTextField();
        inputPanel.add(segmentsField);

        inputPanel.add(new JLabel("Select Function:"));
        String[] functions = {"1 / x", "tan(x)", "x * sin(x)", "e^(-x^2)", "Custom"};
        functionComboBox = new JComboBox<>(functions);
        inputPanel.add(functionComboBox);

        inputPanel.add(new JLabel("Custom Function (if selected):"));
        customFunctionField = new JTextField();
        inputPanel.add(customFunctionField);

        JButton calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);

        add(inputPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performIntegration();
            }
        });
    }

    private void performIntegration() {
        try {
            double start = Double.parseDouble(startField.getText());
            double end = Double.parseDouble(endField.getText());
            int n = Integer.parseInt(segmentsField.getText());
            int functionChoice = functionComboBox.getSelectedIndex();
            String customFunctionInput = customFunctionField.getText();

            if (start < 0 || end <= start || n <= 0) {
                resultArea.setText("Invalid input. Please enter valid values.");
                return;
            }

            SimpsonsRule.Function f;
            SimpsonsRule.Function fFourth;
            double trueValue = 0.0; // Placeholder for true value
            boolean customFunction = false;

            switch (functionChoice) {
                case 0:
                    if (start == 0) {
                        resultArea.setText("The function 1/x is undefined at x = 0. Please enter a valid lower limit.");
                        return;
                    }
                    f = SimpsonsRule::f1;
                    fFourth = SimpsonsRule::f1_fourth;
                    trueValue = Math.log(end) - Math.log(start); // Integral of 1/x is ln(x)
                    break;
                case 1:
                    f = SimpsonsRule::f2;
                    fFourth = SimpsonsRule::f2_fourth;
                    trueValue = -Math.log(Math.abs(Math.cos(end))) + Math.log(Math.abs(Math.cos(start))); // Integral of tan(x) is -ln|cos(x)|
                    break;
                case 2:
                    f = SimpsonsRule::f3;
                    fFourth = SimpsonsRule::f3_fourth;
                    trueValue = (-end * Math.cos(end) + Math.sin(end)) - (-start * Math.cos(start) + Math.sin(start)); // Integral of x*sin(x) is x*sin(x) - cos(x)
                    break;
                case 3:
                    f = SimpsonsRule::f4;
                    fFourth = SimpsonsRule::f4_fourth;
                    trueValue = SimpsonsRule.integralF4(start, end); // Approximation for integral of e^(-x^2) from start to end
                    break;
                case 4:
                    f = x -> {
                        Argument xArg = new Argument("x = " + x);
                        Expression expression = new Expression(customFunctionInput, xArg);
                        return expression.calculate();
                    };
                    customFunction = true;
                    fFourth = x -> Double.NaN; // For custom functions, the fourth derivative is not available
                    Expression integralExpression = new Expression("int(" + customFunctionInput + ", x, " + start + ", " + end + ")");
                    trueValue = integralExpression.calculate();
                    break;
                default:
                    resultArea.setText("Unexpected error. Please try again.");
                    return;
            }

            double answer = SimpsonsRule.simpsons(start, end, n, f);
            double actualError = Double.NaN; // Placeholder for actual error
            if (!Double.isNaN(trueValue)) {
                actualError = Math.abs(trueValue - answer);
            }

            // Calculate error bounds
            double h = (end - start) / n;
            double maxFourthDerivative = Double.NEGATIVE_INFINITY;
            double minFourthDerivative = Double.POSITIVE_INFINITY;
            for (int i = 0; i <= n; i++) {
                double x = start + i * h;
                double fourthDerivativeValue = fFourth.apply(x);
                if (fourthDerivativeValue > maxFourthDerivative) {
                    maxFourthDerivative = fourthDerivativeValue;
                }
                if (fourthDerivativeValue < minFourthDerivative) {
                    minFourthDerivative = fourthDerivativeValue;
                }
            }
            double upperErrorBound = Math.abs(Math.pow(end - start, 5) / (180 * Math.pow(n, 4)) * maxFourthDerivative);
            double lowerErrorBound = Math.abs(Math.pow(end - start, 5) / (180 * Math.pow(n, 4)) * minFourthDerivative);

            // Print the results
            resultArea.setText("Numerically computed value: " + answer + "\n");
            if (!Double.isNaN(trueValue)) {
                resultArea.append("True value: " + trueValue + "\n");
                resultArea.append("Actual error: " + actualError + "\n");
            } else if (customFunction) {
                resultArea.append("No true value provided for custom function.\n");
            }
            resultArea.append("Error Upper Bound: " + upperErrorBound + "\n");
            resultArea.append("Error Lower Bound: " + lowerErrorBound + "\n");

            // Check if the actual error is within the error bounds
            if (!Double.isNaN(actualError) && lowerErrorBound < actualError && actualError < upperErrorBound) {
                resultArea.append("\nThe actual error is within the error bounds.\n");
                resultArea.append(lowerErrorBound + " < " + actualError + " < " + upperErrorBound);
            } else if (!Double.isNaN(actualError)) {
                resultArea.append("The actual error is not within the error bounds.\n");
                resultArea.append(lowerErrorBound + " < " + actualError + " < " + upperErrorBound);
            } else {
                resultArea.append("No true value available for custom function. Error bounds not applicable.");
            }

        } catch (NumberFormatException e) {
            resultArea.setText("Invalid input. Please enter valid numbers.");
        } catch (Exception e) {
            resultArea.setText("An error occurred: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new SimpsonsRuleGUI().setVisible(true);
        });
    }
}
