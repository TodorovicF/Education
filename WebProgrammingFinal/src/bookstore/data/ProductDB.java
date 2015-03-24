package bookstore.data;

import java.sql.*;
import java.util.*;

import bookstore.business.*;

public class ProductDB {

    //This method returns null if a product isn't found.
    public static Product selectProduct(String productCode) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product "
                + "WHERE ProductDescription LIKE ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, productCode);
            rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setISBN13(rs.getLong("ProductISBN13"));
                p.setISBN10(rs.getLong("ProductISBN10"));
                p.setAuthor(rs.getString("ProductAuthor"));
                p.setDescription(rs.getString("ProductDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setStock(rs.getInt("ProductStock"));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static Product selectProduct(long isbn13) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product "
                + "WHERE ProductISBN13 = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, isbn13);
            rs = ps.executeQuery();
            if (rs.next()) {
                Product p = new Product();
                p.setISBN13(rs.getLong("ProductISBN13"));
                p.setISBN10(rs.getLong("ProductISBN10"));
                p.setAuthor(rs.getString("ProductAuthor"));
                p.setDescription(rs.getString("ProductDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setStock(rs.getInt("ProductStock"));
                return p;
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    //This method returns null if a product isn't found.
    public static List<Product> selectProducts() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product ORDER BY ProductCategory";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setISBN13(rs.getLong("ProductISBN13"));
                p.setISBN10(rs.getLong("ProductISBN10"));
                p.setAuthor(rs.getString("ProductAuthor"));
                p.setDescription(rs.getString("ProductDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setStock(rs.getInt("ProductStock"));
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

    //This method returns null if a product isn't found.
    public static List<Product> selectNewestProducts() {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM Product ORDER BY ProductAddedDate DESC LIMIT 4";
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while (rs.next()) {
                Product p = new Product();
                p.setISBN13(rs.getLong("ProductISBN13"));
                p.setISBN10(rs.getLong("ProductISBN10"));
                p.setAuthor(rs.getString("ProductAuthor"));
                p.setDescription(rs.getString("ProductDescription"));
                p.setPrice(rs.getDouble("ProductPrice"));
                p.setCategory(rs.getString("ProductCategory"));
                p.setStock(rs.getInt("ProductStock"));
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

    public static void updateStock(Product product) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE Product SET "
                + "ProductStock = ? "
                + "WHERE ProductISBN13 = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, product.getStock());
            ps.setString(2, product.getISBN13().toString());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void addProduct(Product product) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //Check if product exists
        Product p = ProductDB.selectProduct(product.getISBN13());
        if(p != null) {
            String query = "INSERT INTO product (ProductISBN13, ProductISBN10, "
                    + "ProductDescription, ProductAuthor, ProductPrice, ProductCategory, ProductStock, ProductAddedDate) "
                    + " VALUES (?, ?, ?, ?, ?, ?, ?, CURRENT_TIME())";
            try {
                ps = connection.prepareStatement(query);
                ps.setString(1, product.getISBN13().toString());
                ps.setString(2, product.getISBN10().toString());
                ps.setString(3, product.getDescription());
                ps.setString(4, product.getAuthor());
                ps.setDouble(5, product.getPrice());
                ps.setString(6, product.getCategory());
                ps.setInt(7, product.getStock());
                ps.executeUpdate();
            } catch (SQLException e) {
                System.err.println(e);
            } finally {
                DBUtil.closeResultSet(rs);
                DBUtil.closePreparedStatement(ps);
                pool.freeConnection(connection);
            }
        }
    }
}
