package mortgage;

import javax.swing.*;
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

    public CalculatorGui() {
        this.guiFrame = new JFrame("Mortgage Calculator");
        this.guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.principalLbl = new JLabel("Principal Amount: $");
        this.termLbl = new JLabel("Loan Term (in years):");
        this.rateLbl = new JLabel("Annual Percentage Rate:");
        this.principalField = new JTextField(20);
        this.termField = new JTextField(20);
        this.rateField = new JTextField(20);
        this.chartBtn = new JButton("Amort. Table");
        this.graphBtn = new JButton("Principal Graph");
        this.container = new JPanel();
        this.leftColumn = new JPanel();
        this.rightColumn = new JPanel();
        this.initialized = false;
    }

    public void show() {
        if (!this.initialized) {
            this.assembleInterface();
            this.initialized = true;
        }
    }

    private void assembleInterface() {
        this.leftColumn.setLayout(new GridLayout(0, 1));
        this.leftColumn.add(this.principalLbl);
        this.leftColumn.add(this.termLbl);
        this.leftColumn.add(this.rateLbl);
        this.leftColumn.add(this.chartBtn);

        this.rightColumn.setLayout(new GridLayout(0, 1));
        this.rightColumn.add(this.principalField);
        this.rightColumn.add(this.termField);
        this.rightColumn.add(this.rateField);
        this.rightColumn.add(this.graphBtn);

        this.container.setBorder(BorderFactory.createEtchedBorder());
        this.container.setLayout(new BorderLayout());
        this.container.add(this.leftColumn, BorderLayout.CENTER);
        this.container.add(this.rightColumn, BorderLayout.EAST);

        this.guiFrame.setContentPane(this.container);

        // Display the window.
        // frame.setPreferredSize(new Dimension(500, 200));
        this.guiFrame.pack();
        this.guiFrame.setVisible(true);
    }
}