package johanp.Domain.Services.UserF;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import johanp.Domain.Models.DatabaseConnection;
import johanp.Domain.Models.User;

/**
 * Implementación del servicio de usuario para la autenticación.
 */
public class UserService extends UnicastRemoteObject implements IUserService {

    public UserService() throws RemoteException {
        super();
    }

    @Override
    public User login(String userName, String pass) throws RemoteException {
        System.out.println("username: " + userName + " y pass: " + pass);
        // Definir la consulta SQL
        String sql = "SELECT id, name, role FROM `Users` WHERE name = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Conexión a la base de datos." + conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, userName);
                stmt.setString(2, pass);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        int userId = rs.getInt("id");
                        String username = rs.getString("name");
                        int role = rs.getInt("role");
                        User user = new User(userId, username,role);
                        System.out.println("User devuelto: " + user.getName());
                        return user;
                    } else {
                        System.out.println("No se encontraron resultados.");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error durante el login: " + e.getMessage());
        }
        return null; // Retornar null si el login falla
    }
    @Override
    public int addUser(User user) throws RemoteException {
        // Definir la consulta SQL para insertar un nuevo usuario
        String sql = "INSERT INTO Users (name, password, role) VALUES (?, ?, ?)";
        
        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Conexión a la base de datos: " + conn);
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Establecer los valores para el PreparedStatement
                stmt.setString(1, user.getName());
                stmt.setString(2, user.getPassword());
                stmt.setInt(3, user.getRole());
                
                // Ejecutar la consulta de inserción
                int affectedRows = stmt.executeUpdate();
                
                // Retornar 1 si se afectaron filas (es decir, inserción exitosa), 0 en caso contrario
                return affectedRows > 0 ? 1 : 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error al agregar el usuario: " + e.getMessage());
        }
    }
    
}

