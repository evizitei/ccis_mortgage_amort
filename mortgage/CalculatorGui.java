package mortgage;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import java.awt.*;

public class CalculatorGui {
    private JFrame guiFrame;
    private JPanel container;
    private JPanel leftColumn;
    private JPanel rightColumn;
    private JLabel principalLbl;
    private JLabel termLbl;
    private JLabel rateLbl;
    private JTextField principalField;
    private JTextField termField;
    private JTextField rateField;
    private JButton chartBtn;
    private JButton graphBtn;
    private boolean initialized;
    private LoanForm form;

    public CalculatorGui() {
        this.guiFrame = new JFrame("Mortgage Calculator");
        this.guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.principalLbl = new JLabel("Principal Amount: $");
        this.termLbl = new JLabel("Loan Term (in years):");
        this.rateLbl = new JLabel("Annual Percentage Rate:");
        this.principalField = new JTextField("250000", 20);
        this.termField = new JTextField("360", 20);
        this.rateField = new JTextField("3.5", 20);
        this.chartBtn = new JButton("Amort. Table");
        this.graphBtn = new JButton("Principal Graph");
        this.container = new JPanel();
        this.leftColumn = new JPanel();
        this.rightColumn = new JPanel();
        this.initialized = false;
        this.form = new LoanForm(principalField.getText(), termField.getText(), rateField.getText());
    }

    public void show() {
        if (!this.initialized) {
            this.assembleInterface();
            this.initialized = true;
        }
    }

    public boolean inputsValid() {
        return form.isValid();
    }

    private void assembleInterface() {
        this.leftColumn.setLayout(new GridLayout(0, 1));
        this.leftColumn.add(this.principalLbl);
        this.leftColumn.add(this.termLbl);
        this.leftColumn.add(this.rateLbl);
        this.leftColumn.add(this.chartBtn);

        MortgageDocListener fieldListener = new MortgageDocListener();
        this.rightColumn.setLayout(new GridLayout(0, 1));
        this.rightColumn.add(this.principalField);
        this.rightColumn.add(this.termField);
        this.rightColumn.add(this.rateField);
        this.rightColumn.add(this.graphBtn);
        principalField.getDocument().addDocumentListener(fieldListener);
        termField.getDocument().addDocumentListener(fieldListener);
        rateField.getDocument().addDocumentListener(fieldListener);

        this.container.setBorder(BorderFactory.createEtchedBorder());
        this.container.setLayout(new BorderLayout());
        this.container.add(this.leftColumn, BorderLayout.CENTER);
        this.container.add(this.rightColumn, BorderLayout.EAST);

        this.guiFrame.setContentPane(this.container);

        // Display the window.
        // frame.setPreferredSize(new Dimension(500, 200));
        this.guiFrame.pack();
        this.guiFrame.setVisible(true);
        enableButtons(inputsValid());
    }

    private void enableButtons(boolean enabled) {
        chartBtn.setEnabled(enabled);
        graphBtn.setEnabled(enabled);
    }

    private void updateFormValues() {
        form.setPrincipal(principalField.getText());
        form.setTerm(termField.getText());
        form.setRate(rateField.getText());
        System.out.println("SUMMARY:  " + form.outputSummary());
        System.out.println("IS VALID: " + Boolean.toString(form.isValid()));
        enableButtons(inputsValid());
    }

    class MortgageDocListener implements DocumentListener {
        public void insertUpdate(DocumentEvent e) {
            System.out.println("EVENT FIRED");
            updateFormValues();
        }

        public void removeUpdate(DocumentEvent e) {
            System.out.println("EVENT FIRED");
            updateFormValues();
        }

        public void changedUpdate(DocumentEvent e) {
            System.out.println("EVENT FIRED");
            updateFormValues();
        }

    }
}