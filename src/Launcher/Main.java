package Launcher;

import Controlador.AuthService;
import Controlador.LoginController;
import Controlador.MenuController;
import Controlador.UserStore;
import Modelo.SistemaFinanzas;
import Vista.MenuPrincipal;
import Vista.VentanaLogin;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        String userDbPath = "data/users.json";

        // Inicializar capa de persistencia y autenticación
        UserStore store = new UserStore(userDbPath);
        AuthService auth = new AuthService(store);

        // Inicializar modelo de finanzas
        SistemaFinanzas modeloFinanzas = new SistemaFinanzas();

        SwingUtilities.invokeLater(() -> {
            // Crear controlador de menú
            MenuController menuController = new MenuController(modeloFinanzas);
            
            // Crear vista del menú principal (sin mostrar aún)
            MenuPrincipal menuPrincipal = new MenuPrincipal(menuController);
            menuPrincipal.getFrame().setVisible(false); // Ocultar hasta login exitoso
            
            // Crear controlador de login
            LoginController loginController = new LoginController(auth, modeloFinanzas, menuPrincipal);
            
            // Crear y mostrar ventana de login
            VentanaLogin ventanaLogin = new VentanaLogin(loginController);
            loginController.setVista(ventanaLogin);
            ventanaLogin.mostrarVentana();
        });
    }
}