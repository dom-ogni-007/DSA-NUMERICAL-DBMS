import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class JacobiMethod extends JFrame {
    private JPanel matrixPanel;
    private JTextField[][] matrixFields;
    private JTextField epsilonField;
    private JTextArea resultArea;
    private JTable iterationTable;
    private double[][] M;

    public static final int MAX_ITERATIONS = 100;

    public JacobiMethod() {
        setTitle("Jacobi Method");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(3, 1));

        JPanel equationsPanel = new JPanel();
        equationsPanel.setLayout(new FlowLayout());
        equationsPanel.add(new JLabel("Set number of equations:"));
        JTextField numEquationsField = new JTextField(10);
        equationsPanel.add(numEquationsField);

        JButton setEquationsButton = new JButton("Set");
        setEquationsButton.addActionListener(e -> {
            int n = Integer.parseInt(numEquationsField.getText());
            setMatrixFields(n);
        });
        equationsPanel.add(setEquationsButton);

        inputPanel.add(equationsPanel);

        matrixPanel = new JPanel();
        inputPanel.add(matrixPanel);

        JPanel epsilonPanel = new JPanel();
        epsilonPanel.setLayout(new FlowLayout());
        epsilonPanel.add(new JLabel("Error:"));
        epsilonField = new JTextField(10);
        epsilonPanel.add(epsilonField);

        JButton solveButton = new JButton("Calculate");
        solveButton.addActionListener(new SolveButtonListener());
        epsilonPanel.add(solveButton);

        inputPanel.add(epsilonPanel);

        add(inputPanel, BorderLayout.NORTH);

        JPanel outputPanel = new JPanel();
        outputPanel.setLayout(new BorderLayout());

        iterationTable = new JTable(new DefaultTableModel(new Object[]{"Iteration", "x1", "x2", "x3", "Relative Error"}, 0));
        JScrollPane iterationScrollPane = new JScrollPane(iterationTable);
        outputPanel.add(iterationScrollPane, BorderLayout.CENTER);

        resultArea = new JTextArea(5, 20);
        JScrollPane resultScrollPane = new JScrollPane(resultArea);
        outputPanel.add(resultScrollPane, BorderLayout.SOUTH);

        add(outputPanel, BorderLayout.CENTER);
    }

    private void setMatrixFields(int n) {
        matrixPanel.removeAll();
        matrixPanel.setLayout(new GridLayout(n, n + 1));

        matrixFields = new JTextField[n][n + 1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n + 1; j++) {
                matrixFields[i][j] = new JTextField(5);
                matrixPanel.add(matrixFields[i][j]);
            }
        }

        matrixPanel.revalidate();
        matrixPanel.repaint();
    }

    private class SolveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int n = matrixFields.length;
            M = new double[n][n + 1];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n + 1; j++) {
                    M[i][j] = Double.parseDouble(matrixFields[i][j].getText());
                }
            }

            double epsilon = Double.parseDouble(epsilonField.getText());

            if (!makeDominant()) {
                JOptionPane.showMessageDialog(null, "The system isn't diagonally dominant: The method cannot guarantee convergence.");
            }

            solve(epsilon);
        }
    }

    public boolean transformToDominant(int r, boolean[] V, int[] R) {
        int n = M.length;
        if (r == M.length) {
            double[][] T = new double[n][n + 1];
            for (int i = 0; i < R.length; i++) {
                System.arraycopy(M[R[i]], 0, T[i], 0, n + 1);
            }

            M = T;

            return true;
        }

        for (int i = 0; i < n; i++) {
            if (V[i]) continue;

            double sum = 0;

            for (int j = 0; j < n; j++)
                sum += Math.abs(M[i][j]);

            if (2 * Math.abs(M[i][r]) > sum) {
                V[i] = true;
                R[r] = i;

                if (transformToDominant(r + 1, V, R))
                    return true;

                V[i] = false;
            }
        }

        return false;
    }

    public boolean makeDominant() {
        boolean[] visited = new boolean[M.length];
        int[] rows = new int[M.length];

        Arrays.fill(visited, false);

        return transformToDominant(0, visited, rows);
    }

public void solve(double epsilon) {
    DefaultTableModel iterationModel = (DefaultTableModel) iterationTable.getModel();
    iterationModel.setRowCount(0);

    int iterations = 0;
    int n = M.length;

    double[] X = new double[n]; // Approximations
    double[] P = new double[n]; // Prev
    Arrays.fill(X, 0);

    // Record the initial approximation (iteration 0)
    Object[] initialRowData = new Object[n + 2];
    initialRowData[0] = iterations;
    for (int i = 0; i < n; i++) {
        initialRowData[i + 1] = X[i];
    }
    initialRowData[n + 1] = 0; // Initial error is 0 for all elements
    iterationModel.addRow(initialRowData);

    iterations++;

    while (iterations <= MAX_ITERATIONS) {
        for (int i = 0; i < n; i++) {
            double sum = M[i][n]; // b_n
            double diagonal = M[i][i];

            if (diagonal == 0) {
                resultArea.append("Error: Diagonal element is zero. Unable to continue.\n");
                return;
            }

            for (int j = 0; j < n; j++)
                if (j != i)
                    sum -= M[i][j] * P[j];

            X[i] = 1 / diagonal * sum;
        }

        Object[] rowData = new Object[n + 2];
        rowData[0] = iterations;
        for (int i = 0; i < n; i++) {
            rowData[i + 1] = X[i];
        }

        boolean stop = true;
        double maxError = 0;
        for (int i = 0; i < n; i++) {
            double error = Math.abs((X[i] - P[i]) / X[i]);
            if (error > maxError) {
                maxError = error;
            }
            if (error > epsilon) {
                stop = false;
            }
        }
        rowData[n + 1] = maxError;

        iterationModel.addRow(rowData);
        iterations++;

        if (stop)
            break;

        System.arraycopy(X, 0, P, 0, n); // Update P with the values of X
    }

    resultArea.append("Solutions converged:\n");
    for (int i = 0; i < n; i++) {
        resultArea.append("x" + (i + 1) + ": " + X[i] + "\n");
    }
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JacobiMethod gui = new JacobiMethod();
            gui.setVisible(true);
        });
    }
}
