package mortgage;

import javax.swing.*;
import javax.swing.event.*;

import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;

public class CalculatorGui {
    private JFrame guiFrame;
    private JPanel container;
    private JPanel leftColumn;
    private JPanel rightColumn;
    private JLabel principalLbl;
    private JLabel termLbl;
    private JLabel rateLbl;
    private JLabel paymentLbl;
    private JLabel totalLbl;
    private JTextField principalField;
    private JTextField termField;
    private JTextField rateField;
    private JLabel paymentField;
    private JLabel totalField;
    private JButton chartBtn;
    private JButton graphBtn;
    private boolean initialized;
    private LoanForm form;
    private Loan loan;

    public CalculatorGui() {
        this.guiFrame = new JFrame("Mortgage Calculator");
        this.guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.principalLbl = new JLabel("Principal Amount: $");
        this.termLbl = new JLabel("Loan Term (in years):");
        this.rateLbl = new JLabel("Interest Rate: %");
        this.paymentLbl = new JLabel("Monthly Payment: $");
        this.totalLbl = new JLabel("Total Repayment: $");
        this.principalField = new JTextField("250000", 20);
        this.termField = new JTextField("360", 20);
        this.rateField = new JTextField("3.5", 20);
        this.paymentField = new JLabel();
        this.totalField = new JLabel();
        this.chartBtn = new JButton("Amort. Table");
        this.graphBtn = new JButton("Principal Graph");
        this.container = new JPanel();
        this.leftColumn = new JPanel();
        this.rightColumn = new JPanel();
        this.initialized = false;
        this.form = new LoanForm(principalField.getText(), termField.getText(), rateField.getText());
        this.loan = form.getLoan();
    }

    public void show() {
        if (!this.initialized) {
            this.assembleInterface();
            this.bindButtons();
            this.initialized = true;
        }
    }

    public boolean inputsValid() {
        return form.isValid();
    }

    private void bindButtons() {
        chartBtn.addActionListener(new ChartListener());
        graphBtn.addActionListener(new GraphListener());
    }

    private void assembleInterface() {
        leftColumn.setLayout(new GridLayout(0, 1));
        leftColumn.add(principalLbl);
        leftColumn.add(termLbl);
        leftColumn.add(rateLbl);
        leftColumn.add(paymentLbl);
        leftColumn.add(totalLbl);
        leftColumn.add(chartBtn);

        MortgageDocListener fieldListener = new MortgageDocListener();
        rightColumn.setLayout(new GridLayout(0, 1));
        rightColumn.add(principalField);
        rightColumn.add(termField);
        rightColumn.add(rateField);
        rightColumn.add(paymentField);
        rightColumn.add(totalField);
        rightColumn.add(graphBtn);
        principalField.getDocument().addDocumentListener(fieldListener);
        termField.getDocument().addDocumentListener(fieldListener);
        rateField.getDocument().addDocumentListener(fieldListener);

        container.setBorder(BorderFactory.createEtchedBorder());
        container.setLayout(new BorderLayout());
        container.add(leftColumn, BorderLayout.CENTER);
        container.add(rightColumn, BorderLayout.EAST);

        guiFrame.setContentPane(container);
        guiFrame.pack();
        guiFrame.setVisible(true);
        updateDerivativeUI(inputsValid());
    }

    private void updateDerivativeUI(boolean inputsValid) {
        chartBtn.setEnabled(inputsValid);
        graphBtn.setEnabled(inputsValid);
        if (inputsValid) {
            double paymentAmt = loan.computePayment();
            double paymentsTotal = loan.totalOfPayments();
            DecimalFormat df = new DecimalFormat("0.00");
            paymentField.setText(df.format(paymentAmt));
            totalField.setText(df.format(paymentsTotal));
        } else {
            paymentField.setText("-- (input invalid) --");
            totalField.setText("-- (input invalid) --");
        }
    }

    private void updateFormValues() {
        form.setPrincipal(principalField.getText());
        form.setTerm(termField.getText());
        form.setRate(rateField.getText());
        loan = form.getLoan();
        updateDerivativeUI(inputsValid());
    }

    class MortgageDocListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            updateFormValues();
        }

        public void removeUpdate(DocumentEvent e) {
            updateFormValues();
        }

        public void changedUpdate(DocumentEvent e) {
            updateFormValues();
        }

    }

    class ChartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // display/center the jdialog when the button is pressed
            JDialog d = new JDialog(guiFrame, "Hello Chart", true);
            d.setLocationRelativeTo(guiFrame);
            d.setVisible(true);
        }
    }

    class GraphListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // display/center the jdialog when the button is pressed
            JDialog d = new JDialog(guiFrame, "Hello Graph", true);
            d.setLocationRelativeTo(guiFrame);
            d.setVisible(true);
        }
    }
}