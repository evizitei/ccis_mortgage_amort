package mortgage;

/*
 * HelloWorldSwing.java requires no other files.
 */
import javax.swing.*;
import java.awt.*;

public class CalculatorApp {
    /**
     * Create the GUI and show it. For thread safety, this method should be invoked
     * from the event-dispatching thread.
     */
    private static void createAndShowGUI() {
        // Create and set up the window.
        JFrame frame = new JFrame("Mortgage Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel principalLbl = new JLabel("Principal Amount: $");
        JLabel termLbl = new JLabel("Loan Term (in years):");
        JLabel rateLbl = new JLabel("Annual Percentage Rate:");
        JTextField principalField = new JTextField(20);
        JTextField termField = new JTextField(20);
        JTextField rateField = new JTextField(20);

        JButton chartBtn = new JButton("Amort. Table");
        JButton graphBtn = new JButton("Principal Graph");

        JPanel leftColumn = new JPanel();
        leftColumn.setLayout(new GridLayout(0, 1));
        leftColumn.add(principalLbl);
        leftColumn.add(termLbl);
        leftColumn.add(rateLbl);
        leftColumn.add(chartBtn);

        JPanel rightColumn = new JPanel();
        rightColumn.setLayout(new GridLayout(0, 1));
        rightColumn.add(principalField);
        rightColumn.add(termField);
        rightColumn.add(rateField);
        rightColumn.add(graphBtn);

        JPanel container = new JPanel();
        container.setBorder(BorderFactory.createEtchedBorder());
        container.setLayout(new BorderLayout());
        container.add(leftColumn, BorderLayout.CENTER);
        container.add(rightColumn, BorderLayout.EAST);

        frame.setContentPane(container);

        // Display the window.
        // frame.setPreferredSize(new Dimension(500, 200));
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        // Schedule a job for the event-dispatching thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}