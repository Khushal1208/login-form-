package mypack;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.*;

@WebServlet("/LoginBackend")
public class LoginBackend extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database URL, Username, and Password
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Login_Form_data?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "KHUpr@1208";

    public LoginBackend() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try {
            // Load MySQL JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Prepare SQL insert statement
            String sql = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            // Execute the insert statement
            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                out.println("A new user was inserted successfully!");
            } else {
                out.println("Failed to insert the new user.");
            }

            // Close the connection
            statement.close();
            connection.close();

        } catch (ClassNotFoundException e) {
            out.println("Error loading MySQL driver: " + e.getMessage());
            e.printStackTrace(out);
        } catch (SQLException e) {
            out.println("Error executing SQL: " + e.getMessage());
            e.printStackTrace(out);
        } catch (Exception e) {
            out.println("General error: " + e.getMessage());
            e.printStackTrace(out);
        } finally {
            out.close();
        }
    }
}
