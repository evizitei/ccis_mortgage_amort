package mortgage;

import java.util.ArrayList;

/**
 * Loan is a domain object representing a mortgage. It contains all the business
 * logic for deriving payment values, lists of payments, computed interest
 * rates, etc. Anything computationally derived from the loan entry data should
 * be added to this object.
 */
public class Loan {
    private double principalAmt;
    private int termInMonths;
    private double ratePct;

    /**
     * Constructor requires the 3 standard inputs for mortgage computation. These
     * are expected to be valid values because a LoanForm should be validating the
     * inputs and parsing the strings into numerical values.
     *
     * @param principal the initial balance of the loan.
     * @param term      the number of months over which the loan will run
     * @param rate      the interest rate for the loan
     */
    public Loan(double principal, int term, double rate) {
        principalAmt = principal;
        termInMonths = term;
        ratePct = rate;
    }

    /**
     * getTerm is a simple getter for the termInMonths field
     *
     * @return the number of months over which the loan will run
     */
    public int getTerm() {
        return termInMonths;
    }

    /**
     * getPrincipal is a simple getter for the principalAmt field
     *
     * @return the initial amount the loan is issued for
     */
    public double getPrincipal() {
        return principalAmt;
    }

    /**
     * getMonthlyInterestRate is a helper for computing the quantity to multiply the
     * remaining balance by each month to determine how much interest accrues that
     * month.
     *
     * @return monthly interest rate
     */
    public double getMonthlyInterestRate() {
        return ratePct / 100.0 / 12.0;
    }

    /**
     * computePayment uses the amortization formula to figure out how much the
     * monthly payment for a given loan will be.
     *
     * @return the amount of the monthly payment for this loan
     */
    public double computePayment() {
        if (ratePct == 0.0) {
            return principalAmt / termInMonths;
        }
        double monthlyRate = getMonthlyInterestRate();
        double compoundedRate = Math.pow((1 + monthlyRate), (termInMonths * -1));
        double rateRatio = (1 - compoundedRate) / monthlyRate;
        return principalAmt / rateRatio;
    }

    /**
     * totalOfPayments uses the monthly payment info to compute how much money will
     * be repaid in total over the life of the loan
     *
     * @return sum of all payments over the term of the loan
     */
    public double totalOfPayments() {
        return computePayment() * termInMonths;
    }

    /**
     * getPayments constructs a list of payments by deriving each Payment object
     * from it's predecessor, thus using the remaining balance from the prior
     * payment to determine how much interest accures in the next month, and from
     * that the the principal/interest split, and from that the new remaining
     * balance. Any UI displaying the life of the loan, like the AmortTable or the
     * PrincipalGraph in this application, will require some list like this.
     *
     * @return ArrayList of payment objects ordered chronologically
     */
    public ArrayList<Payment> getPayments() {
        ArrayList<Payment> payments = new ArrayList<Payment>();
        int paymentNumber = 1;
        Payment currentPayment = new Payment(this);
        payments.add(currentPayment);
        while (paymentNumber < termInMonths) {
            paymentNumber = paymentNumber + 1;
            currentPayment = new Payment(currentPayment);
            payments.add(currentPayment);
        }
        return payments;
    }

}