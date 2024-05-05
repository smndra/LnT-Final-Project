package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

public class Connect{
	private String kode = "root";
	private String nama = "";
	private Integer harga;
	private Integer stok;
	private String url = "jdbc:mysql://localhost:3306/pt pudding";
			
	Connection con;
	Statement st;
	
	ResultSet rs;
	ResultSetMetaData rsm;
	
	public static Connect connect;
	
	public static Connect getConnection() {
		if(connect == null) {
			return new Connect();
		}
		return connect;
	}
	
	private Connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, kode, nama);
			st = con.createStatement();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	// SELECT
	public ResultSet execQuery(String query) {
		try {
			rs = st.executeQuery(query);
			rsm = rs.getMetaData();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return rs;
	}
	
	// INSERT/UPDATE/DELETE
	public void execUpdate(String query) {
		try {
			st.executeUpdate(query);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public PreparedStatement prepareStatement(String query) {
		PreparedStatement ps = null;
		try {
			ps = con.prepareStatement(query);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return ps;
	}
}
