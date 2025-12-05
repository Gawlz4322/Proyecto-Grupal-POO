package Controller;

import Model.FinanceSystem;
import Model.User;
import View.MainMenu;
import View.LoginWindow;
import Utils.PasswordUtils;

import javax.swing.*;

public class LoginController {
    private LoginWindow view;
    private final AuthService authService;
    private final FinanceSystem financeSystem;
    private final MainMenu mainMenu;

    public LoginController(AuthService authService, FinanceSystem financeSystem, MainMenu mainMenu) {
        this.financeSystem = financeSystem;
        this.mainMenu = mainMenu;
        this.authService = authService;
    }

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

    public void setView(LoginWindow view) {
        this.view = view;
    }
}
