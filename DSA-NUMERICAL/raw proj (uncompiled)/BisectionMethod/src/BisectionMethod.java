import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BisectionMethod {
    public static double function(double x, String equation) {
        return equationparser(equation, x);
    }

    private static double equationparser(String equation, double x) {
        equation = equation.replaceAll("\\s+", "");
        String[] terms = equation.split("(?=[+-])");

        double result = 0.0;
        for (String term : terms) {
            if (term.equals("")) continue;
            term = term.replace("+-", "-");
            double coefficient;
            int power;
            if (term.contains("x")) {
                String[] parts = term.split("x\\^?");
                if (parts[0].equals("") || parts[0].equals("+")) {
                    coefficient = 1.0;
                } else if (parts[0].equals("-")) {
                    coefficient = -1.0;
                } else {
                    coefficient = Double.parseDouble(parts[0]);
                }

                if (parts.length > 1) {
                    power = Integer.parseInt(parts[1]);
                } else {
                    power = 1;
                }
            } else {
                coefficient = Double.parseDouble(term);
                power = 0;
            }
            result += coefficient * Math.pow(x, power);
        }
        return result;
    }

    public static double calculate(String equation, double a, double b, double epsilon, DefaultTableModel tableModel) {
        double Lower = function(a, equation);
        double Upper = function(b, equation);

        if (Lower * Upper >= 0) {
            JOptionPane.showMessageDialog(null, "The function has the same sign at the endpoints. Bisection method cannot be applied.", "Error", JOptionPane.ERROR_MESSAGE);
            return Double.NaN;
        }

        double mid = a;
        int iteration = 0;
        while ((b - a) >= epsilon) {
            mid = (a + b) / 2;
            double fmid = function(mid, equation);
            double error = Math.abs(b - a);
            tableModel.addRow(new Object[]{iteration, a, b, mid, fmid, error});
            if (Math.abs(fmid) < 1e-10) {
                return mid;
            } else if (Lower * fmid < 0) {
                b = mid;
            } else {
                a = mid;
            }
            iteration++;
        }

        return mid;
    }

    public static void createAndShowGUI() {
        JFrame frame = new JFrame("Bisection Method Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel equationLabel = new JLabel("Enter equation:");
        JTextField equationField = new JTextField();

        JLabel lowerBoundLabel = new JLabel("Enter First Guess:");
        JTextField lowerBoundField = new JTextField();

        JLabel upperBoundLabel = new JLabel("Enter Second Guess:");
        JTextField upperBoundField = new JTextField();

        JLabel errorLabel = new JLabel("Enter error:");
        JTextField errorField = new JTextField();

        JButton solveButton = new JButton("Calculate");

        String[] columnNames = {"Iteration", "a", "b", "mid", "f(mid)", "Error"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        panel.add(equationLabel);
        panel.add(equationField);
        panel.add(lowerBoundLabel);
        panel.add(lowerBoundField);
        panel.add(upperBoundLabel);
        panel.add(upperBoundField);
        panel.add(errorLabel);
        panel.add(errorField);
        panel.add(solveButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String equation = equationField.getText();
                    double lowerBound = Double.parseDouble(lowerBoundField.getText());
                    double upperBound = Double.parseDouble(upperBoundField.getText());
                    double error = Double.parseDouble(errorField.getText());

                    tableModel.setRowCount(0); // Clear previous results

                    double root = calculate(equation, lowerBound, upperBound, error, tableModel);
                    JOptionPane.showMessageDialog(null, "Root: " + root, "Result", JOptionPane.INFORMATION_MESSAGE);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Please enter valid numbers.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                createAndShowGUI();
            }
        });
    }
}