package mortgage;

import java.util.ArrayList;

public class Loan {
    private double principalAmt;
    private int termInMonths;
    private double ratePct;

    public Loan(double principal, int term, double rate) {
        principalAmt = principal;
        termInMonths = term;
        ratePct = rate;
    }

    public double getPrincipal() {
        return principalAmt;
    }

    public double getMonthlyInterestRate() {
        return ratePct / 100.0 / 12.0;
    }

    public double computePayment() {
        if (ratePct == 0.0) {
            return principalAmt / termInMonths;
        }
        double monthlyRate = getMonthlyInterestRate();
        double compoundedRate = Math.pow((1 + monthlyRate), (termInMonths * -1));
        double rateRatio = (1 - compoundedRate) / monthlyRate;
        return principalAmt / rateRatio;
    }

    public double totalOfPayments() {
        return computePayment() * termInMonths;
    }

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