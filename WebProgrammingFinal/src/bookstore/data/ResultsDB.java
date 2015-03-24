package bookstore.data;

import bookstore.business.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Filip on 3/10/2015.
 */
public class ResultsDB {
    public static List<Product> searchResults(String search,String context) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        List<Product> results = new ArrayList<>(5);
        int count = 0;

        String query = " SELECT * FROM Product "
                + " WHERE ProductDescription LIKE "
                + " ? ";
        switch (context) {
            case "isbn13":
                query = " SELECT * FROM Product "
                        + " WHERE ProductISBN13 LIKE "
                        + " ? ";
                break;
            case "isbn10":
                query = " SELECT * FROM Product "
                        + " WHERE ProductISBN10 LIKE "
                        + " ? ";
                break;
            case "author":
                query = " SELECT * FROM Product "
                        + " WHERE ProductAuthor LIKE "
                        + " ? ";
                break;
            case "category":
                query = " SELECT * FROM Product "
                        + " WHERE ProductCategory LIKE "
                        + " ? ";
                break;
        }
        try {
            ps = connection.prepareStatement(query);

            ps.setString(1, "%"+search+"%");
            rs = ps.executeQuery();
            while (rs.next()) {
                Product newResult = new Product();
                newResult.setDescription(rs.getString("ProductDescription"));
                newResult.setAuthor(rs.getString("ProductAuthor"));
                newResult.setISBN13(Long.parseLong(rs.getString("ProductISBN13")));
                results.add(count, newResult);
                count++;
                if (count == 5) break;
            }
            return results;
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
