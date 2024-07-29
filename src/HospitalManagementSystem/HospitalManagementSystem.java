package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class HospitalManagementSystem {
	//private=for security data members
	//static=main method ke bhar koi obejct na banna pade
	//final=ek bar koi value dal di jaye throghout the program vo he chale
	private static final String url="jdbc:mysql://localhost:3306/hospital";
	private static final String username ="root";
	private static final String password = "HimGau704@";
	public static void main(String[] args) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver" );
		    
		}catch(ClassNotFoundException e) {
			e.printStackTrace();
			
		}
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		try {
			Connection connection = DriverManager.getConnection(url,username,password);
			@SuppressWarnings("unused")
			Patient patient = new Patient(connection, scanner);
			@SuppressWarnings("unused")
			Doctor doctor = new Doctor(connection);
			while(true) {
				System.out.println("HOSPITAL MANAGEMENT STSYTEM");
				System.out.println("1. Add Patient");
				System.out.println("2. View Patients");
				System.out.println("3. View Doctors");
				System.out.println("4. Book Appointment");
				System.out.println("5. Exit");
				System.out.println("Enter your choice");
				int Choice = scanner.nextInt();
				switch(Choice) {
				case 1:
				// Add Pateint 
				patient.addPatient();
				System.out.println();
				break;
				
				case 2:
				// View Pateints
				patient.viewPatients();
				System.out.println();
				break;
				
				
				case 3:
				// View Doctors
				doctor.viewDoctors();
				System.out.println();
				break;
				
				
				case 4:
				 bookAppointment(patient,doctor,connection,scanner);
					System.out.println();
					break;
				case 5:
					System.out.println("Thank you for using Hospital Management System");
				return;
				default:
					System.out.println("Enter valid Choice!!");
					break;
				
				}
			}
		}catch(SQLException e) {
			e.printStackTrace();
			
		}
		
	}
	public static void bookAppointment(Patient patient, Doctor doctor, Connection connection, Scanner scanner) {
		System.out.print("Enter Patient Id:");
		int PatientId=scanner.nextInt();
		System.out.print("enter Doctor Id:");
		int doctorId = scanner.nextInt();
		System.out.print("Enter appointment date(YYYY-MM-DD):");
		String appointmentDate = scanner.next();
		if(patient.getPatientById(PatientId)&& doctor.getDoctorsById(doctorId)) 
		{
			if(checkDoctorAvailability(doctorId, appointmentDate ,connection))
			{
				String appointmentQuery= "INSERT INTO appointments(patient_id, doctor_id, appointment_date) VALUES(?,?,?)";
				try {
					PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
					preparedStatement.setInt(1,PatientId);
					preparedStatement.setInt(2,doctorId);
					preparedStatement.setString(3,appointmentDate);
					int rowsAffected = preparedStatement.executeUpdate();
					if(rowsAffected>0) {
						System.out.println("Appointment Booked!");
						
					}else {
						System.out.println("Failed to Book Appointment!");
					}
					
					
					
				}catch (SQLException e) {
					e.printStackTrace();
				}
			
			}else {
				System.out.println("doctor not available on this date");
			}
			
		}else {
			System.out.println("Either doctor or patient doesn't exist!!!");
		}
		
	}
	public static boolean checkDoctorAvailability(int doctorId, String appointmentDate, Connection connection) {
		
	String query= "SELECT COUNT(*) FROM appointemnts WHERE doctor_id=? AND appointment_date =?";
	try {
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1,doctorId);
		preparedStatement.setString(2, appointmentDate);
		ResultSet resultSet = preparedStatement.executeQuery();
		if(resultSet.next()) {
			int count = resultSet.getInt(1);
			if(count==0) {
				return true;
				
			}else {
				return false;
				 
			}
		}
		
		
		
	}catch (SQLException e) {
		e.printStackTrace();
	}
	return false;
	
}

}
