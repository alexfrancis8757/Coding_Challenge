package org.example.Pojo;

import com.opencsv.bean.CsvBindByPosition;

public class PriceData {
    @CsvBindByPosition(position = 0)
    private String stockId;

    @CsvBindByPosition(position = 1)
    private String stockTimestamp;

    @CsvBindByPosition(position = 2)
    private double stockPrice;


    public String getStockId() {
        return stockId;
    }


    public double getStockPrice() {
        return stockPrice;
    }

    public String getStockTimestamp() {
        return stockTimestamp;
    }

    public void setStockId(String stockId) {
        this.stockId = stockId;
    }

    public void setStockPrice(double stockPrice) {
        this.stockPrice = stockPrice;
    }

    public void setStockTimestamp(String stockTimestamp) {
        this.stockTimestamp = stockTimestamp;
    }

    /*public PriceData(String stockId, double stockPrice, LocalDate stockTimestamp) {
        this.stockId = stockId;
        this.stockPrice = stockPrice;
        this.stockTimestamp = stockTimestamp;
    }
*/
    public PriceData() {
    }

    @Override
    public String toString() {
        return "PriceData{" +
                "stockId='" + stockId + '\'' +
                ", stockTimestamp=" + stockTimestamp +
                ", stockPrice=" + stockPrice +
                '}';
    }

}
