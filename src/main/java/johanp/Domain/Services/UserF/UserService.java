/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
 *
 * @author johan
 */
public class UserService extends UnicastRemoteObject implements IUserService {

    public UserService() throws RemoteException {
        super();
    }

    @Override
    public User login(String userName, String pass) throws RemoteException {
        // Definir la consulta SQL
        String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, userName);
            stmt.setString(2, pass);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Crear y devolver un objeto User con el id y el username
                    int userId = rs.getInt("id");
                    String username = rs.getString("username");

                    User user = new User(userId, username);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RemoteException("Error during login: " + e.getMessage());
        }
        return null; // Retornar null si el login falla
    }

}
