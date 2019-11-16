package mortgage;

import java.text.*;

public class LoanForm {
    private String principal;
    private String term;
    private String rate;

    public LoanForm(String principal, String term, String rate) {
        this.principal = principal;
        this.term = term;
        this.rate = rate;
    }

    public double getPrincipal() {
        double value = 0.0;
        try {
            value = NumberFormat.getInstance().parse(this.principal).doubleValue();
        } catch (ParseException e) {
            value = -1.0;
        }
        return value;
    }

    public int getTerm() {
        int value = 0;
        try {
            value = NumberFormat.getInstance().parse(this.term).intValue();
        } catch (ParseException e) {
            value = -1;
        }
        return value;
    }

    public double getRate() {
        double value = 0.0;
        try {
            value = NumberFormat.getInstance().parse(this.rate).doubleValue();
        } catch (ParseException e) {
            value = -1.0;
        }
        return value;
    }

    public boolean principalIsValid() {
        double val = this.getPrincipal();
        return val > 0.0 && val < 1000000000.0;
    }

    public boolean termIsValid() {
        int val = this.getTerm();
        return val > 0 && val < 1000;
    }

    public boolean rateIsValid() {
        double val = this.getRate();
        return val > -0.000000001 && val < 100.0;
    }

    public boolean isValid() {
        return this.principalIsValid() && this.termIsValid() && this.rateIsValid();
    }
}