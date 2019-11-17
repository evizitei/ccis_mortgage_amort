package mortgage;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.*;

public class AmortTable {
    private JFrame parentFrame;
    private Loan currentLoan;
    private JDialog displayFrame;

    public AmortTable(JFrame baseFrame, Loan loan) {
        parentFrame = baseFrame;
        currentLoan = loan;
        displayFrame = new JDialog(parentFrame, "Amortization Table", true);
    }

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