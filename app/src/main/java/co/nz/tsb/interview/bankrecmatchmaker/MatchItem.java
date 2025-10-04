package co.nz.tsb.interview.bankrecmatchmaker;

public class MatchItem {

    private final String paidTo;
    private final String transactionDate;
    private final float total;
    private final String docType;
    private boolean selected;


    public MatchItem(String paidTo, String transactionDate, int total, String docType) {
        this.paidTo = paidTo;
        this.transactionDate = transactionDate;
        this.total = total;
        this.docType = docType;
        this.selected = false;
    }

    public String getPaidTo() {
        return paidTo;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public float getTotal() {
        return total;
    }

    public String getDocType() {
        return docType;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
