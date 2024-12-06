package org.example.Pojo;

public class OutlierDetails {
    private String stockId;
    private String timestamp;
    private double actualPrice;
    private double mean;
    private double ActualStokeMeanDifference;
    private double percentageDeviation;

    public OutlierDetails(String stockId, String timestamp, double actualPrice, double mean, double actualStokeMeanDifference, double percentageDeviation) {
        this.stockId = stockId;
        this.timestamp = timestamp;
        this.actualPrice = actualPrice;
        this.mean = mean;
        ActualStokeMeanDifference = actualStokeMeanDifference;
        this.percentageDeviation = percentageDeviation;
    }

    public String getStockId() {
        return stockId;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(double actualPrice) {
        this.actualPrice = actualPrice;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getActualStokeMeanDifference() {
        return ActualStokeMeanDifference;
    }

    public void setActualStokeMeanDifference(double actualStokeMeanDifference) {
        ActualStokeMeanDifference = actualStokeMeanDifference;
    }

    public double getPercentageDeviation() {
        return percentageDeviation;
    }

    public void setPercentageDeviation(double percentageDeviation) {
        this.percentageDeviation = percentageDeviation;
    }

    @Override
    public String toString() {
        return "OutlierDetails{" +
                "stockId='" + stockId + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", actualPrice=" + actualPrice +
                ", mean=" + mean +
                ", ActualStokeMeanDifference=" + ActualStokeMeanDifference +
                ", percentageDeviation=" + percentageDeviation +
                '}';
    }
}
