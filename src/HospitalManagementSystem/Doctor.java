package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.util.Scanner;

public class Doctor {
	private Connection connection;
	
	//private Scanner scanner;
	
	public Doctor(Connection connection) {
	this.connection=connection;
	//this.scanner=scanner;
	
	}
		public void viewDoctors() {
		String query ="select * from doctors";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet resultSet= preparedStatement.executeQuery();
		    System.out.println("doctors: ");
		    System.out.println("+------------+--------------------+------------------+");
		    System.out.println("| Doctor ID  | Name               | Specialization        |");
		    System.out.println("+------------+--------------------+------------------+");
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				
				String specialization = resultSet.getString("specialization");
				
				System.out.printf("|%-12s|%-20s|%-18s|\n", id, name, specialization);
				System.out.println("+------------+--------------------+------------------+");
				
			}
	}catch (SQLException e) {
		e.printStackTrace();
		
	}
	}
	public boolean getDoctorsById(int id) {
		String query= "Select * from patients where id =?";
		try {
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			preparedStatement.setInt (1,id);
			ResultSet resultSet = preparedStatement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
			else {
				return false;
			}
			
			}catch (SQLException e) {
				e.printStackTrace();
				
			
			
		}
		return false;
	}

}
