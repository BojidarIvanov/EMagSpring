package com.emag.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.TreeSet;

import com.emag.model.ReviewPojo;
import com.mysql.jdbc.Statement;

public class ReviewDAO {

	private static ReviewDAO instance;
	private Connection connection;
	// the key of the map is productId
	private static final HashMap<String, TreeSet<ReviewPojo>> reviews = new HashMap<>();

	private ReviewDAO() {
	}

	public static synchronized ReviewDAO getInstance() throws SQLException {
		if (ReviewDAO.instance == null) {
			instance = new ReviewDAO();
			instance.getAllReviews();
		}
		return instance;
	}

	public int addReview(ReviewPojo review) throws SQLException {
		int reviewId = 0;
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement(
				"INSERT INTO reviews (reviewRating, reviewText, reviewer, productID) VALUES (?, ?, ?, ?)",
				Statement.RETURN_GENERATED_KEYS);
		ps.setInt(1, review.getReviewingRating());
		ps.setString(2, review.getReviewText());
		ps.setInt(3, review.getReviewerID());
		ps.setInt(4, review.getProductID());
		ps.executeUpdate();
		ResultSet rs = ps.getGeneratedKeys();
		rs.next();
		reviewId = rs.getInt(1);
		rs.close();
		ps.close();
		System.out.println("review is added");
		return reviewId;
	}

	public boolean reviewExist(ReviewPojo review) throws SQLException {
		ReviewPojo reviewToCompare = null;
		reviewToCompare = getReview(review);
		return reviewToCompare != null;
	}

	public ReviewPojo getReview(ReviewPojo review) throws SQLException {
		ReviewPojo reviewToGet = null;
		ArrayList<ReviewPojo> reviews = new ArrayList<>();
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM reviews");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			reviews.add(new ReviewPojo(rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
		}
		for (ReviewPojo r : reviews) {
			if (r.equals(review)) {
				reviewToGet = r;
			}
		}

		rs.close();
		ps.close();
		return reviewToGet;
	}

	public boolean deleteReview(int reviewId) throws SQLException {
		int result = 0;
		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("DELETE FROM reviews WHERE reviewID = ?");
		ps.setInt(1, reviewId);
		result = ps.executeUpdate();

		ps.close();
		return result > 0;
	}

	public Map<Integer, ReviewPojo> getAllReviewsByProductId(int productId) throws SQLException {

		Map<Integer, ReviewPojo> products = new TreeMap<>();

		this.connection = DBManager.CON1.getConnection();
		PreparedStatement ps = this.connection.prepareStatement("SELECT * FROM reviews WHERE productID = ?");
		ps.setInt(1, productId);
		ResultSet rs = ps.executeQuery();
		int commentNumber = 0;
		while (rs.next()) {
			products.put(++commentNumber, new ReviewPojo(rs.getInt("reviewID"), rs.getInt("reviewRating"),
					rs.getString("reviewText"), rs.getInt("reviewer"), rs.getInt("productID")));
		}
		return products;
	}

	public HashMap<String, TreeSet<ReviewPojo>> getAllReviews() throws SQLException {

		Connection conn = DBManager.CON1.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM reviews");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			synchronized (reviews) {
				ReviewPojo review = new ReviewPojo(rs.getInt("reviewID"), rs.getInt("reviewRating"),
						rs.getString("reviewText"), rs.getInt("reviewer"), rs.getInt("productID"));
				synchronized (reviews) {
					if (reviews.containsKey(String.valueOf(rs.getInt("productID")))) {
						reviews.get(String.valueOf(rs.getInt("productID"))).add(review);
					} else {
						reviews.put(String.valueOf(rs.getInt("productID")), new TreeSet<>());
						reviews.get(String.valueOf(rs.getInt("productID"))).add(review);
					}
				}
			}
		}
		rs.close();
		ps.close();
		return reviews;
	}

	public static void main(String[] args) throws SQLException {

		boolean exists = false;
		ReviewDAO r = ReviewDAO.getInstance();
		System.out.println(r.addReview(new ReviewPojo(1, "something", 1, 1)));
		int count = 0;

	}
}
