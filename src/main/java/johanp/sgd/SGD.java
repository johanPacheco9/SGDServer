/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package johanp.sgd;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import johanp.Domain.Services.FileF.FIleService;
import johanp.Domain.Services.FileF.IFileService;

/**
 *
 * @author johan
 */
public class SGD {

      private static final int PUERTO = 8083; // Puerto para UserService
  
    
    private static final String SERVICIO_NOMBRE = "fileService";
 
    public static void main(String[] args) {
      try {
           
            Registry registryFileService = LocateRegistry.createRegistry(PUERTO);
            
   
            IFileService fileService = new FIleService();
            
            
            registryFileService.rebind(SERVICIO_NOMBRE, fileService);
           
            
            

          
            
            
            System.out.println("FileService corriendo en el puerto:" + PUERTO);
            
        } catch (RemoteException e) {
            System.out.println("Error de RMI: " + e.getMessage());
        }
    }
}
