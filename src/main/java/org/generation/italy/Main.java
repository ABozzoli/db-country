package org.generation.italy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

	private final static String DB_URL = "jdbc:mysql://localhost:3306/nations";
	private final static String DB_USER = "root";
	private final static String DB_PASSWORD = "rootpassword";
	
	public static void main(String[] args) {

		// scan init
		Scanner scan = new Scanner(System.in);
		
		try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
			System.out.println(!con.isClosed());
			
			Country selectedCountry = selectCountryById(con, scan);
			
			if (selectedCountry != null) {
				System.out.println("You selected: " + selectedCountry.getName());
			} else {
				System.out.println("Id not found.");
			}
			
		} catch (SQLException e) {
			System.out.println("WHOOPS, ERROR:");
			System.out.println(e.getMessage());
		}
		
		// scanner close
		scan.close();
	}
	
	private static Country selectCountryById(Connection con, Scanner scan) throws SQLException {
		Country selectedCountry = null;

		System.out.print("Select country id: ");
		int countryId = Integer.parseInt(scan.nextLine());

		String query = "SELECT * FROM countries WHERE country_id = ?;";
		try (PreparedStatement ps = con.prepareStatement(query)) {
			ps.setInt(1, countryId);

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					
					// converti in LocalDate solo se la data non è null
					LocalDate date = null;
					if (rs.getDate(4) != null) {
						date = rs.getDate(4).toLocalDate();
					}
					selectedCountry = new Country(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), date, rs.getString(5), rs.getString(6), rs.getInt(7));
				}
				
			}

		}
		return selectedCountry;
	}

}
