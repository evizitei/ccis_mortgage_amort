package mortgage;

import javax.swing.*;

public class AmortTable {
    private JFrame parentFrame;
    private Loan currentLoan;
    private JDialog displayFrame;

    public AmortTable(JFrame baseFrame, Loan loan) {
        parentFrame = baseFrame;
        currentLoan = loan;
        displayFrame = new JDialog(parentFrame, "Hello Chart", true);
    }

    public void popup() {
        displayFrame.setLocationRelativeTo(parentFrame);
        displayFrame.setVisible(true);
    }
}