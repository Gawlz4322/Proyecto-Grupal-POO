import Controlador.AuthService;
import Controlador.LoginController;
import Controlador.MenuController;
import Controlador.RegistroController;
import Controlador.UserStore;
import Modelo.SistemaFinanzas;
import Vista.MenuPrincipal;
import Vista.VentanaLogin;
import Vista.VentanaRegistro;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        String userDbPath = "src/main/resources/data/users.json";
        String financeDbPath = "src/main/resources/data/finances.json";

        // Inicializar capa de persistencia y autenticación
        UserStore store = new UserStore(userDbPath);
        AuthService auth = new AuthService(store);

        // Inicializar store de finanzas
        Controlador.FinanceStore financeStore = new Controlador.FinanceStore(financeDbPath);

        // Inicializar modelo de finanzas
        SistemaFinanzas modeloFinanzas = new SistemaFinanzas(financeStore);

        SwingUtilities.invokeLater(() -> {
            // Crear controlador de menú
            MenuController menuController = new MenuController(modeloFinanzas);

            // Crear vista del menú principal (sin mostrar aún)
            MenuPrincipal menuPrincipal = new MenuPrincipal(menuController);
            menuPrincipal.getFrame().setVisible(false); // Ocultar hasta login exitoso

            // Crear ventana de login
            LoginController loginController = new LoginController(auth, modeloFinanzas, menuPrincipal);
            VentanaLogin ventanaLogin = new VentanaLogin(loginController);
            loginController.setVista(ventanaLogin);

            // Crear ventana de registro y conectarla
            RegistroController registroController = new RegistroController(auth, ventanaLogin);
            VentanaRegistro ventanaRegistro = new VentanaRegistro(registroController);

            // IMPORTANTE: Conectar ventana de registro con login
            ventanaLogin.setVentanaRegistro(ventanaRegistro);

            // Mostrar ventana de login
            ventanaLogin.mostrarVentana();
        });
    }
}