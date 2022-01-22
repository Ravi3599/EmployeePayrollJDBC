package com.bridgelabz.employeepayrolljdbcapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.mysql.cj.xdevapi.Statement;

public class EmployeePayrollDBService {
	
	private Connection getConnection() throws SQLException {
		
		String jdbcURL = "jdbc:mysql://localhost:3306/employee_payroll?useSSL=false";
		String userName = "root";
		String password = "Imravi@99";
		Connection connection;
		
		System.out.println("Connecting to the database : "+jdbcURL);
		connection = DriverManager.getConnection(jdbcURL, userName, password);
		System.out.println("Connection is Succcessfully Established!! "+connection);
		
		return connection;
	}
	
	public List<EmployeePayrollData> readData(){
		
		String sqlStatement = "SELECT emp_id, emp_name, basic_pay, start FROM employee JOIN payroll ON employee.payroll_id = payroll.payroll_id;";
		List<EmployeePayrollData> employeePayrollList = new ArrayList<>();
				
		try (Connection connection = getConnection();){
			java.sql.Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sqlStatement);
			
			while(resultSet.next()) {
				int id = resultSet.getInt("emp_id");
				String name = resultSet.getString("emp_name");
				double basicSalary = resultSet.getDouble("basic_pay");
				LocalDate startDate = resultSet.getDate("start").toLocalDate();
				employeePayrollList.add(new EmployeePayrollData(id, name, basicSalary, startDate));
			}
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return employeePayrollList;
	}
}