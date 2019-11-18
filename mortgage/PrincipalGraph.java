package mortgage;

import java.awt.*;
import javax.swing.*;

/**
 * PrincipalGraph is a container for the dialog box that contains the custom
 * graph of loan term against remaining principal balance. The custom drawing
 * actually happens in the GraphPanel class which is an extensin of JPanel.
 */
public class PrincipalGraph {
    private JFrame parentFrame;
    private Loan currentLoan;
    private JDialog displayFrame;

    /**
     * Constructor requires the main application frame to orient itself to and the
     * loan instance for passing to the graphing panel.
     *
     * @param baseFrame the JFrame of the parent application
     * @param loan      a valid Loan instance to graph the remaining principal over
     *                  time.
     */
    public PrincipalGraph(JFrame baseFrame, Loan loan) {
        parentFrame = baseFrame;
        currentLoan = loan;
        displayFrame = new JDialog(parentFrame, "Principal Graph", true);
    }

    /**
     * popup instantiates the graph and adds it to a dialog box we configure the
     * layout on here. This should be invoked at the moment we want the principal
     * graph to become visible.
     */
    public void popup() {
        GraphPanel panel = new GraphPanel(currentLoan);
        panel.setPreferredSize(new Dimension(600, 600));
        displayFrame.setLocationRelativeTo(parentFrame);
        displayFrame.add(panel, BorderLayout.CENTER);
        displayFrame.pack();
        displayFrame.setVisible(true);
    }
}