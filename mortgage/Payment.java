package mortgage;

public class Payment {
    private int number;
    private Loan loan;
    private double priorPrincipal;

    public Payment(Loan l) {
        number = 1;
        loan = l;
        priorPrincipal = l.getPrincipal();
    }

    public Payment(Payment priorPayment) {
        number = priorPayment.getNumber() + 1;
        loan = priorPayment.getLoan();
        priorPrincipal = priorPayment.getRemainingPrincipal();
    }

    public Loan getLoan() {
        return loan;
    }

    public int getNumber() {
        return number;
    }

    public double getAccruedInterest() {
        return priorPrincipal * loan.getMonthlyInterestRate();
    }

    public double getPaymentAmount() {
        double loanPmt = loan.computePayment();
        double newBalance = priorPrincipal + getAccruedInterest();
        if (loanPmt < newBalance) {
            return loanPmt;
        } else {
            return newBalance;
        }
    }

    public double getPrincipalReductionAmount() {
        return getPaymentAmount() - getAccruedInterest();
    }

    public double getRemainingPrincipal() {
        return priorPrincipal - getPrincipalReductionAmount();
    }
}