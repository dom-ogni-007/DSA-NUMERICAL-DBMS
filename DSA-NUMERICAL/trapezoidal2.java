package trapezoidal2;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.mariuszgromada.math.mxparser.*;

public class trapezoidal2 {

    @FunctionalInterface
    public interface Function {
        double apply(double x);
    }

    private JFrame frame;
    private JTextField lowerLimitField;
    private JTextField upperLimitField;
    private JTextField intervalsField;
    private JTextField customFunctionField;
    private JTextArea outputArea;
    private JTable table;
    private DefaultTableModel tableModel;
    private JComboBox<String> functionComboBox;

    public trapezoidal2() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setTitle("Trapezoidal Integration Tool");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2, 10, 10));

        JLabel functionLabel = new JLabel("Select a function to integrate:");
        inputPanel.add(functionLabel);

        functionComboBox = new JComboBox<>(new String[]{
                "1. f(x) = 1 / x",
                "2. f(x) = tan(x)",
                "3. f(x) = x * sin(x)",
                "4. f(x) = e^(-x^2)",
                "5. Enter own function"
        });
        inputPanel.add(functionComboBox);

        JLabel lowerLimitLabel = new JLabel("Enter the lower limit (>= 0):");
        inputPanel.add(lowerLimitLabel);

        lowerLimitField = new JTextField();
        inputPanel.add(lowerLimitField);

        JLabel upperLimitLabel = new JLabel("Enter the upper limit:");
        inputPanel.add(upperLimitLabel);

        upperLimitField = new JTextField();
        inputPanel.add(upperLimitField);

        JLabel intervalsLabel = new JLabel("Enter the number of subintervals:");
        inputPanel.add(intervalsLabel);

        intervalsField = new JTextField();
        inputPanel.add(intervalsField);

        JLabel customFunctionLabel = new JLabel("Enter custom function:");
        inputPanel.add(customFunctionLabel);

        customFunctionField = new JTextField();
        customFunctionField.setEnabled(false);
        inputPanel.add(customFunctionField);

        functionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (functionComboBox.getSelectedIndex() == 4) {
                    customFunctionField.setEnabled(true);
                } else {
                    customFunctionField.setEnabled(false);
                }
            }
        });

        JButton calculateButton = new JButton("Calculate");
        inputPanel.add(calculateButton);

        outputArea = new JTextArea();
        outputArea.setEditable(false);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());
        outputPanel.add(new JScrollPane(outputArea), BorderLayout.CENTER);

        tableModel = new DefaultTableModel(new Object[]{"x", "f(x)", "Index"}, 0);
        table = new JTable(tableModel);
        outputPanel.add(new JScrollPane(table), BorderLayout.SOUTH);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(outputPanel, BorderLayout.CENTER);

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performIntegration();
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    trapezoidal2 window = new trapezoidal2();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void performIntegration() {
        try {
            double start = Double.parseDouble(lowerLimitField.getText());
            double end = Double.parseDouble(upperLimitField.getText());
            int n = Integer.parseInt(intervalsField.getText());

            if (start < 0 || end <= start || n <= 0) {
                throw new NumberFormatException();
            }

            int functionChoice = functionComboBox.getSelectedIndex() + 1;

            Function f;
            double trueValue = 0.0;
            boolean customFunction = false;

            switch (functionChoice) {
                case 1:
                    if (start == 0) {
                        throw new ArithmeticException("The function 1/x is undefined at x = 0.");
                    }
                    f = trapezoidal2::f1;
                    trueValue = Math.log(end) - Math.log(start);
                    break;
                case 2:
                    f = trapezoidal2::f2;
                    trueValue = -Math.log(Math.abs(Math.cos(end))) + Math.log(Math.abs(Math.cos(start)));
                    break;
                case 3:
                    f = trapezoidal2::f3;
                    trueValue = (-end * Math.cos(end) + Math.sin(end)) - (-start * Math.cos(start) + Math.sin(start));
                    break;
                case 4:
                    f = trapezoidal2::f4;
                    trueValue = integralF4(start, end);
                    break;
                case 5:
                    String functionInput = customFunctionField.getText();
                    f = x -> {
                        Argument xArg = new Argument("x = " + x);
                        Expression expression = new Expression(functionInput, xArg);
                        return expression.calculate();
                    };
                    customFunction = true;
                    Expression integralExpression = new Expression("int(" + functionInput + ", x, " + start + ", " + end + ")");
                    trueValue = integralExpression.calculate();
                    break;
                default:
                    throw new IllegalArgumentException("Unexpected function choice.");
            }

            double answer = trapezoidal(start, end, n, f);
            double actualError = Double.NaN;
            if (!Double.isNaN(trueValue)) {
                actualError = Math.abs(trueValue - answer);
            }

            double h = (end - start) / n;
            double fDoublePrimeMax = 2.0 / Math.pow(start, 3);
            double fDoublePrimeMin = 2.0 / Math.pow(end, 3);

            double upperErrorBound = Math.pow(h, 2) / 12 * (end - start) * fDoublePrimeMax;
            double lowerErrorBound = Math.pow(h, 2) / 12 * (end - start) * fDoublePrimeMin;

            outputArea.setText("");
            outputArea.append("Numerically computed value: " + answer + "\n");
            if (!Double.isNaN(trueValue)) {
                outputArea.append("True value: " + trueValue + "\n");
                outputArea.append("Actual error: " + actualError + "\n");
            } else if (customFunction) {
                outputArea.append("No true value provided for custom function.\n");
            }
            outputArea.append("Error Upper Bound: " + upperErrorBound + "\n");
            outputArea.append("Error Lower Bound: " + lowerErrorBound + "\n");

            if (!Double.isNaN(actualError) && lowerErrorBound < actualError && actualError < upperErrorBound) {
                outputArea.append("\nThe actual error is within the error bounds.\n");
                outputArea.append(lowerErrorBound + " < " + actualError + " < " + upperErrorBound + "\n");
            } else if (!Double.isNaN(actualError)) {
                outputArea.append("The actual error is not within the error bounds.\n");
                outputArea.append(lowerErrorBound + " < " + actualError + " < " + upperErrorBound + "\n");
            } else {
                outputArea.append("No true value available for custom function. Error bounds not applicable.\n");
            }

            tableModel.setRowCount(0);
            double hStep = (end - start) / n;
            outputArea.append("\nIntegration result:\n");
            outputArea.append(" x | f(x) |  T\n");
            outputArea.append("-----|---------|-------\n");
            for (int i = 0; i <= n; i++) {
                double x = start + i * hStep;
                double fx = f.apply(x);
                tableModel.addRow(new Object[]{x, fx, i});
                outputArea.append(String.format("%4.2f | %6.4f |  %d\n", x, fx, i));
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter valid numeric values.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Function Error", JOptionPane.ERROR_MESSAGE);
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage(), "Unexpected Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Define the functions and their derivatives
    public static double f1(double x) {
        return 1.0 / x;
    }

    public static double f2(double x) {
        return Math.tan(x);
    }

    public static double f3(double x) {
        return x * Math.sin(x);
    }

    public static double f4(double x) {
        return Math.exp(-Math.pow(x, 2));
    }

    public static double trapezoidal(double start, double end, int n, Function f) {
        double h = (end - start) / n;
        double sum = f.apply(start) + f.apply(end);

        for (int i = 1; i < n; i++) {
            double x = start + i * h;
            sum += 2 * f.apply(x);
        }

        return (h / 2) * sum;
    }

    public static double integralF4(double start, double end) {
        Expression integral = new Expression("int(exp(-x^2), x, " + start + ", " + end + ")");
        return integral.calculate();
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
