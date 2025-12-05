package Controller;

import View.LoginWindow;
import View.RegisterWindow;
import javax.swing.JOptionPane;
import java.util.Arrays;

public class RegisterController {
    private RegisterWindow view;
    private final AuthService authService;
    private final LoginWindow loginWindow;

    public RegisterController(AuthService authService, LoginWindow loginWindow) {
        this.authService = authService;
        this.loginWindow = loginWindow;
    }

    public void setView(RegisterWindow view) {
        this.view = view;
    }

    public void attemptRegister() {
        String username = view.getUsername();
        char[] password = view.getPassword();
        char[] confirmPassword = view.getConfirmPassword();

        try {
            if (!Arrays.equals(password, confirmPassword)) {
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }

            authService.register(username, password);

            JOptionPane.showMessageDialog(
                    view.getFrame(),
                    "Usuario registrado exitosamente",
                    "Registro exitoso",
                    JOptionPane.INFORMATION_MESSAGE);

            view.clearFields();
            view.closeWindow();
            loginWindow.showWindow();

        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                    view.getFrame(),
                    e.getMessage(),
                    "Error en registro",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '\0');
            Arrays.fill(confirmPassword, '\0');
        }
    }

    public void returnToLogin() {
        view.clearFields();
        view.closeWindow();
        loginWindow.showWindow();
    }
}