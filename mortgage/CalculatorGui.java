package mortgage;

import javax.swing.*;
import javax.swing.event.*;

import java.text.DecimalFormat;
import java.awt.*;
import java.awt.event.*;

/**
 * CalculatorGui is the top level UI object containing the JFrame for the main
 * display window and all the UI components that are part of the entry form
 * window. Those components are organized internally into columns, but all are
 * attributes of this GUI object.
 */
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

    /**
     * The constructor instantiates all of the UI components and puts default values
     * into a LoanForm instance to help the user understand what format to put in
     * each entry field.
     */
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

    /**
     * The show method makes sure we don't orchestrate the UI more than once since
     * several components include configurations that are not idempotent and we only
     * want that run once.
     */
    public void show() {
        if (!this.initialized) {
            this.assembleInterface();
            this.bindButtons();
            this.initialized = true;
        }
    }

    /**
     * inputsValid is a wrapper for checking the correct formatting of the inputs in
     * all the form fields, though it delegates the actual validation logic to the
     * LoanForm instance
     *
     * @return the validity assessment from the LoanForm instance
     */
    public boolean inputsValid() {
        return form.isValid();
    }

    /**
     * bindButtons is part of the UI assembly routine initializing listener objects
     * for the interactive components and attaching them to the UI elementrs that
     * should trigger them.
     */
    private void bindButtons() {
        chartBtn.addActionListener(new ChartListener());
        graphBtn.addActionListener(new GraphListener());
    }

    /**
     * assembleInterface is called from the UI assembly routine and organizes all
     * the swing UI components in this form into a 2 column layout. It also attaches
     * change handlers to each text field so that we can check the validity of
     * inputs as they change and recompute the derived payments values real time.
     */
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

    /**
     * updateDerivativeUI changes the entry form depending on whether all the input
     * fields contain valid data. If they do, the buttons are enabled and the
     * payment and total fields are recomputed. Otherwise the payments get turned
     * off and the paymen and total fields are updated to give visual feedback that
     * the inputs are problematic.
     *
     * @param inputsValid boolean indicating whether the text input from the user is
     *                    ok. Should be queried from the LoanForm instnace.
     */
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

    /**
     * updateFormValues retrieves the text in each field from the UI and updates all
     * the values in the LoanForm instance (which handles the validation logic).
     * This lets us call one method no matter which field they change.
     */
    private void updateFormValues() {
        form.setPrincipal(principalField.getText());
        form.setTerm(termField.getText());
        form.setRate(rateField.getText());
        loan = form.getLoan();
        updateDerivativeUI(inputsValid());
    }

    /**
     * MortgageDocListener is meant to be attached to each of the text fields on the
     * entry form. Changing any of them will cause updateFormValues to be run again,
     * removing any need to thread individual fields to individual form values.
     */
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

    /**
     * ChartListener is meant to be attached to the chart button and is the bridge
     * for instantiating an AmortTable instance for showing the list of payments for
     * a loan.
     */
    class ChartListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            AmortTable chart = new AmortTable(guiFrame, loan);
            chart.popup();
        }
    }

    /**
     * GraphListener is meant to be attached to the graph button and is the bridge
     * for instantiating a PrincipalGraph instance for showing the graph of the
     * principal balance over time for a specific loan configuration.
     */
    class GraphListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            PrincipalGraph graph = new PrincipalGraph(guiFrame, loan);
            graph.popup();
        }
    }
}