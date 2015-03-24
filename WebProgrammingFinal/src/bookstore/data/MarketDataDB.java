package bookstore.data;

import bookstore.business.Invoice;
import bookstore.business.Product;
import bookstore.business.UserCategory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by v-ctran on 3/10/2015.
 */
public class MarketDataDB {

    public static List<Product> topSellers() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT ProductID,SUM(Quantity) FROM LineItem Group BY ProductID ORDER BY 2 DESC LIMIT 10";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            ArrayList<Product> products = new ArrayList<>();
            while(rs.next()) {
                Product p = ProductDB.selectProduct(Long.parseLong(rs.getString("ProductID")));
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }

    }

    public static List<Product> topSellerCategory() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "Select a.ProductID,a.ProductCategory,SUM(a.Quantity) FROM (SELECT ProductID,ProductCategory,Quantity FROM LineItem,Product WHERE (LineItem.ProductID = Product.ProductISBN13))a GROUP BY ProductCategory,ProductID DESC";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            ArrayList<Product> products = new ArrayList<>();
            while(rs.next()) {
                Product p = ProductDB.selectProduct(Long.parseLong(rs.getString("ProductID")));
                products.add(p);
            }
            return products;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static List<UserCategory> topUserCategory() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT SUM(count) as count,b.email,b.category FROM (SELECT a.Quantity as count,a.category as category,a.email as email FROM (Select User.EmailAddress AS email,Product.ProductCategory AS category,Quantity FROM LineItem INNER JOIN Product ON LineItem.ProductID = Product.ProductISBN13 INNER JOIN Invoice ON LineItem.InvoiceID = Invoice.InvoiceID INNER JOIN User ON Invoice.UserID = User.UserID)a)b GROUP BY b.category,b.email ORDER BY category,count DESC";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();

            ArrayList<UserCategory> userCategories = new ArrayList<>();
            while(rs.next()) {
                UserCategory userCategory = new UserCategory();
                userCategory.setCount(rs.getInt(1));
                userCategory.setUser(UserDB.selectUser(rs.getString(2)));
                userCategory.setCategory(rs.getString(3));
                userCategories.add(userCategory);
            }
            return userCategories;
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
