/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanp.Domain.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author johan
 */

 public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/usuarios";
    private static final String USER = "root"; 
    private static final String PASSWORD = "1098825894";

    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            if (connection != null && !connection.isClosed()) {
                System.out.println("Conexión a la base de datos exitosa.");
            } else {
                System.out.println("No se pudo conectar a la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Error durante la conexión a la base de datos: " + e.getMessage());
            throw e;
        }
        return connection;
    }
}