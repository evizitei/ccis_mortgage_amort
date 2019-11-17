package mortgage;

import java.awt.*;
import javax.swing.*;

public class PrincipalGraph {
    private JFrame parentFrame;
    private Loan currentLoan;
    private JDialog displayFrame;

    public PrincipalGraph(JFrame baseFrame, Loan loan) {
        parentFrame = baseFrame;
        currentLoan = loan;
        displayFrame = new JDialog(parentFrame, "Principal Graph", true);
    }

    public void popup() {
        GraphPanel panel = new GraphPanel(currentLoan);
        panel.setPreferredSize(new Dimension(600, 600));
        displayFrame.setLocationRelativeTo(parentFrame);
        displayFrame.add(panel, BorderLayout.CENTER);
        displayFrame.pack();
        displayFrame.setVisible(true);
    }
}