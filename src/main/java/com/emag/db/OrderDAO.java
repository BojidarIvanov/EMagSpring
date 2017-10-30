package com.emag.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.emag.model.OrderPojo;
import com.emag.model.ProductPojo;
import com.emag.model.UserPojo;

public class OrderDAO {

	private static OrderDAO instance;

	private OrderDAO() {
	}

	public static synchronized OrderDAO getInstance() {
		if (instance == null) {
			instance = new OrderDAO();
		}
		return instance;
	}

	// returns list of orders made by a customer the search is made by user_id
	public List<OrderPojo> getOrdersForUser(int user_id) throws SQLException {
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement(
				"SELECT order_id, created_at, shipping_address, billing_address, amount, status FROM orders WHERE customer_id = ?");
		ps.setLong(1, user_id);
		ResultSet rs = ps.executeQuery();
		List<OrderPojo> orders = new ArrayList<>();

		while (rs.next()) {
			orders.add(new OrderPojo(rs.getInt("order_id"), rs.getTimestamp("created_at").toLocalDateTime(), user_id,
					rs.getString("shipping_address"), rs.getString("billing_address"), rs.getBigDecimal("amount"),
					rs.getInt("status")));
		}
		ps.close();
		rs.close();

		return orders;
	}

	public long addOrder(OrderPojo order) throws SQLException {

		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = null;
		PreparedStatement orderedProducts = null;
		long orderId = 0;
		try {
			// setting up mySql transaction
			con.setAutoCommit(false);
			ps = con.prepareStatement(
					"INSERT INTO orders (customer_id, shipping_address, billing_address, amount, created_at, status) VALUES(?,?,?,?,?,?);",
					Statement.RETURN_GENERATED_KEYS);
			ps.setLong(1, order.getUserId());
			ps.setString(2, order.getShippingAddress());
			ps.setString(3, order.getBillingAddress());
			ps.setBigDecimal(4, order.getTotalPrice());
			ps.setString(5, order.getDate().toString());
			// default status 1
			ps.setInt(6, 1);
			ps.executeUpdate();
			// getting auto-generated key
			ResultSet rs = ps.getGeneratedKeys();
			rs.next();
			long key = rs.getLong(1);
			orderId = key;
			// calling the method to fill the ordered_products table
			orderedProducts = addToOrderedProducts(order, key);
			con.commit();
			// if no exception is thrown till this moment it's time to update
			// allProducts
			Map<Integer, ProductPojo> prod = ProductDAO.getInstance().getAllProducts();
			synchronized (prod) {
				prod = ProductDAO.getInstance().getAllProducts(true);
			}

		} catch (SQLException e) {
			if (con != null) {
				try {
					System.err.print("Transaction is being rolled back");
					con.rollback();
				} catch (SQLException excep) {
					throw new SQLException(excep);
				}
			}
			throw new SQLException(e);
		} finally {
			if (ps != null) {
				ps.close();
			}
			if (orderedProducts != null) {
				orderedProducts.close();
			}
			con.setAutoCommit(true);
		}
		return orderId;
	}

	// no need to create separate pojo for ordered_product table it's being
	// populated by below method.
	public PreparedStatement addToOrderedProducts(OrderPojo order, long key) throws SQLException {
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement("INSERT INTO ordered_products  VALUES(?,?,?);");
		Map<ProductPojo, Integer> basket = order.getCollection();
		System.out.println("Basket: " + basket);
		PreparedStatement prepStatement = null;
		for (Entry<ProductPojo, Integer> entry : basket.entrySet()) {
			System.out.println("Product vs order");
			ps.setLong(1, key);
			ps.setLong(2, entry.getKey().getProductID());
			ps.setInt(3, entry.getValue());
			ps.executeUpdate();
			// when adding product to ordered items they are deducted from
			// products table availability column
			 prepStatement = ProductDAO.getInstance()
					.removeProductByIdAndQnt(entry.getKey().getProductID(), entry.getValue());
			
		}
		prepStatement.close();
		return ps;
	}

	public HashSet<OrderPojo> getOrdersForUser(UserPojo u) throws SQLException {
		Connection con = DBManager.CON1.getConnection();
		PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE customer_id = ?;");
		ps.setLong(1, u.getCustomerID());
		ResultSet rs = ps.executeQuery();
		LinkedHashSet<OrderPojo> orders = new LinkedHashSet<>();
		while (rs.next()) {
			HashMap<String, Integer> products = ProductDAO.getInstance().getProductsForOrder(rs.getInt("order_id"));
			OrderPojo order = new OrderPojo(rs.getInt("order_id"), rs.getTimestamp("created_at").toLocalDateTime(),
					rs.getInt("customer_id"), rs.getString("shipping_address"), rs.getString("billing_address"),
					rs.getBigDecimal("amount"), rs.getInt("status"));
			orders.add(order);
			order.setHistoryForOrderedProducts(products);
		}
		return orders;
	}

//	public static void main(String[] args) throws SQLException {
//		System.out.println("Start");
//		OrderDAO od = OrderDAO.getInstance();
//		OrderPojo op1 = new OrderPojo(LocalDateTime.now(), 1);
//		OrderPojo op2 = new OrderPojo(LocalDateTime.now().minusDays(5), 2);
//		OrderPojo op3 = new OrderPojo(LocalDateTime.now().minusDays(10), 1);
//
//		od.addOrder(op1);
//		od.addOrder(op2);
//		od.addOrder(op3);
//		System.out.println("End");
//		System.out.println(od.getOrdersForUser(1));
//
//	}
}
