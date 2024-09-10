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
    public byte[] getFile(String fileName) throws RemoteException {
        java.io.File fileToRead = new java.io.File(FILE_DIRECTORY + fileName);
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
    public String addFile(File file, String userName) throws RemoteException {
        // Agregar separador de directorio entre FILE_DIRECTORY y el userName
        String userDirectoryPath = FILE_DIRECTORY + userName + "/";
        java.io.File userDirectory = new java.io.File(userDirectoryPath);
        if (!userDirectory.exists()) {
            if (!userDirectory.mkdirs()) {
                return "Error: Could not create directory for user " + userName;
            }
        }
        java.io.File fileToSave = new java.io.File(userDirectoryPath + file.getName());

        try (FileOutputStream fileOutput = new FileOutputStream(fileToSave)) {
            byte[] fileData = file.getContent(); // Obtener los bytes del archivo
            fileOutput.write(fileData); // Escribir los bytes al archivo
            return "File added successfully: " + file.getName();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error adding file: " + e.getMessage();
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

}
