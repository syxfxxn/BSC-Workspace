package com.bsc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bsc.beans.Users; // Assuming you have a User class

public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public ChangePassword() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {

			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet StudentServlet</title>");
			out.println("</head>");
			out.println("<body>");

			Users user = new Users();
			HttpSession session = request.getSession();
	
			try {

				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/bsc?allowPublicKeyRetrieval=true&useSSL=false", "root",
						"@dmin123");

				String query = "SELECT * FROM users WHERE id = ?";
				PreparedStatement preparedStatement = con.prepareStatement(query);

				preparedStatement.setInt(1, (int)session.getAttribute("id")); 
				

				// Execute the query
				ResultSet resultSet = preparedStatement.executeQuery();


				if (resultSet.next()) {
				    // Retrieve the data from the result set and set it in the Users object
				    user.setPassword(resultSet.getString("setPassword"));
;
				}


			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("user", user);
			RequestDispatcher rd = request.getRequestDispatcher("change-password.jsp");
			rd.forward(request, response);

			out.println("</body>");
			out.println("</html>");
			
			// Handle success or failure as needed
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			/* ---- LAYOUT FOR UPDATE QUERY ---- */

			// Step 1: Create HTML layout
			PrintWriter out = response.getWriter();
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<title>Servlet StudentServlet</title>");
			out.println("</head>");
			out.println("<body>");

			// Step 2: Create ArrayList using associated Beans
			
			Users u = new Users();

			// Step 3: Get variables (if neccessary)

			/* Example: */
			String Password = request.getParameter("cnp");
			HttpSession session = request.getSession();
			Users user_passed  = new Users();
			user_passed.setPassword(Password);
			
			
			
			// String password = request.getParameter("password");

			try {
				// Step 4: Connect to Database
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(
						"jdbc:mysql://localhost:3306/bsc?allowPublicKeyRetrieval=true&useSSL=false", "root",
						"@dmin123");

				// Step 5: Initialize MySQL Variables
				String query = null;
				PreparedStatement preparedStatement = null;

				// Step 6: Run query
				query = "UPDATE users SET password = ? WHERE id = ?";
				preparedStatement = con.prepareStatement(query);

				// Step 7: Create single variable from arraylist
				

				// Step 8: Set data to variable
				preparedStatement.setString(1, Password);
				preparedStatement.setInt(2, Integer.parseInt((String) session.getAttribute("id"))); // Assuming 'id' is
																									// an integer
				/* -- Add more here -- */

				// Step 9: Execute Query
				preparedStatement.executeUpdate();
				ResultSet resultSet = preparedStatement.executeQuery();
				
				u.setName(resultSet.getString("setPassword"));


				

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			request.setAttribute("user", u);
			response.sendRedirect("/bsc/change-password.jsp");
			out.println("</body>");
			out.println("</html>");

			// Handle success or failure as needed
		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}
