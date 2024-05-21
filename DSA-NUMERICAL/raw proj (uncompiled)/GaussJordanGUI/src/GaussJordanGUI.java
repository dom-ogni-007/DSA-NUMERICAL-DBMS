import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GaussJordanGUI {
    private JFrame frame;
    private JTextField numVariablesField;
    private JTextField[][] coefficientFields;
    private JPanel inputPanel;
    private JPanel matrixPanel;
    private JPanel stepPanel;
    private JTextArea solutionArea;
    private int numVariables;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                GaussJordanGUI window = new GaussJordanGUI();
                window.frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public GaussJordanGUI() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Gauss-Jordan Elimination");
        frame.setBounds(100, 100, 800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout(0, 0));

        inputPanel = new JPanel();
        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        inputPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblNumVariables = new JLabel("Enter the number of variables:");
        inputPanel.add(lblNumVariables, gbc);

        gbc.gridx = 1;
        numVariablesField = new JTextField(10);
        inputPanel.add(numVariablesField, gbc);

        gbc.gridx = 2;
        JButton btnLoadMatrix = new JButton("Load Matrix");
        btnLoadMatrix.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadMatrixFields();
            }
        });
        inputPanel.add(btnLoadMatrix, gbc);

        gbc.gridx = 3;
        JButton btnSolve = new JButton("Solve");
        btnSolve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                solveGaussJordan();
            }
        });
        inputPanel.add(btnSolve, gbc);

        matrixPanel = new JPanel();
        matrixPanel.setLayout(new BoxLayout(matrixPanel, BoxLayout.Y_AXIS));
        JScrollPane matrixScrollPane = new JScrollPane(matrixPanel);
        frame.getContentPane().add(matrixScrollPane, BorderLayout.CENTER);

        stepPanel = new JPanel();
        stepPanel.setLayout(new BoxLayout(stepPanel, BoxLayout.Y_AXIS));
        JScrollPane stepScrollPane = new JScrollPane(stepPanel);
        frame.getContentPane().add(stepScrollPane, BorderLayout.SOUTH);

        solutionArea = new JTextArea();
        solutionArea.setEditable(false);
        solutionArea.setRows(5);
        frame.getContentPane().add(new JScrollPane(solutionArea), BorderLayout.SOUTH);
    }

    private void loadMatrixFields() {
        try {
            numVariables = Integer.parseInt(numVariablesField.getText());
            matrixPanel.removeAll();

            coefficientFields = new JTextField[numVariables][numVariables + 1];

            for (int i = 0; i < numVariables; i++) {
                JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
                rowPanel.add(new JLabel("Equation " + (i + 1) + ":"));

                for (int j = 0; j < numVariables + 1; j++) {
                    coefficientFields[i][j] = new JTextField(5);
                    rowPanel.add(coefficientFields[i][j]);
                }
                matrixPanel.add(rowPanel);
            }

            frame.revalidate();
            frame.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid number of variables.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void solveGaussJordan() {
        double[][] matrix = new double[numVariables][numVariables + 1];

        try {
            for (int i = 0; i < numVariables; i++) {
                for (int j = 0; j < numVariables + 1; j++) {
                    matrix[i][j] = Double.parseDouble(coefficientFields[i][j].getText());
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(frame, "Please enter valid coefficients.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        stepPanel.removeAll();
        displayMatrix(matrix, "Initial Matrix:");

        gaussJordan(matrix);

        frame.revalidate();
        frame.repaint();
    }

    private void displayMatrix(double[][] matrix, String title) {
        DefaultTableModel model = new DefaultTableModel();
        for (int i = 0; i < matrix[0].length; i++) {
            model.addColumn("Col " + (i + 1));
        }
        for (double[] row : matrix) {
            Object[] rowData = new Object[row.length];
            for (int i = 0; i < row.length; i++) {
                rowData[i] = row[i];
            }
            model.addRow(rowData);
        }
        JTable table = new JTable(model);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel(title), BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);

        // Add the panel to the matrixPanel
        matrixPanel.add(panel);

        // Revalidate and repaint the matrixPanel
        matrixPanel.revalidate();
        matrixPanel.repaint();
    }
    private void gaussJordan(double[][] matrix) {
        int size = matrix.length;

        for (int i = 0; i < size; i++) {
            // Make the diagonal element 1
            double diag = matrix[i][i];
            for (int j = 0; j < size + 1; j++) {
                matrix[i][j] /= diag;
            }

            for (int k = 0; k < size; k++) {
                if (k != i) {
                    double factor = matrix[k][i];
                    for (int j = 0; j < size + 1; j++) {
                        matrix[k][j] -= factor * matrix[i][j];
                    }
                }
            }

            displayMatrix(matrix, "Matrix after elimination step " + (i + 1) + ":");
        }

        solutionArea.setText("The solutions are:\n");
        for (int i = 0; i < size; i++) {
            solutionArea.append("x" + (i + 1) + " = " + matrix[i][size] + "\n");
        }
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
