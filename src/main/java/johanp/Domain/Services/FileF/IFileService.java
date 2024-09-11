/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package johanp.Domain.Services.FileF;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import johanp.Domain.Models.File;

/**
 *
 * @author johan
 */
public interface IFileService extends Remote{
    byte[] getFile(String path) throws RemoteException;
    List<File> getFiles(String userName) throws  RemoteException;
    String addFile(File file, String userName,int author_id) throws RemoteException;
    String deleteFile(String fileName) throws RemoteException;
    boolean searchFile(String fileName) throws RemoteException;

    //
    void asignarPermisos(File file, String userName, int permiso) throws RemoteException;
    String modifyFile(File file, String userName) throws RemoteException;  // Método para modificar archi
}




