package bookstore.business;

import java.io.Serializable;
import java.text.DateFormat;

/**
 * Created by Filip on 3/8/2015.
 */
public class Report implements Serializable {

    private float totalSales;
    private float totalProfit;
    private String startDate;
    private String endDate;
    private int bookCount;

    public Report() {
        totalSales = 0;
        totalProfit = 0;
        startDate = " ";
        endDate = " ";
        bookCount = 0;
    }

    public void setTotalSales(float totalSales) {this.totalSales = totalSales;}

    public void setTotalProfit(float totalProfit) {this.totalProfit = totalProfit;}

    public void setStartDate(String startDate) {this.startDate = startDate;}

    public void setEndDate(String endDate) {this.endDate = endDate;}

    public void setBookCount(int bookCount) {this.bookCount = bookCount;}

    public float getTotalSales() {return totalSales;}

    public float getTotalProfit() {return totalProfit;}

    public String getStartDate() {return startDate;}

    public String getEndDate() {return endDate;}

    public int getBookCount() {return bookCount;}
}
