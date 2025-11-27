package Controlador;

import Vista.VentanaLogin;
import Vista.VentanaRegistro;
import javax.swing.JOptionPane;
import java.util.Arrays;

public class RegistroController {
    private VentanaRegistro vista;
    private final AuthService authService;
    private final VentanaLogin loginView;

    public RegistroController(AuthService authService, VentanaLogin loginView) {
        this.authService = authService;
        this.loginView = loginView;
    }

    public void setVista(VentanaRegistro vista) {
        this.vista = vista;
    }

    public void intentarRegistro() {
        String username = vista.getUsuario();
        char[] password = vista.getClave();
        char[] confirmPassword = vista.getConfirmarClave();

        try {
            // Validar que las contraseñas coincidan
            if (!Arrays.equals(password, confirmPassword)) {
                throw new IllegalArgumentException("Las contraseñas no coinciden");
            }

            // Intentar registro
            authService.register(username, password);
            
            // Si tiene éxito, mostrar mensaje y volver al login
            JOptionPane.showMessageDialog(
                vista.getFrame(),
                "Usuario registrado exitosamente",
                "Registro exitoso",
                JOptionPane.INFORMATION_MESSAGE
            );
            
            vista.limpiarCampos();
            vista.cerrarVentana();
            loginView.mostrarVentana();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(
                vista.getFrame(),
                e.getMessage(),
                "Error en registro",
                JOptionPane.ERROR_MESSAGE
            );
        } finally {
            // Limpiar contraseñas de la memoria
            Arrays.fill(password, '\0');
            Arrays.fill(confirmPassword, '\0');
        }
    }

    public void volverAlLogin() {
        vista.limpiarCampos();
        vista.cerrarVentana();
        loginView.mostrarVentana();
    }
}