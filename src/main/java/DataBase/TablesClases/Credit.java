package DataBase.TablesClases;

public class Credit {
    private int ID;
    private String Bank;
    private String CreditName;
    private int MinSum;
    private int MaxSum;
    private int MinTerm;
    private int MaxTerm;
    private int percent;
    public Credit(){};
    public Credit(String bank, String creditName, int minSum, int maxSum, int minTerm, int maxTerm, int percent) {
        Bank = bank;
        CreditName = creditName;
        MinSum = minSum;
        MaxSum = maxSum;
        MinTerm = minTerm;
        MaxTerm = maxTerm;
        this.percent = percent;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getBank() {
        return Bank;
    }

    public void setBank(String bank) {
        Bank = bank;
    }

    public String getCreditName() {
        return CreditName;
    }

    public void setCreditName(String creditName) {
        CreditName = creditName;
    }

    public int getMinSum() {
        return MinSum;
    }

    public void setMinSum(int minSum) {
        MinSum = minSum;
    }

    public int getMaxSum() {
        return MaxSum;
    }

    public void setMaxSum(int maxSum) {
        MaxSum = maxSum;
    }

    public int getMinTerm() {
        return MinTerm;
    }

    public void setMinTerm(int minTerm) {
        MinTerm = minTerm;
    }

    public int getMaxTerm() {
        return MaxTerm;
    }

    public void setMaxTerm(int maxTerm) {
        MaxTerm = maxTerm;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }
}
