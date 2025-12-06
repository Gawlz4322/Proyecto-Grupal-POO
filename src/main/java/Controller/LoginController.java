package Controller;

import Model.FinanceSystem;
import Model.User;
import View.MainMenu;
import View.LoginWindow;
import Utils.PasswordUtils;

import javax.swing.*;

/**
 * Controlador encargado de gestionar la lógica de la ventana de inicio de
 * sesión.
 */
public class LoginController {
    private LoginWindow view;
    private final AuthService authService;
    private final FinanceSystem financeSystem;
    private final MainMenu mainMenu;

    /**
     * Constructor del controlador de login.
     *
     * @param authService   Servicio de autenticación.
     * @param financeSystem Sistema financiero principal.
     * @param mainMenu      Referencia al menú principal (para mostrarlo tras login
     *                      exitoso).
     */
    public LoginController(AuthService authService, FinanceSystem financeSystem, MainMenu mainMenu) {
        this.financeSystem = financeSystem;
        this.mainMenu = mainMenu;
        this.authService = authService;
    }

    /**
     * Intenta iniciar sesión con los datos ingresados en la vista.
     * Si es exitoso, cierra la ventana de login y abre el menú principal.
     */
    public void attemptLogin() {
        String u = view.getUsername();
        char[] p = view.getPassword().toCharArray();
        try {
            User user = authService.login(u, p);
            financeSystem.login(user);
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
     * @param view Ventana de login.
     */
    public void setView(LoginWindow view) {
        this.view = view;
    }
}
