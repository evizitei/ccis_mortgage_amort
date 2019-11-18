package mortgage;

import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

/**
 * GraphPanel is for the custom drawing of a chart showing the reduction in the
 * principal balance over the life of the loan. It manages turning the payments
 * data from the Loan instance into x,y points on the graph itself and uses awt
 * graphics for drawing the axes, label annotations, and the data line.
 */
public class GraphPanel extends JPanel {

    private static final long serialVersionUID = 2951304622079774537L;
    private int graphBound;
    private int panelSide;
    private Loan loan;

    /**
     * constructor to initialize the panel with an assumed side length (square
     * panel) and a "border" width (graphBound) so that all the math for computing
     * the position of each payment point can be easily changed to other sizes.
     *
     * @param inputLoan a Loan instance from which we can ask for the calculated
     *                  payments.
     */
    public GraphPanel(Loan inputLoan) {
        super();
        graphBound = 60;
        panelSide = 600;
        loan = inputLoan;
    }

    /**
     * paintComponent overrides the method on the standard JPanel so that we can
     * perform custom graphics drawing to produce a chart.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawAxes(g);
        drawPrincipal(g);
    }

    /**
     * getBoundedLength is a helper for determining how big one side of the "graph"
     * itself is after accounting for the borders and margins around the edge of the
     * panel. This makes it easier to compute pixel positions for each data point on
     * the graph in a way that will still work if the dimensions of the graph are
     * changed.
     *
     * @return the computed side length of the (square) graphing data area.
     */
    private int getBoundedLength() {
        return panelSide - (graphBound * 2);
    }

    /**
     * getTermXPoints iterates through the computed payment objects to get the X
     * component of each data point by extracting the payment number, dividing it by
     * the total number of payments to get "what percentage of the way across the
     * graph should this point be" and then we can use that percentage of the graph
     * side length to compute the pixel position of each data point.
     *
     * @return an array of integers representing the X position for each payment
     *         data point within the graph.
     */
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

    /**
     * getPrincipalYPoints iterates through the computed payment objects to get the
     * Y component of each payment data point by extracting the principal value from
     * that payment, dividing it by the original principal to get "what percentage
     * of the way across the graph should this point be" and then we can use that
     * percentage of the graph side length to compute the pixel position of each
     * data point.
     *
     * @return an array of integers representing the Y position for each payment
     *         data point within the graph.
     */
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

    /**
     * drawPrincipal is invoked to draw the blue line representing the principal
     * balance as it changes over the life of the loan.
     *
     * @param g the graphics objects from the JPanel provided in the paintComponent
     *          method
     */
    private void drawPrincipal(Graphics g) {
        g.setColor(Color.BLUE);
        int[] termPoints = getTermXPoints();
        int[] balancePoints = getPrincipalYPoints();
        g.drawPolyline(termPoints, balancePoints, loan.getTerm());
    }

    /**
     * drawAxes is invoked to draw the X and Y axes of the principal graph including
     * axis labels and orienting values at the extreme edge of each axis.
     *
     * @param g the graphics objects from the JPanel provided in the paintComponent
     *          method
     */
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