//Nucleus-Coding-Challenge
//Paul van Sittert

package bitcoin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

public class PortfolioPerformance {

    private static final List<Price> PRICES = List.of(
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 5, 0, 0), new BigDecimal("35464.53")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 2, 5, 0, 0), new BigDecimal("35658.76")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 3, 5, 0, 0), new BigDecimal("36080.06")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 3, 13, 0, 0), new BigDecimal("37111.11")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 6, 5, 0, 0), new BigDecimal("38041.47")),
            new Price(LocalDateTime.of(2021, Month.SEPTEMBER, 7, 5, 0, 0), new BigDecimal("34029.61")));

    private static final List<Transaction> TRANSACTIONS = List.of(
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 9, 0, 0), new BigDecimal("0.012")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 1, 15, 0, 0), new BigDecimal("-0.007")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 4, 9, 0, 0), new BigDecimal("0.017")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 5, 9, 0, 0), new BigDecimal("-0.01")),
            new Transaction(LocalDateTime.of(2021, Month.SEPTEMBER, 7, 9, 0, 0), new BigDecimal("0.1")));

    // Complete this method to return a list of daily portfolio values with one record for each day from the 01-09-2021-07-09-2021 in ascending date order
    public static List<DailyPortfolioValue> getDailyPortfolioValues() {
        
        List<DailyPortfolioValue> DAILYPORTFOLIOVALUE = new ArrayList<>(); //Variable to stor the output list of portfolio values.
        LocalDateTime reportStartDate = LocalDateTime.of(2021, Month.SEPTEMBER, 1, 0, 0, 0); //Variable to store the start date of the reporting period
        LocalDateTime reportEndDate = LocalDateTime.of(2021, Month.SEPTEMBER, 7, 0, 0, 0); //Variable to store the end date of the reporting period
        BigDecimal currentBitcoin = new BigDecimal("0");
        BigDecimal currentPrice = new BigDecimal("0");
        int i = 0; //Variable to store the progress through the transactions List to reduce repeated searching through data. 
        
        //Calculates the total amount of Bitcoins in the portfolio before the reporting period
        for (i = i; i < TRANSACTIONS.size(); i++) {
            if (reportStartDate.isAfter(TRANSACTIONS.get(i).effectiveDate()) ){ //Only adds the Bitcoins if the Transactons date is before the start of the reporting period.
                currentBitcoin = currentBitcoin.add(TRANSACTIONS.get(i).numberOfBitcoins());
            } else {
                break;  //Breaks out of the loop once the current transaction reaches the date of the reporting perdiod.
            };
        };

        // Loops through each day in the reporting period
        for (LocalDateTime j = reportStartDate; reportEndDate.plusDays(1).isAfter(j); j = j.plusDays(1)) {
            
            // Continues to loop through the transaction. Only adds the transactions the happen between the end of the prevoius loop and the next day.
            for (i = i; i < TRANSACTIONS.size(); i++) {
                if (j.plusDays(1).isAfter(TRANSACTIONS.get(i).effectiveDate()) ){
                    currentBitcoin = currentBitcoin.add(TRANSACTIONS.get(i).numberOfBitcoins());
                } else {
                    break;  
                };
            };
            
            //Loops through the Prices list to get the most upto date price.
            for (int k = 0; k < PRICES.size(); k++){
                if (j.plusDays(1).isAfter(PRICES.get(k).effectiveDate()) ){ //Replaces current price as long as the Price change happens before the end of the day 
                    currentPrice = PRICES.get(k).price();
                } else {
                    break;  
                };
            };
            
            //Multiplies the total amount of bitcoins in th portfolio by the most upto date price to get the current balue of the portfolio.
            BigDecimal dailyValue = currentBitcoin.multiply(currentPrice);
            
            //Saves the daily value of the portfolio to the DAILYPORTFOLIOVALUE list alongside the date
            DAILYPORTFOLIOVALUE.add(new DailyPortfolioValue(j.toLocalDate(), dailyValue));
        };
        
        //Outputs the list of values
        return DAILYPORTFOLIOVALUE;
    }
}



