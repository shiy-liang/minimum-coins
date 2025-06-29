package sg.nus.iss.javaspring.coins.DTO;

public class CoinsCount {
    private double denomination;
    private int count;
    public Double getDenomination() {
        return denomination;
    }

    public void setDenomination(Double denomination) {
        this.denomination = denomination;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
