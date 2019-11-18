package mortgage;

import java.text.*;

/**
 * LoanForm is for taking the string representations of Loan input parameters
 * from the UI and validating whether they are of the correct format and in the
 * valid ranges for each parameter. It they are, this class can construct a
 * valid Loan object for computing detailed payment schedules, etc.
 */
public class LoanForm {
    private String principal;
    private String term;
    private String rate;

    /**
     * Constructor requires the 3 standard inputs for mortgage computation. These
     * are expected to be string values because a LoanForm should be the validating
     * layer for parsing the strings into numerical values.
     *
     * @param principal the initial balance of the loan.
     * @param term      the number of months over which the loan will run
     * @param rate      the interest rate for the loan
     */
    public LoanForm(String principal, String term, String rate) {
        this.principal = principal;
        this.term = term;
        this.rate = rate;
    }

    /**
     * setPrincipal is a basic setter for updating the principal value when the UI
     * changes
     *
     * @param val new value for the loan principal
     */
    public void setPrincipal(String val) {
        principal = val;
    }

    /**
     * getPrincipal tries to parse the string value of the principal field into a
     * valid number
     *
     * @return the numeric representation of the current principal field
     */
    public double getPrincipal() {
        double value = 0.0;
        try {
            value = NumberFormat.getInstance().parse(this.principal).doubleValue();
        } catch (ParseException e) {
            value = -1.0;
        }
        return value;
    }

    /**
     * setTerm is a basic setter for updating the term length of the loan when the
     * UI changes
     *
     * @param val new value for the term of the loan
     */
    public void setTerm(String val) {
        term = val;
    }

    /**
     * getTerm tries to parse the string value of the term field into a valid number
     *
     * @return the numeric representation of the current term field
     */
    public int getTerm() {
        int value = 0;
        try {
            value = NumberFormat.getInstance().parse(this.term).intValue();
        } catch (ParseException e) {
            value = -1;
        }
        return value;
    }

    /**
     * setRate is a basic setter for updating the interest rate of the loan when the
     * UI changes
     *
     * @param val new value for the interest rate of the loan
     */
    public void setRate(String val) {
        rate = val;
    }

    /**
     * getRate tries to parse the string value of the interest rate field into a
     * valid number
     *
     * @return the numeric representation of the current interest rate field
     */
    public double getRate() {
        double value = 0.0;
        try {
            value = NumberFormat.getInstance().parse(this.rate).doubleValue();
        } catch (ParseException e) {
            value = -1.0;
        }
        return value;
    }

    /**
     * principalIsValid performs a regex check on the string value of the principal
     * field to make sure it looks like a number and then checks that the parsed
     * value is within a sane range for a mortgage principal
     *
     * @return true if principal is valid
     */
    public boolean principalIsValid() {
        if (!principal.matches("\\d+\\.*\\d*")) {
            return false;
        }
        double val = this.getPrincipal();
        return val > 0.0 && val < 1000000000.0;
    }

    /**
     * termIsValid performs a regex check on the string value of the term field to
     * make sure it looks like a number and then checks that the parsed value is
     * within a sane range for a mortgage term in months
     *
     * @return true if term is valid
     */
    public boolean termIsValid() {
        if (!term.matches("\\d+\\.*\\d*")) {
            return false;
        }
        int val = this.getTerm();
        return val > 0 && val < 1000;
    }

    /**
     * rateIsValid performs a regex check on the string value of the interest rate
     * field to make sure it looks like a number and then checks that the parsed
     * value is within a sane range for a mortgage interest rate
     *
     * @return true if rate is valid
     */
    public boolean rateIsValid() {
        if (!rate.matches("\\d+\\.*\\d*")) {
            return false;
        }
        double val = this.getRate();
        return val > -0.000000001 && val < 100.0;
    }

    /**
     * isValid is the boolean product of all the individual field validation
     * methods.
     *
     * @return true if all fields are valid.
     */
    public boolean isValid() {
        return this.principalIsValid() && this.termIsValid() && this.rateIsValid();
    }

    /**
     * getLoan instantiates a Loan object with the parsed numeric value of each
     * field. This should only be invoked after confirming the inputs are valid with
     * the isValid method.
     *
     * @throws RuntimeException if you fail to validate ahead of time and the data
     *                          is invalid
     * @return a Loan instance with valid input paramters
     */
    public Loan getLoan() {
        if (!isValid()) {
            throw new RuntimeException("Loan should not be created if LoanForm is invalid!");
        }
        return new Loan(getPrincipal(), getTerm(), getRate());
    }
}