package johanp.sgd;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

import johanp.Domain.Models.DatabaseConnection;
import johanp.Domain.Services.FileF.FIleService;
import johanp.Domain.Services.FileF.IFileService;
import johanp.Domain.Services.UserF.IUserService;
import johanp.Domain.Services.UserF.UserService;

public class SGD {

    private static final int PUERTO = 8083;
    private static final int PUERTOUSER = 8089;
    private static final String SERVICIO_NOMBRE = "fileService";
    private static final String SERVICIO_USUARIO = "userService";
    //private static final String FILE_DIRECTORY = "/home/cliente/share/";

    // Mapa para almacenar los usuarios y sus hilos
    //private static ConcurrentHashMap<User, Thread> usuariosActivos = new ConcurrentHashMap<>();
    //private static ExecutorService poolHilos = Executors.newCachedThreadPool();

    public static void main(String[] args) {
        try {
            System.setProperty("java.rmi.server.hostname", "192.168.1.15");
            

            System.out.println("Drivers JDBC registrados:");
            Enumeration<Driver> drivers = DriverManager.getDrivers();
            while (drivers.hasMoreElements()) {
                Driver driver = drivers.nextElement();
                System.out.println("Driver: " + driver.getClass().getName());
            }

            Registry registryFileService = LocateRegistry.createRegistry(PUERTO);
            IFileService fileService = new FIleService();
            registryFileService.rebind(SERVICIO_NOMBRE, fileService);


            Registry registryUserService = LocateRegistry.createRegistry(PUERTOUSER);
            IUserService userService = new UserService();
            registryUserService.rebind(SERVICIO_USUARIO, userService);


            System.out.println("FileService corriendo en el puerto: " + PUERTO);
            System.out.println("UserService corriendo en el puerto: " + PUERTOUSER);

        } catch (RemoteException e) {
            System.out.println("Error de RMI: " + e.getMessage());
        }
    }

    /*  Método para enviar mensajes a todos los usuarios conectados
    public static void enviarBroadcast(String mensaje) {
        usuariosActivos.forEach((usuario, hilo) -> {
            System.out.println("Enviando mensaje a " + usuario.getName() + ": " + mensaje);
            // Aquí puedes implementar lógica de sockets o alguna otra forma de comunicación
            // entre el servidor y los clientes para que realmente reciban el mensaje.
        });
    }
    */
    public static void TestDb() {
        Connection connection = null;
        try {
            connection = DatabaseConnection.getConnection();
            // Puedes agregar más pruebas o consultas aquí si lo deseas.
        } catch (SQLException e) {
            System.err.println("Error durante la prueba de la conexión a la base de datos: " + e.getMessage());
        } finally {
            // Asegúrate de cerrar la conexión si se ha abierto
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Conexión cerrada.");
                } catch (SQLException e) {
                    System.err.println("Error al cerrar la conexión: " + e.getMessage());
                }
            }
        }
    }
    

    /*  Método para agregar un usuario y asignarle un hilo
    public static void agregarUsuario(User user) {
        if (!usuariosActivos.containsKey(user)) {
            Thread hiloUsuario = new Thread(() -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println("Hilo para el usuario " + user.getName() + " está activo.");
                        Thread.sleep(5000);  // Simula actividad
                    }
                } catch (InterruptedException e) {
                    System.out.println("Hilo del usuario " + user.getName() + " ha sido detenido.");
                }
            });
            poolHilos.execute(hiloUsuario);

            usuariosActivos.put(user, hiloUsuario);
            System.out.println("Usuario " + user.getName() + " ha sido añadido con el hilo " + hiloUsuario.getId());
        } else {
            System.out.println("Usuario " + user.getName() + " ya está activo.");
        }
    }
    */

    /*  Método para eliminar un usuario y detener su hilo
    public static void eliminarUsuario(User user) {
        if (usuariosActivos.containsKey(user)) {
            Thread hilo = usuariosActivos.get(user);
            hilo.interrupt();
            usuariosActivos.remove(user);
            System.out.println("Usuario " + user.getName() + " ha sido removido.");
        } else {
            System.out.println("Usuario " + user.getName() + " no está activo.");
        }
    }
*/
    /*  Método para reactivar un hilo de usuario inactivo
    public static void reactivarUsuario(User user) {
        if (usuariosActivos.containsKey(user)) {
            Thread hilo = usuariosActivos.get(user);
            if (!hilo.isAlive()) {
                agregarUsuario(user);  // Si el hilo está muerto, lo reactiva
            } else {
                System.out.println("El hilo del usuario " + user.getName() + " ya está activo.");
            }
        } else {
            agregarUsuario(user);  // Si no existe el usuario, lo agrega
        }
    }
    */
}