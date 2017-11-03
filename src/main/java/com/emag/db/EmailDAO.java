package com.emag.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class EmailDAO {

	private static EmailDAO instance;
	private Connection connection;
	private static final HashSet<String> allEmails = new HashSet<>();

	private EmailDAO() {
	}

	public static synchronized EmailDAO getInstance() throws SQLException {
		if (instance == null) {
			instance = new EmailDAO();
			instance.getAllEmails();
		}
		return instance;
	}

	public boolean addEmail(String email) throws SQLException {
		boolean emailAdded = false;
		getAllEmails();
		if (!emailExists(email)) {
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement("INSERT INTO subscribers (emails) VALUES (?);");
			ps.setString(1, email);
			ps.executeUpdate();
			synchronized (allEmails) {
				allEmails.add(email);
			}
			emailAdded = true;
			ps.close();
		}
		return emailAdded;
	}

	public boolean deleteEmail(String email) throws SQLException {

		int result = 0;
		if (emailExists(email)) {
			this.connection = DBManager.CON1.getConnection();
			PreparedStatement ps = this.connection.prepareStatement("DELETE FROM subscribers WHERE emails = ?");
			ps.setString(1, email);
			result = ps.executeUpdate();
			synchronized (allEmails) {
			allEmails.remove(email);
			}
			ps.close();
		}

		return result > 0;
	}

	public boolean emailExists(String email) throws SQLException {

		return allEmails.contains(email);
	}

	public HashSet<String> getAllEmails() throws SQLException {

		Connection conn = DBManager.CON1.getConnection();
		PreparedStatement ps = conn.prepareStatement("SELECT * FROM subscribers");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			allEmails.add(rs.getString("emails"));
		}
		rs.close();
		ps.close();
		return allEmails;
	}
}
