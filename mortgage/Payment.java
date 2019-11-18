package mortgage;

/**
 * Payment represents a single payment within the life of a loan. Useful for
 * computing interest/principal splits and the remaining principal balance after
 * a given payment.
 */
public class Payment {
    private int number;
    private Loan loan;
    private double priorPrincipal;

    /**
     * This constructor is intended to be used for creating the FIRST payment for a
     * loan. Other loan payments should be instantiated by passing the payment that
     * precedes them to the Payment(Payment priorPayment) constructor. This is the
     * expected way to build up a list of payments. See "getPayments" on the Loan
     * class.
     *
     * @param l the loan object for which we are constructing a payment list.
     */
    public Payment(Loan l) {
        number = 1;
        loan = l;
        priorPrincipal = l.getPrincipal();
    }

    /**
     * This constructor is intended to be used for creating all the payments for a
     * loan EXCEPT the first one because it requires the preceding payment to be
     * passed in as a parameter. This is so the payment can be derived using the
     * remaining balance from the prior payment to determine how much interest
     * accures in this month, and from that the the principal/interest split, and
     * from that the new remaining balance.
     *
     * The FIRST payment for a loan should be instantiated by passing the loan that
     * thge payments are for to the Payment(Loan l) constructor. This is the
     * expected way to build up a list of payments. See "getPayments" on the Loan
     * class.
     *
     * @param priorPayment the preceding payment in the amortization schedule for
     *                     this loan.
     */
    public Payment(Payment priorPayment) {
        number = priorPayment.getNumber() + 1;
        loan = priorPayment.getLoan();
        priorPrincipal = priorPayment.getRemainingPrincipal();
    }

    /**
     * getLoan is a simple getter for returning the loan instance on which this set
     * of payments is constructed.
     *
     * @return the Loan instance
     */
    public Loan getLoan() {
        return loan;
    }

    /**
     * getNumber is a simple getter for returning the number for this payment, which
     * should be an integer on the interval [1, loanTerm].
     *
     * @return the Payment number
     */
    public int getNumber() {
        return number;
    }

    /**
     * getAccruedInterest uses the prior principal and the interest rate to compute
     * how much interest accures this month, which is an important input for
     * computing how much this payment reduces the balance of the loan.
     *
     * @return the dollar amount of interest accrued in the month of this payment
     */
    public double getAccruedInterest() {
        return priorPrincipal * loan.getMonthlyInterestRate();
    }

    /**
     * getPaymentAmount will usually just be the payment amount the Loan instance
     * itself computes. However in special cases where the borrower has made partial
     * payments, or has paid ahead, it's possible the last payment will actually be
     * less.
     *
     * @return dollar amount of total payment
     */
    public double getPaymentAmount() {
        double loanPmt = loan.computePayment();
        double newBalance = priorPrincipal + getAccruedInterest();
        if (loanPmt < newBalance) {
            return loanPmt;
        } else {
            return newBalance;
        }
    }

    /**
     * getPrincipalReductionAmount computes what portion of the total payment goes
     * towards principal reduction by subtracting the accrued interest.
     *
     * @return dollar amount of payment going to principal reduction.
     */
    public double getPrincipalReductionAmount() {
        return getPaymentAmount() - getAccruedInterest();
    }

    /**
     * getRemainingPrincipal computes the posterior balance of the loan after
     * applying this payment.
     *
     * @return dollar amount of remaining loan balance AFTER this payment
     */
    public double getRemainingPrincipal() {
        return priorPrincipal - getPrincipalReductionAmount();
    }
}