import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import johanp.Domain.Services.FileF.FIleService;
import johanp.Domain.Services.FileF.IFileService;

public class SGD {

    private static final int PUERTO = 8083; // Puerto para UserService
    private static final String SERVICIO_NOMBRE = "fileService";

    public static void main(String[] args) {
        try {
            // Configura la IP del servidor RMI
            System.setProperty("java.rmi.server.hostname", "192.168.1.15"); // Cambia la IP por la correcta
            
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
