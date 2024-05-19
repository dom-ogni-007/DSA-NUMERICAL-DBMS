/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package jacobimethod;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class JacobiMethodGUI extends JFrame {

    private JTextField equationCountField;
    private JTextArea equationArea;
    private JTextArea resultArea;

    public JacobiMethodGUI() {
        setTitle("Jacobi Method Solver");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(0, 1));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel equationCountLabel = new JLabel("Enter the number of equations:");
        inputPanel.add(equationCountLabel);

        equationCountField = new JTextField();
        inputPanel.add(equationCountField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int equationCount = Integer.parseInt(equationCountField.getText());
                displayEquationInputs(equationCount);
            }
        });
        inputPanel.add(submitButton);

        add(inputPanel, BorderLayout.NORTH);

        equationArea = new JTextArea();
        JScrollPane equationScrollPane = new JScrollPane(equationArea);
        add(equationScrollPane, BorderLayout.CENTER);

        JButton solveButton = new JButton("Solve");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveEquations();
            }
        });
        add(solveButton, BorderLayout.SOUTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        add(resultScrollPane, BorderLayout.EAST);
    }

    private void displayEquationInputs(int equationCount) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < equationCount; i++) {
            sb.append("Equation ").append(i + 1).append(": ");
            sb.append("\n");
        }
        equationArea.setText(sb.toString());
    }

    private void solveEquations() {
        try {
            int n = Integer.parseInt(equationCountField.getText());
            double[][] M = parseEquations(n, new StringReader(equationArea.getText()));

            JacobiMethod jacobi = new JacobiMethod(M);

            if (!jacobi.makeDominant()) {
                resultArea.setText("The system isn't diagonally dominant: The method cannot guarantee convergence.");
            } else {
                jacobi.solve();
                resultArea.setText("Solution:\n");
                for (int i = 0; i < M.length; i++) {
                    resultArea.append("X_" + i + " = " + M[i][n] + "\n");
                }
            }
        } catch (NumberFormatException ex) {
            resultArea.setText("Error: Please enter valid numbers in the equations.");
        } catch (IOException ex) {
            resultArea.setText("An error occurred while parsing equations: " + ex.getMessage());
        } catch (Exception ex) {
            resultArea.setText("An error occurred: " + ex.getMessage());
        }
    }

    private double[][] parseEquations(int n, Reader reader) throws IOException {
        double[][] matrix = new double[n][n + 1];
        BufferedReader bufferedReader = new BufferedReader(reader);

        for (int i = 0; i < n; i++) {
            String equation = bufferedReader.readLine();
            String[] parts = equation.split("=");
            String expression = parts[0].trim();
            double constant = Double.parseDouble(parts[1].trim());

            String[] terms = expression.split("\\s*[+\\-]\\s*");
            for (String term : terms) {
                term = term.trim();
                int coefficient = 1;
                if (term.contains("x")) {
                    String[] partsTerm = term.split("x");
                    if (partsTerm[0].length() > 0) {
                        coefficient = Integer.parseInt(partsTerm[0].trim());
                    }
                    int index = Integer.parseInt(partsTerm[1].trim());
                    matrix[i][index] = coefficient;
                } else {
                    constant -= Integer.parseInt(term);
                }
            }
            matrix[i][n] = constant;
        }

        return matrix;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JacobiMethodGUI().setVisible(true);
        });
    }
}



