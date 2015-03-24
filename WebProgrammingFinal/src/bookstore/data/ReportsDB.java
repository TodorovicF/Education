package bookstore.data;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import bookstore.business.*;

public class ReportsDB {

    // Returns null if error encountered
    public static Report selectReport(char reportType) throws ParseException {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        float amount = 0;
        int bookCount = 0;
        java.sql.Date endDate;
        java.sql.Date startDate;
        Calendar cal = Calendar.getInstance();
        Report report = new Report();

        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date currentDate = new java.util.Date();
        String currentDateFormatted = formatDate.format(currentDate);
        java.util.Date currentDateFormattedUtil = formatDate.parse(currentDateFormatted);
        java.sql.Date currentDateSQL = new java.sql.Date(currentDateFormattedUtil.getTime());

        // Get Current Month Report
        if (reportType == '0') {
            cal.add(Calendar.MONTH, -1);
            java.util.Date endDateUtil = cal.getTime();
            java.sql.Date endDateSQL = new java.sql.Date(endDateUtil.getTime());

            endDate = endDateSQL;
            startDate = currentDateSQL;
        }

        // Get Previous Month Report
        else if (reportType == '1') {
            cal.add(Calendar.MONTH, -1);
            java.util.Date endDateUtil = cal.getTime();
            java.sql.Date endDateSQL = new java.sql.Date(endDateUtil.getTime());
            startDate = endDateSQL;

            cal.add(Calendar.MONTH, -1);
            java.util.Date endDateUtil2 = cal.getTime();
            java.sql.Date endDateSQL2 = new java.sql.Date(endDateUtil2.getTime());
            endDate = endDateSQL2;
        }

        // Get Current Week Report
        else if (reportType == '2') {
            cal.add(Calendar.DAY_OF_MONTH, -7);
            java.util.Date endDateUtil = cal.getTime();
            java.sql.Date endDateSQL = new java.sql.Date(endDateUtil.getTime());

            endDate = endDateSQL;
            startDate = currentDateSQL;
        }

        // Get Previous Week Report
        else if (reportType == '3') {
            cal.add(Calendar.DAY_OF_MONTH, -7);
            java.util.Date endDateUtil = cal.getTime();
            java.sql.Date endDateSQL = new java.sql.Date(endDateUtil.getTime());
            startDate = endDateSQL;

            cal.add(Calendar.DAY_OF_MONTH, -7);
            java.util.Date endDateUtil2 = cal.getTime();
            java.sql.Date endDateSQL2 = new java.sql.Date(endDateUtil2.getTime());
            endDate = endDateSQL2;
        }

        // Get Two Weeks Previous Report
        else if (reportType == '4') {
            cal.add(Calendar.DAY_OF_MONTH, -14);
            java.util.Date endDateUtil = cal.getTime();
            java.sql.Date endDateSQL = new java.sql.Date(endDateUtil.getTime());
            startDate = endDateSQL;

            cal.add(Calendar.DAY_OF_MONTH, -7);
            java.util.Date endDateUtil2 = cal.getTime();
            java.sql.Date endDateSQL2 = new java.sql.Date(endDateUtil2.getTime());
            endDate = endDateSQL2;
        }

        // Get Two Months Previous Report
        else {
            cal.add(Calendar.MONTH, -2);
            java.util.Date endDateUtil = cal.getTime();
            java.sql.Date endDateSQL = new java.sql.Date(endDateUtil.getTime());
            startDate = endDateSQL;

            cal.add(Calendar.MONTH, -1);
            java.util.Date endDateUtil2 = cal.getTime();
            java.sql.Date endDateSQL2 = new java.sql.Date(endDateUtil2.getTime());
            endDate = endDateSQL2;
        }

        String query = " SELECT TotalAmount FROM Invoice "
                + " WHERE InvoiceDate BETWEEN "
                + " ? AND ? ";
        try {
            ps = connection.prepareStatement(query);
            ps.setDate(2, startDate);
            ps.setDate(1, endDate);
            rs = ps.executeQuery();
            while (rs.next()) {
                amount += rs.getFloat("TotalAmount");
                bookCount++;
            }
            report.setStartDate(startDate.toString());
            report.setEndDate(endDate.toString());
            report.setTotalSales(amount);
            report.setTotalProfit(amount);
            report.setBookCount(bookCount);
            return report;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
