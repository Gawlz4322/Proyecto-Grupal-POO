package Controller;

import View.LoginWindow;
import View.RegisterWindow;
import javax.swing.JOptionPane;
import java.util.Arrays;

/**
 * Controlador para la ventana de registro.
 * Gestiona el proceso de registro de nuevos usuarios.
 */
public class RegisterController {
    private RegisterWindow view;
    private final AuthService authService;
    private final LoginWindow loginWindow;

    /**
     * Inicializa el controlador de registro.
     *
     * @param authService El servicio de autenticación.
     * @param loginWindow La ventana de login para volver tras el registro.
     */
    public RegisterController(AuthService authService, LoginWindow loginWindow) {
        this.authService = authService;
        this.loginWindow = loginWindow;
    }

    /**
     * Establece la vista asociada a este controlador.
     *
     * @param view La ventana de registro.
     */
    public void setView(RegisterWindow view) {
        this.view = view;
    }

    /**
     * Intenta registrar un nuevo usuario con los datos proporcionados en la vista.
     * Valida que las contraseñas coincidan y delega el registro al servicio de
     * autenticación.
     */
    public void attemptRegister() {
        String username = view.getUsername();
        char[] password = view.getPassword();
        char[] confirmPassword = view.getConfirmPassword();

        try {
            // Validate passwords match
            if (!Arrays.equals(password, confirmPassword)) {
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }

            // Attempt registration
            authService.register(username, password);

            // If successful, show message and return to login
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
            // Clear passwords from memory
            Arrays.fill(password, '\0');
            Arrays.fill(confirmPassword, '\0');
        }
    }

    /**
     * Vuelve a la ventana de inicio de sesión.
     */
    public void returnToLogin() {
        view.clearFields();
        view.closeWindow();
        loginWindow.showWindow();
    }
}