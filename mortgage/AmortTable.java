package mortgage;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;

/**
 * AmortTable is the UI class for the popup window that contains the list of all
 * payments for a given loan, presented in a dialog box.
 */
public class AmortTable {
    private JFrame parentFrame;
    private Loan currentLoan;
    private JDialog displayFrame;

    /**
     * The constructor, which is the expected way to create a table. It's ok to
     * create and dispose of these UI tables, all the state is in the loan object.
     *
     * @param baseFrame the JFrame of the parent application that is popping this
     *                  window up. Useful for relative positioning.
     * @param loan      the Loan object with the state for this table, we need it to
     *                  get the computed payments list.
     */
    public AmortTable(JFrame baseFrame, Loan loan) {
        parentFrame = baseFrame;
        currentLoan = loan;
        displayFrame = new JDialog(parentFrame, "Amortization Table", true);
    }

    /**
     * popup is the thing to call for making the amortization table a visible
     * window. It takes care of constructing the scrollable table UI from the
     * payments data it collects from the loan object.
     */
    public void popup() {
        String[] columnNames = { "Number", "Payment Amt", "Interest", "Principal", "Balance" };
        JTable paymentsTable = new JTable(paymentsData(), columnNames);
        paymentsTable.setPreferredScrollableViewportSize(new Dimension(500, 80));
        JScrollPane scrollPane = new JScrollPane(paymentsTable);
        displayFrame.add(scrollPane, BorderLayout.CENTER);
        displayFrame.setLocationRelativeTo(parentFrame);
        displayFrame.pack();
        displayFrame.setVisible(true);
    }

    /**
     * paymentsData is a wrapper for the payments list that is queried from the loan
     * object. The JList needs an array of arrays, so we need to iterate over the
     * actual Payment objects computed from the business logic and turn them into
     * the 2D array that the UI needs with the columns displayed in the correct
     * order.
     *
     * @return a 2D array reprsentation of strings for the computed data in each
     *         Payment object, with columns correctly ordered for the Amortization
     *         Table
     */
    public Object[][] paymentsData() {
        ArrayList<Payment> payments = currentLoan.getPayments();
        Object[][] data = new Object[payments.size()][5];
        DecimalFormat df = new DecimalFormat("0.00");
        payments.forEach((p) -> {
            int pIndex = payments.indexOf(p);
            data[pIndex][0] = p.getNumber();
            data[pIndex][1] = df.format(p.getPaymentAmount());
            data[pIndex][2] = df.format(p.getAccruedInterest());
            data[pIndex][3] = df.format(p.getPrincipalReductionAmount());
            data[pIndex][4] = df.format(p.getRemainingPrincipal());
        });
        return data;
    }
}