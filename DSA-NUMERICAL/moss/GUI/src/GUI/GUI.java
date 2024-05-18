package GUI;

import javax.swing.*;

public class GUI {

    public static void main(String[] args) {
        // Create the JFrame
        JFrame frame = new JFrame("Newton-Raphson Method");

        // Set the default close operation
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of the NewJPanel
        GUIMoss newJPanel = new GUIMoss();

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
