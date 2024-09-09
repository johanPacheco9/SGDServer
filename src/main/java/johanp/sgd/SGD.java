package johanp.sgd;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import johanp.Domain.Services.FileF.FIleService;
import johanp.Domain.Services.FileF.IFileService;

public class SGD {

    private static final int PUERTO = 8083; // Puerto para UserService
    private static final String SERVICIO_NOMBRE = "fileService";
    private static final String FILE_DIRECTORY = "/home/cliente/share";

    public static void main(String[] args) {
        java.io.File fileToSave = new java.io.File(FILE_DIRECTORY + "holassss ");
        try (FileOutputStream fileOutput = new FileOutputStream(fileToSave)) {
            String content = "This is a test.";
            fileOutput.write(content.getBytes());
            System.out.println("File written successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            // Configura la IP del servidor RMI
            System.setProperty("java.rmi.server.hostname", "10.153.70.123"); // Cambia la IP por la correcta
            
            Registry registryFileService = LocateRegistry.createRegistry(PUERTO);
            
            IFileService fileService = new FIleService();
            //para nuevo
            
            registryFileService.rebind(SERVICIO_NOMBRE, fileService);
            
            System.out.println("FileService corriendo en el puerto: " + PUERTO);
            
        } catch (RemoteException e) {
            System.out.println("Error de RMI: " + e.getMessage());
        }
    }
}