package org.example.Service;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import org.example.Pojo.OutlierDetails;
import org.example.Pojo.PriceData;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class OutlierCalculator {
    public OutlierCalculator() {
    }
    String filePath = System.getProperty("user.dir")+"/src/main/resources/";
    List<PriceData> thirtyConsecutiveData = new ArrayList<>();
        public List<PriceData> readfile(String stockName){
           try {
               System.out.println(filePath);
               File childFolders = new File(filePath+stockName);
               if(childFolders.exists()) {
                   File[] csvFiles = childFolders.listFiles();

                   //FileReader fileReader = new FileReader(filePath);
                   for (File files : csvFiles) {
                       FileReader fileReader = new FileReader(files);
                       CsvToBean<PriceData> csvToBean = new CsvToBeanBuilder<PriceData>(fileReader)
                               .withType(PriceData.class)
                               .withIgnoreLeadingWhiteSpace(true)
                               .withOrderedResults(true)
                               .withIgnoreEmptyLine(true)
                               .build();

                       List<PriceData> records = csvToBean.parse();
                        if (records.size()<30){
                            System.out.println("file contains less than 30 data");
                        }
                        else {
                            Random random = new Random();
                            int start = random.nextInt(records.size() - 29);
                            for(int i = start;i<start+30;i++){
                                thirtyConsecutiveData.add(records.get(i));
                            }
                            /*for (PriceData record : records) {
                                System.out.println(record);
                            }*/
                            for (PriceData data : thirtyConsecutiveData){
                                System.out.println(data);
                            }
                        }
                       /*System.out.println("_________________");
                       System.out.println(records.size());*/
                   }
               }
               else {
                   System.out.println("folder not found!!");
               }
            } catch (Exception e) {
               System.out.println(" oops exception occured");
            }
           return thirtyConsecutiveData;
        }



        public void outlierList(String stockName) {
            //giving the stock name as input to the fucntion. // possible stock names under the parent folder
            // NYSE , NASDAQ, LSE
            List<PriceData> sheetDataPoints = readfile(stockName);
            //List<String> distinctProductId = sheetDataPoints.stream().map(PriceData::getStockId).distinct().collect(Collectors.toList());
            Map<String, List<PriceData>> groupedByStockId = sheetDataPoints.stream()
                    .collect(Collectors.groupingBy(PriceData::getStockId));
            for (Map.Entry<String, List<PriceData>> value : groupedByStockId.entrySet()) {
                String stockId = value.getKey();
                List<PriceData> stockDetailsPerStockId = value.getValue();
                double mean = stockDetailsPerStockId.stream().collect(Collectors.averagingDouble(PriceData::getStockPrice));
                double stdDeviation = Math.sqrt(
                        stockDetailsPerStockId.stream()
                                .mapToDouble(dp -> Math.pow(dp.getStockPrice() - mean, 2))
                                .sum() / stockDetailsPerStockId.size());


                //To Find outliers
                List<OutlierDetails> outlierDetails = new ArrayList<>();
                for (PriceData p : sheetDataPoints) {
                    double deviation = Math.abs(p.getStockPrice() - mean);
                    if (deviation > 2 * stdDeviation) {
                        double percentDeviation = ((deviation - 2 * stdDeviation) / mean) * 100;
                        outlierDetails.add(new OutlierDetails(
                                stockId,
                                p.getStockTimestamp(),
                                p.getStockPrice(),
                                mean,
                                deviation,
                                percentDeviation
                        ));
                    }
                }

                System.out.println("Outliers for Stock ID: " + stockId);
                for (OutlierDetails outlier : outlierDetails) {
                    System.out.println(outlier);
                }
            OutlierCalculator.createOutlierCsv(stockId,outlierDetails);
            }
        }

        public static void createOutlierCsv(String stockId, List<OutlierDetails> outlierDetails){
            String fileName = stockId+"outlier details.csv";
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileName));
                bufferedWriter.write("Stock-ID,Timestamp,Actual Stock Price,Mean,Deviation,% Deviation\n");

                // Write each outlier
                for (OutlierDetails outlier : outlierDetails) {
                    bufferedWriter.write(String.format(
                            "%s,%s,%.2f,%.2f,%.2f,%.2f\n",
                            outlier.getStockId(),
                            outlier.getTimestamp(),
                            outlier.getActualPrice(),
                            outlier.getMean(),
                            outlier.getActualStokeMeanDifference(),
                            outlier.getPercentageDeviation()
                    ));
                }
                bufferedWriter.flush();
                System.out.println("Outliers written to: " + fileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

}
