import Controller.AuthService;
import Controller.LoginController;
import Controller.MenuController;
import Controller.RegisterController;
import Controller.UserStore;
import Controller.FinanceStore;
import Model.FinanceSystem;
import View.MainMenu;
import View.LoginWindow;
import View.RegisterWindow;

import javax.swing.SwingUtilities;

/**
 * Clase principal de la aplicación.
 * Inicializa los componentes y arranca la interfaz gráfica.
 */
public class Main {
    /**
     * Punto de entrada de la aplicación.
     * Configura la persistencia, los modelos, controladores y vistas.
     *
     * @param args Argumentos de la línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        String userDbPath = "src/main/resources/data/users.json";
        String financeDbPath = "src/main/resources/data/finances.json";

        // Configurar FlatLaf (Mejora UI)
        try {
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        // Inicializar persistencia y capa de autenticación
        UserStore store = new UserStore(userDbPath);
        AuthService auth = new AuthService(store);

        // Inicializar almacenamiento de finanzas
        FinanceStore financeStore = new FinanceStore(financeDbPath);

        // Inicializar modelo de finanzas
        FinanceSystem financeSystem = new FinanceSystem(financeStore);

        SwingUtilities.invokeLater(() -> {
            // Crear controlador del menú
            MenuController menuController = new MenuController(financeSystem);

            // Crear vista del menú principal (oculta inicialmente)
            MainMenu mainMenu = new MainMenu(menuController);
            mainMenu.getFrame().setVisible(false); // Ocultar hasta login exitoso

            // Crear ventana de login
            LoginController loginController = new LoginController(auth, financeSystem, mainMenu);
            LoginWindow loginWindow = new LoginWindow(loginController);
            loginController.setView(loginWindow);

            // Crear ventana de registro y conectarla
            RegisterController registerController = new RegisterController(auth, loginWindow);
            RegisterWindow registerWindow = new RegisterWindow(registerController);

            // IMPORTANTE: Conectar ventana de registro con login
            loginWindow.setRegisterWindow(registerWindow);

            // Mostrar ventana de login
            loginWindow.showWindow();
        });
    }
}