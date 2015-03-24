package bookstore.data;

import bookstore.business.Rating;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Christopher on 3/9/2015.
 */
public class RatingDB {
    public static List<Rating> selectRatings(long isbn13) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM ratings "
                + "WHERE RatingISBN13 = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, isbn13);
            rs = ps.executeQuery();

            ArrayList<Rating> ratings = new ArrayList<>();
            while (rs.next()) {
                Rating r = new Rating();
                r.setRatingID(rs.getInt("RatingID"));
                r.setIsbn13(Long.parseLong(rs.getString("RatingISBN13")));
                r.setScore(rs.getInt("RatingValue"));
                r.setUserEmail(rs.getString("RatingEmail"));
                r.setOpinion(rs.getString("RatingDescription"));
                ratings.add(r);
            }
            return ratings;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void update(Rating rating) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "UPDATE ratings SET "
                + "RatingID = ?, "
                + "RatingISBN13 = ?, "
                + "RatingValue = ?, "
                + "RatingEmail = ?, "
                + "RatingDescription = ? "
                + "WHERE RatingID = ?";
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, rating.getRatingID());
            ps.setString(2, String.valueOf(rating.getIsbn13()));
            ps.setInt(3, rating.getScore());
            ps.setString(4, rating.getUserEmail());
            ps.setString(5, rating.getOpinion());
            ps.setInt(6, rating.getRatingID());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void remove(Rating rating) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "DELETE FROM ratings WHERE RatingID = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, rating.getRatingID());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static void insert(Rating rating) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        //Check if user already has a rating
        Rating r = ratingExists(rating.getIsbn13(), rating.getUserEmail());
        //If rating exists, update existing rating
        if(r != null) {
            r.setScore(rating.getScore());
            r.setOpinion(rating.getOpinion());
            update(r);
            return;
        }
        String query = "INSERT INTO ratings (RatingISBN13, RatingValue, RatingEmail, RatingDescription) "
                + "VALUES (?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, String.valueOf(rating.getIsbn13()));
            ps.setInt(2, rating.getScore());
            ps.setString(3, rating.getUserEmail());
            ps.setString(4, rating.getOpinion());

            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println(e);
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static Rating ratingExists(long isbn13, String email) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM ratings "
                + "WHERE RatingISBN13 = ? AND RatingEmail = ?";

        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, isbn13);
            ps.setString(2, email);
            rs = ps.executeQuery();

            if(rs.next()) {
                Rating r = new Rating();
                r.setRatingID(rs.getInt("RatingID"));
                r.setIsbn13(Long.parseLong(rs.getString("RatingISBN13")));
                r.setScore(rs.getInt("RatingValue"));
                r.setUserEmail(rs.getString("RatingEmail"));
                r.setOpinion(rs.getString("RatingDescription"));
                return r;
            }
            return null;
        } catch (SQLException e) {
            System.err.println(e);
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

    public static List<Rating> bestRatings(long isbn13) {
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "SELECT * FROM ratings "
                + "WHERE RatingISBN13 = ? ORDER BY RatingValue DESC LIMIT 5";

        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, isbn13);
            rs = ps.executeQuery();

            ArrayList<Rating> ratings = new ArrayList<>();
            while(rs.next()) {
                Rating r = new Rating();
                r.setRatingID(rs.getInt("RatingID"));
                r.setIsbn13(Long.parseLong(rs.getString("RatingISBN13")));
                r.setScore(rs.getInt("RatingValue"));
                r.setUserEmail(rs.getString("RatingEmail"));
                r.setOpinion(rs.getString("RatingDescription"));
                ratings.add(r);
            }
            return ratings;
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
