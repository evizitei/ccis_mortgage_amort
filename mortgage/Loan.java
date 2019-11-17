package mortgage;

public class Loan {
    private double principalAmt;
    private int termInMonths;
    private double ratePct;

    public Loan(double principal, int term, double rate) {
        principalAmt = principal;
        termInMonths = term;
        ratePct = rate;
    }

    public double computePayment() {
        if (ratePct == 0.0) {
            return principalAmt / termInMonths;
        }
        double monthlyRate = ratePct / 100.0 / 12.0;
        double compoundedRate = Math.pow((1 + monthlyRate), (termInMonths * -1));
        double rateRatio = (1 - compoundedRate) / monthlyRate;
        return principalAmt / rateRatio;
    }

    public double totalOfPayments() {
        return computePayment() * termInMonths;
    }

}