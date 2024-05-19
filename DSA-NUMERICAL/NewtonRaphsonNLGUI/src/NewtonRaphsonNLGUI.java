import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class NewtonRaphsonNLGUI {

    public static double equation1Set1(double x, double y) {
        return x * x + 2 * y * y - 22;
    }

    public static double equation2Set1(double x, double y) {
        return 2 * x * x - x * y + 3 * y - 11;
    }

    public static double partialDerivativeX1Set1(double x, double y) {
        return 2 * x;
    }

    public static double partialDerivativeY1Set1(double x, double y) {
        return 4 * y;
    }

    public static double partialDerivativeX2Set1(double x, double y) {
        return 4 * x - y;
    }

    public static double partialDerivativeY2Set1(double x, double y) {
        return -x + 3;
    }

    public static double equation1Set2(double x, double y) {
        return 120 + (1255 * x) + (6060 * (x * x) * x) - (657 * y) - (919 * Math.pow((y - x), 3));
    }

    public static double equation2Set2(double x, double y) {
        return -398 - (657 * x) + (919 * Math.pow((y - x), 3)) + (726 * y) + (196 * Math.pow(y, 3));
    }

    public static double partialDerivativeX1Set2(double x, double y) {
        return 1255 + 18180 * x * x - 919 * 3 * Math.pow((y - x), 2) * (-1);
    }

    public static double partialDerivativeY1Set2(double x, double y) {
        return -657 - 919 * 3 * Math.pow((y - x), 2);
    }

    public static double partialDerivativeX2Set2(double x, double y) {
        return -657 + 919 * 3 * Math.pow((y - x), 2) * (-1);
    }

    public static double partialDerivativeY2Set2(double x, double y) {
        return 726 + 3 * 196 * y * y + 919 * 3 * Math.pow((y - x), 2);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Nonlinear System Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel errorLabel = new JLabel("Enter error: ");
        JTextField errorField = new JTextField();
        JLabel xLabel = new JLabel("Enter First Guess (x): ");
        JTextField xField = new JTextField();
        JLabel yLabel = new JLabel("Enter Second Guess (y): ");
        JTextField yField = new JTextField();

        JLabel equationSetLabel = new JLabel("Select Equation Set: ");
        JComboBox<String> equationComboBox = new JComboBox<>();
        equationComboBox.addItem("Set 1: x^2 + 2y^2 = 22, 2x^2 - xy + 3y = 11");
        equationComboBox.addItem("Set 2: Custom Equations (120 + (1255 * x) + (6060 * (x * x) * x) - (657 * y) - (919 * Math.pow((y - x), 3)), -398 - (657 * x) + (919 * Math.pow((y - x), 3)) + (726 * y) + (196 * Math.pow(y, 3)))");

        JButton solveButton = new JButton("Solve");

        String[] columnNames = {"Iteration", "x", "y", "XError", "YError"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable resultTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(resultTable);

        JTextArea resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);

        panel.add(equationSetLabel);
        panel.add(equationComboBox);
        panel.add(errorLabel);
        panel.add(errorField);
        panel.add(xLabel);
        panel.add(xField);
        panel.add(yLabel);
        panel.add(yField);
        panel.add(solveButton);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(resultScrollPane, BorderLayout.SOUTH);

        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double error = Double.parseDouble(errorField.getText());
                    double x = Double.parseDouble(xField.getText());
                    double y = Double.parseDouble(yField.getText());

                    double errorX, errorY;
                    int iterations = 0;
                    tableModel.setRowCount(0); // Clear previous results
                    StringBuilder resultBuilder = new StringBuilder();

                    int selectedSet = equationComboBox.getSelectedIndex();

                    if (selectedSet == 0) {
                        // Solve for Set 1
                        do {
                            double determinant = partialDerivativeX1Set1(x, y) * partialDerivativeY2Set1(x, y)
                                    - partialDerivativeX2Set1(x, y) * partialDerivativeY1Set1(x, y);

                            double deltaX = (-equation1Set1(x, y) * partialDerivativeY2Set1(x, y)
                                    + equation2Set1(x, y) * partialDerivativeY1Set1(x, y)) / determinant;
                            double deltaY = (equation1Set1(x, y) * partialDerivativeX2Set1(x, y)
                                    - equation2Set1(x, y) * partialDerivativeX1Set1(x, y)) / determinant;

                            x += deltaX;
                            y += deltaY;

                            errorX = Math.abs(deltaX);
                            errorY = Math.abs(deltaY);

                            iterations++;
                            tableModel.addRow(new Object[]{iterations, x, y, errorX, errorY});
                        } while (errorX > error || errorY > error);

                        resultBuilder.append("Equation Set 1:\n");
                        resultBuilder.append("x^2 + 2y^2 = 22\n");
                        resultBuilder.append("2x^2 - xy + 3y = 11\n");
                        resultBuilder.append("\nSolution:\n");
                        resultBuilder.append("x = ").append(x).append("\n");
                        resultBuilder.append("y = ").append(y).append("\n");

                    } else if (selectedSet == 1) {
                        // Solve for Set 2
                        do {
                            double determinant = partialDerivativeX1Set2(x, y) * partialDerivativeY2Set2(x, y)
                                    - partialDerivativeX2Set2(x, y) * partialDerivativeY1Set2(x, y);

                            double deltaX = (-equation1Set2(x, y) * partialDerivativeY2Set2(x, y)
                                    + equation2Set2(x, y) * partialDerivativeY1Set2(x, y)) / determinant;
                            double deltaY = (equation1Set2(x, y) * partialDerivativeX2Set2(x, y)
                                    - equation2Set2(x, y) * partialDerivativeX1Set2(x, y)) / determinant;

                            x += deltaX;
                            y += deltaY;

                            errorX = Math.abs(deltaX);
                            errorY = Math.abs(deltaY);

                            iterations++;
                            tableModel.addRow(new Object[]{iterations, x, y, errorX, errorY});
                        } while (errorX > error || errorY > error);

                        resultBuilder.append("Equation Set 2:\n");
                        resultBuilder.append("120 + (1255 * x) + (6060 * (x * x) * x) - (657 * y) - (919 * Math.pow((y - x), 3))\n");
                        resultBuilder.append("-398 - (657 * x) + (919 * Math.pow((y - x), 3)) + (726 * y) + (196 * Math.pow(y, 3))\n");
                        resultBuilder.append("\nSolution:\n");
                        resultBuilder.append("x = ").append(x).append("\n");
                        resultBuilder.append("y = ").append(y).append("\n");
                    }

                    resultArea.setText(resultBuilder.toString());

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Please enter valid numbers for error, x, and y.", "Input Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.setVisible(true);
    }
}