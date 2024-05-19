package GUI;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.DecimalFormat;
import java.util.EmptyStackException;
import java.util.Stack;

public class GUIMoss extends javax.swing.JPanel {

    /**
     * Creates new form GUI
     */
    public GUIMoss() {
        initComponents();
        configureTableRenderers();
        addDefaultItemsToComboBoxes();
    }
    
    private void addDefaultItemsToComboBoxes() {
        combobox.removeAllItems();
        //combobox.removeAllItems();
        
        // Add placeholder
        //jComboBox1.addItem("f(x)1 = ...");
        //jComboBox2.addItem("f(x)2 = ...");
        combobox.addItem("Select a function");
        addItemToComboBox(combobox, "(e^x - sin(x)) / 3");
        //addItemToComboBox(combobox, "(e^x - sin(x)) / 3");
        combobox.setSelectedIndex(0);
        //combobox.setSelectedIndex(0);
    }

    private void addItemToComboBox(JComboBox<String> comboBox, String item) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(item)) {
                return; // Item already exists, do not add it again
            }
        }
        comboBox.addItem(item);
    }


    
    private void configureTableRenderers() {
        DecimalFormat decimalFormat = new DecimalFormat("0.0000000000"); // Format with 10 decimal places
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public void setValue(Object value) {
                setText((value == null) ? "" : decimalFormat.format(value));
            }
        };
        
        jTable1.getColumnModel().getColumn(1).setCellRenderer(renderer); // Set renderer for the 'Result' column
        jTable1.getColumnModel().getColumn(2).setCellRenderer(renderer); // Set renderer for the 'Absolute Error' column
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        solvebtn = new javax.swing.JButton();
        functxtfld = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        itgstxtfld = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        thsldtxtfld = new javax.swing.JTextField();
        combobox = new javax.swing.JComboBox<>();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Number of Iterations", "Result", "Absolute Error"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(java.awt.event.ComponentEvent evt) {
                jTable1ComponentResized(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Enter function: ");

        solvebtn.setText("Solve");
        solvebtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                solvebtnActionPerformed(evt);
            }
        });

        functxtfld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                functxtfldActionPerformed(evt);
            }
        });

        jLabel2.setText("Enter initial guess:");

        itgstxtfld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itgstxtfldActionPerformed(evt);
            }
        });

        jLabel3.setText("Enter threshold for error:");

        thsldtxtfld.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                thsldtxtfldActionPerformed(evt);
            }
        });

        combobox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        combobox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboboxItemStateChanged(evt);
            }
        });
        combobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(functxtfld, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(itgstxtfld, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(thsldtxtfld, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(solvebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combobox, 0, 1, Short.MAX_VALUE)))
                .addGap(60, 60, 60))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(functxtfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(combobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(itgstxtfld, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(thsldtxtfld, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(8, 8, 8)
                .addComponent(solvebtn, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(48, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jTable1ComponentResized(java.awt.event.ComponentEvent evt) {//GEN-FIRST:event_jTable1ComponentResized
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable1ComponentResized

    private void solvebtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_solvebtnActionPerformed
        try {
            String function = functxtfld.getText().replace(" ", "");
            double initialGuess = Double.parseDouble(itgstxtfld.getText());
            double threshold = Double.parseDouble(thsldtxtfld.getText());

            if (function.equals("(e^x-sin(x))/3")) {
                checkHardcodeMOSS(function, initialGuess, threshold);
            } else {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0); // Clear the table before starting

                String[] tokens = function.split("(?<=[-+*/()])|(?=[-+*/()])");
                String[] postfix = infixToPostfix(tokens); // Ensure this method is accessible

                double result = 0;
                double newResult;
                double percentError;
                int iteration = 1;

                do {
                    newResult = evaluatePostfix(postfix, initialGuess); // Ensure this method is accessible
                    percentError = Math.abs((newResult - result) / newResult);

                    model.addRow(new Object[]{iteration, newResult, percentError}); // Add iteration data to the table

                    result = newResult;
                    initialGuess = newResult;
                    iteration++;

                } while (percentError > threshold && iteration < 1000);

                System.out.printf("Final result after iterations: %.10f\n", newResult);
                System.out.printf("Error: %.10f\n", percentError);

            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for initial guess and threshold.");
        } catch (EmptyStackException e) {
            JOptionPane.showMessageDialog(this, "Invalid function format", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_solvebtnActionPerformed

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
    
    private void hardcode(double val, double threshold) {
        double x = val;
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); 

        System.out.println("Iteration 0: x0 = " + x);

        for (int i = 1; i <= 1000; i++) {
            double newx = (Math.exp(x) - Math.sin(x)) / 3;
            double error = Math.abs((newx - x) / newx);

            System.out.printf("Iteration " + i + ": x" + i + " = %.10f" + ", Error = " + "%.10f\n", newx, error); // Print each iteration with error

            model.addRow(new Object[]{i, newx, error}); // Add iteration data to the table

            if (error < threshold) {
                System.out.printf("Final result after iterations: %.10f\n", newx);
                System.out.printf("Error: %.10f\n", error);
                break;
            }
            x = newx;
        }
    }
    
    private void checkHardcodeMOSS(String function, double x0, double tolerance) {
        if (function.equals("(e^x-sin(x))/3")) {
            double derivFunc = Math.abs((Math.exp(x0) - Math.cos(x0)) / 3);
            if (derivFunc <= 1) {
                JOptionPane.showMessageDialog(this, "Testing for convergence...\nThe solution will converge. Continuing the program...\n");
                hardcode(x0, tolerance);
            } else {
                JOptionPane.showMessageDialog(this, "\nTesting for convergence...\nThe solution will not converge. Rerun the program\n");
            }
        } else {
            JOptionPane.showMessageDialog(this, "\nFunction not available. Rerun the program.");
        }
    }
    
    private void functxtfldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_functxtfldActionPerformed

    }//GEN-LAST:event_functxtfldActionPerformed

    private void itgstxtfldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itgstxtfldActionPerformed

    }//GEN-LAST:event_itgstxtfldActionPerformed

    private void thsldtxtfldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_thsldtxtfldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_thsldtxtfldActionPerformed

    private void comboboxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboboxItemStateChanged
        if (evt.getStateChange() == java.awt.event.ItemEvent.SELECTED) {
            String selectedFunction = (String) combobox.getSelectedItem();
            if (!selectedFunction.equals("Select a function")) {
                functxtfld.setText(selectedFunction);
            }
        }
    }//GEN-LAST:event_comboboxItemStateChanged

    private void comboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboboxActionPerformed

        
    }//GEN-LAST:event_comboboxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combobox;
    private javax.swing.JTextField functxtfld;
    private javax.swing.JTextField itgstxtfld;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton solvebtn;
    private javax.swing.JTextField thsldtxtfld;
    // End of variables declaration//GEN-END:variables
}