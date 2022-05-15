package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

public class BillManage {

	private Connection connect() {
		Connection con = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");// this sample 1

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/electri?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC",
					"root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return con;
	}

	//Insert Bill
	public String insertBill(String customerName, String billId, String cardNo, String ccv, String expiredDate) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement
			String query = " insert into bill(`bID`,`customerName`,`billId`,`cardNo`,`ccv`,`expiredDate`)"
					+ " values (?, ?, ?, ?, ?, ?)";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, customerName);
			preparedStmt.setString(3, billId);
			preparedStmt.setString(4, cardNo);
			preparedStmt.setString(5, ccv);
			preparedStmt.setString(6, expiredDate);
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newBill = readBill(); 
			output =  "{\"status\":\"success\", \"data\": \"" + newBill + "\"}";  

		} catch (Exception e) {
			output =  "{\"status\":\"error\", \"data\": \"Error while inserting the Bill. Try again\"}";  
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Read Bill
	public String readBill() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>Customer Name</th><th>Bill ID</th><th>Card No</th><th>CCV</th><th>Expired Date</th><th>Update</th><th>Remove</th></tr>";
			String query = "select * from bill";
			Statement stmt = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmt).executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String bID = Integer.toString(rs.getInt("bID"));
				String customerName = rs.getString("customerName");
				String billId = rs.getString("billId");
				String cardNo = rs.getString("cardNo");
				String ccv = rs.getString("ccv");
				String expiredDate = rs.getString("expiredDate");

				// Add into the html table
				output += "<tr><td><input id='hidBillIDUpdate' name='hidCompIDUpdate' type='hidden' value='" + bID + "'>" + customerName + "</td>";
				output += "<td>" + billId + "</td>";
				output += "<td>" + cardNo + "</td>";
				output += "<td>" + ccv + "</td>";
				output += "<td>" + expiredDate + "</td>";
			
				// buttons     
				output +="<td><input name='btnUpdate' type='button' value='Update' class='btnUpdate btn btn-secondary'></td>"       
						+ "<td><input name='hidItemIDDelete' type='button' value='Remove' class='btnRemove btn btn-danger' data-bid='" + bID + "'>" + "</td></tr>";
				
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the Bill.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	//Update Bill
	public String updateBill(String bID, String customerName, String billId, String cardNo, String ccv, String expiredDate) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for updating.";
			}

			// create a prepared statement
			String query = "UPDATE bill SET customerName=?,billId=?,cardNo=?,ccv=?,expiredDate=?" + "WHERE bID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setString(1, customerName);
			preparedStmt.setString(2, billId);
			preparedStmt.setString(3, cardNo);
			preparedStmt.setString(4, ccv);
			preparedStmt.setString(5, expiredDate);
			preparedStmt.setInt(6, Integer.parseInt(bID));

			// execute the statement    
			preparedStmt.execute();    
			con.close();  
			String newBill = readBill();    
			output = "{\"status\":\"success\", \"data\": \"" +  newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output =  "{\"status\":\"error\", \"data\": \"Error while updating the Bill.\"}";   
			System.err.println(e.getMessage());   
		} 	 
	  return output;  
	} 

	// Delete Bill
	public String deleteBill(String bID) {
		String output = "";

		try {
			Connection con = connect();

			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}

			// create a prepared statement
			String query = "delete from bill where bID=?";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, Integer.parseInt(bID));

			// execute the statement
			preparedStmt.execute();
			con.close(); 
	 
			String  newBill = readBill();    
			output = "{\"status\":\"success\", \"data\": \"" +  newBill + "\"}";    
		}   
		catch (Exception e)   
		{    
			output = "Error while deleting the Bill.";    
			System.err.println(e.getMessage());   
		} 
	 
		return output;  
	}

}

