package aco;

public class AcoTestResult {
    private double value;
    private double score;

    public AcoTestResult(double value, double score) {
        this.value = value;
        this.score = score;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
