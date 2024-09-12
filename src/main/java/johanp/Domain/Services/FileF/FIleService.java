/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package johanp.Domain.Services.FileF;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import johanp.Domain.Models.DatabaseConnection;
import johanp.Domain.Models.File;

/**
 *
 * @author johan
 */
public class FIleService extends UnicastRemoteObject implements IFileService {

    private static final String FILE_DIRECTORY = "/home/cliente/share/";

    public FIleService() throws RemoteException {
        super();
    }

    @Override
    public byte[] getFile(String path) throws RemoteException {
        System.out.println("path:" + path);
        java.io.File fileToRead = new java.io.File(path);
        try (FileInputStream fileInput = new FileInputStream(fileToRead)) {
            byte[] fileData = new byte[(int) fileToRead.length()];
            fileInput.read(fileData);
            return fileData;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RemoteException("Error reading file: " + e.getMessage());
        }
    }

    @Override
    public String addFile(File file, String userName, long author_id) throws RemoteException {
    // Definir el directorio del usuario
    String userDirectoryPath = FILE_DIRECTORY + userName + "/";
    java.io.File userDirectory = new java.io.File(userDirectoryPath);
    
    // Crear el directorio del usuario si no existe
    if (!userDirectory.exists()) {
        if (!userDirectory.mkdirs()) {
            return "Error: Could not create directory for user " + userName;
        }
    }

    
    java.io.File fileToSave = new java.io.File(userDirectoryPath + file.getName());
    try (FileOutputStream fileOutput = new FileOutputStream(fileToSave)) {
        byte[] fileData = file.getContent(); // Obtener los bytes del archivo
        fileOutput.write(fileData); // Escribir los bytes al archivo
    } catch (IOException e) {
        e.printStackTrace();
        return "Error adding file: " + e.getMessage();
    }
    String sql = "INSERT INTO Files (name, size, path, author_id) VALUES (?, ?, ?, ?)";
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, file.getName());
        stmt.setLong(2, file.getSize());
        stmt.setString(3, fileToSave.getAbsolutePath());
        //stmt.setBytes(4, file.getContent()); // Guardar el contenido del archivo
        stmt.setLong(4, author_id);
        
        int affectedRows = stmt.executeUpdate();
        if (affectedRows > 0) {
            return "File and metadata added successfully: " + file.getName();
        } else {
            return "Error: Could not insert file information into the database.";
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Error inserting file into the database: " + e.getMessage();
    }
}

    
    @Override
    public String deleteFile(String fileName) throws RemoteException {
        java.io.File fileToDelete = new java.io.File(FILE_DIRECTORY + fileName);
        if (fileToDelete.delete()) {
            return "File deleted successfully: " + fileName;
        } else {
            return "Error deleting file: " + fileName;
        }
    }
 
    @Override
    public boolean searchFile(String fileName) throws RemoteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void asignarPermisos(File file, String userName, int permiso) {
        // Aquí implementas la lógica para guardar los permisos en la base de datos.
        // Permiso 1 = Lector, Permiso 2 = Escritor.
        System.out.println("Permisos asignados para el archivo: " + file.getName() + " al usuario: " + userName);
    }

    
    public String modifyFile(File file, String userName) throws RemoteException {
        String filePath = FILE_DIRECTORY + userName + "/" + file.getName();
        java.io.File fileToModify = new java.io.File(filePath);

        if (fileToModify.exists()) {
            try (FileOutputStream fileOutput = new FileOutputStream(fileToModify)) {
                fileOutput.write(file.getContent());

                // Notificar a los usuarios que un archivo ha sido modificado
                String mensaje = "El archivo " + file.getName() + " ha sido modificado por " + userName;
                //SGD.enviarBroadcast(mensaje);  // Broadcast a todos los usuarios

                return "Archivo modificado correctamente: " + file.getName();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error al modificar el archivo: " + e.getMessage();
            }
        } else {
            return "El archivo no existe: " + file.getName();
        }
    }

    @Override
    public List<File> getFiles(String userName) throws RemoteException {
    List<File> fileList = new ArrayList<>();
    System.out.println("obtener archivos de:" + userName);
    // Usar LIKE para buscar dentro del path
    String sql = "SELECT name, path, size, author_id FROM Files WHERE path LIKE ?";

    try (Connection conn = DatabaseConnection.getConnection()) {
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Establecer el parámetro del nombre de usuario en la consulta
            stmt.setString(1, "%" + userName + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("name");
                    String path = rs.getString("path");
                    long size = rs.getLong("size");
                    int authorId = rs.getInt("author_id");
                    File file = new File(name);
                    file.setSize(size);
                    file.setPath(path);
                    file.setAuthorId(authorId);
                    fileList.add(file);
                }
            }
        }
    } catch (SQLException e) {
        throw new RemoteException("Error retrieving file list: " + e.getMessage());
    }
    
    // Imprimir detalles de cada archivo
    for (File file : fileList) {
        System.out.println("Name: " + file.getName());
        System.out.println("Path: " + file.getPath());
        System.out.println("Size: " + file.getSize());
        System.out.println("Author ID: " + file.getAuthorId());
        System.out.println("-----------");
    }

    return fileList;
}

}
