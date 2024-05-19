package guinrnonlinear;

import javax.swing.*;

public class GUINRnonlinear {

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Newton-Raphson Method");

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of the NewJPanel
        GUINRnonlinear1 newJPanel = new GUINRnonlinear1();

        // Add the panel to the frame
        frame.add(newJPanel);

        // Pack the frame to fit the preferred size and layouts of its components
        frame.pack();

        // Set the frame to be visible
        frame.setVisible(true);

        // Set the frame size
        frame.setSize(600, 600);
        
        
    }
}
