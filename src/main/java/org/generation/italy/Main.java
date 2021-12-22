package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

	private final static String DB_URL = "jdbc:mysql://localhost:3306/nations";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "rootpassword";
	
	public static void main(String[] args) {

		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			System.out.println(!con.isClosed());
		} catch (SQLException e) {
			System.out.println("OOOPS, si è verificato un errore:");
			System.out.println(e.getMessage());
		}

	}

}
