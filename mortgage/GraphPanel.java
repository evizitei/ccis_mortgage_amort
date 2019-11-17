package mortgage;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class GraphPanel extends JPanel {
    private int graphBound;
    private int panelSide;
    private Loan loan;

    public GraphPanel(Loan inputLoan) {
        super();
        graphBound = 60;
        panelSide = 600;
        loan = inputLoan;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // paint parent's background
        drawAxes(g);
        drawPrincipal(g);
    }

    private int getBoundedLength() {
        return panelSide - (graphBound * 2);
    }

    private int[] getTermXPoints() {
        int maxTerm = loan.getTerm();
        int[] output = new int[maxTerm];
        ArrayList<Payment> pmts = loan.getPayments();
        pmts.forEach((payment) -> {
            int num = payment.getNumber();
            double ratio = ((double) num) / ((double) maxTerm);
            double xCoord = graphBound + (ratio * getBoundedLength());
            output[pmts.indexOf(payment)] = (int) xCoord;
        });
        return output;
    }

    private int[] getPrincipalYPoints() {
        double maxVal = loan.getPrincipal();
        int[] output = new int[loan.getTerm()];
        int bLength = getBoundedLength();
        ArrayList<Payment> pmts = loan.getPayments();
        pmts.forEach((payment) -> {
            double balance = payment.getRemainingPrincipal();
            double ratio = balance / maxVal;
            double yCoord = graphBound + bLength - (ratio * bLength);
            output[pmts.indexOf(payment)] = (int) yCoord;
        });
        return output;
    }

    private void drawPrincipal(Graphics g) {
        g.setColor(Color.BLUE);
        int[] termPoints = getTermXPoints();
        int[] balancePoints = getPrincipalYPoints();
        g.drawPolyline(termPoints, balancePoints, loan.getTerm());
    }

    private void drawAxes(Graphics g) {
        g.setColor(Color.BLACK);
        int farBound = panelSide - graphBound;
        g.drawLine(graphBound, graphBound, graphBound, farBound);
        g.drawLine(graphBound, farBound, farBound, farBound);
        int xAxisLblY = panelSide - (graphBound / 2);
        g.drawString("Term", panelSide / 2, xAxisLblY);
        g.drawString("0", graphBound, xAxisLblY);
        g.drawString(Integer.toString(loan.getTerm()), farBound, xAxisLblY);
        g.drawString("Balance", 1, (panelSide / 2));
        g.drawString("0", graphBound / 2, farBound);
        g.drawString(Double.toString(loan.getPrincipal()), 1, graphBound);
    }
}