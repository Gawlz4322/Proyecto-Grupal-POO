package Controller;

import Model.FinanceSystem;
import Model.User;
import View.MainMenu;
import View.LoginWindow;
import Utils.PasswordUtils;

import javax.swing.*;

/**
 * Controlador para la ventana de inicio de sesión.
 * Gestiona la autenticación del usuario y la transición al menú principal.
 */
public class LoginController {
    private LoginWindow view;
    private final AuthService authService;
    private final FinanceSystem financeSystem;
    private final MainMenu mainMenu;

    /**
     * Inicializa el controlador de login.
     *
     * @param authService   El servicio de autenticación.
     * @param financeSystem El sistema de finanzas.
     * @param mainMenu      La vista del menú principal.
     */
    public LoginController(AuthService authService, FinanceSystem financeSystem, MainMenu mainMenu) {
        this.financeSystem = financeSystem;
        this.mainMenu = mainMenu;
        this.authService = authService;
    }

    /**
     * Intenta iniciar sesión con las credenciales proporcionadas en la vista.
     * Si es exitoso, carga los datos del usuario y muestra el menú principal.
     * Si falla, muestra un mensaje de error.
     */
    public void attemptLogin() {
        String u = view.getUsername();
        char[] p = view.getPassword().toCharArray();
        try {
            User user = authService.login(u, p);
            financeSystem.login(user); // Load user data
            JOptionPane.showMessageDialog(view.getFrame(), "Bienvenido, " + user.getUsername());
            view.closeWindow();
            mainMenu.updateBalanceDisplay(financeSystem.getBalance());
            mainMenu.showWindow();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(view.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            PasswordUtils.zero(p);
            view.clearPassword();
        }
    }

    /**
     * Establece la vista asociada a este controlador.
     *
     * @param view La ventana de login.
     */
    public void setView(LoginWindow view) {
        this.view = view;
    }
}
