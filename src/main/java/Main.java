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
 * Inicializa los componentes, configura el tema y lanza la interfaz gráfica.
 */
public class Main {
    public static void main(String[] args) {
        String userDbPath = "src/main/resources/data/users.json";
        String financeDbPath = "src/main/resources/data/finances.json";

        try {
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            System.err.println("Failed to initialize FlatLaf");
        }

        UserStore store = new UserStore(userDbPath);
        AuthService auth = new AuthService(store);

        FinanceStore financeStore = new FinanceStore(financeDbPath);
        FinanceSystem financeSystem = new FinanceSystem(financeStore);

        SwingUtilities.invokeLater(() -> {
            MenuController menuController = new MenuController(financeSystem);

            MainMenu mainMenu = new MainMenu(menuController);
            mainMenu.getFrame().setVisible(false);

            LoginController loginController = new LoginController(auth, financeSystem, mainMenu);
            LoginWindow loginWindow = new LoginWindow(loginController);
            loginController.setView(loginWindow);

            RegisterController registerController = new RegisterController(auth, loginWindow);
            RegisterWindow registerWindow = new RegisterWindow(registerController);

            loginWindow.setRegisterWindow(registerWindow);

            loginWindow.showWindow();
        });
    }
}