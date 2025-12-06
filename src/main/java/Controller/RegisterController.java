package Controller;

import View.LoginWindow;
import View.RegisterWindow;
import javax.swing.JOptionPane;
import java.util.Arrays;

/**
 * Controlador encargado de la lógica de registro de nuevos usuarios.
 */
public class RegisterController {
    private RegisterWindow view;
    private final AuthService authService;
    private final LoginWindow loginWindow;

    /**
     * Constructor del controlador de registro.
     *
     * @param authService Servicio de autenticación.
     * @param loginWindow Ventana de login para volver tras el registro.
     */
    public RegisterController(AuthService authService, LoginWindow loginWindow) {
        this.authService = authService;
        this.loginWindow = loginWindow;
    }

    /**
     * Establece la vista asociada a este controlador.
     *
     * @param view Ventana de registro.
     */
    public void setView(RegisterWindow view) {
        this.view = view;
    }

    /**
     * Intenta registrar un nuevo usuario validando que las contraseñas coincidan.
     */
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

    /**
     * Cancela el registro y vuelve a la pantalla de inicio de sesión.
     */
    public void returnToLogin() {
        view.clearFields();
        view.closeWindow();
        loginWindow.showWindow();
    }
}