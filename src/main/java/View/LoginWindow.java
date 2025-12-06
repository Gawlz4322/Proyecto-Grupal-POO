package View;

import Controller.LoginController;
import javax.swing.*;

/**
 * Ventana de inicio de sesión de la aplicación.
 */
public class LoginWindow {
    private final JFrame frame = new JFrame("Control de Finanzas Personales (CFP)");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrarse = new JButton("¿No tienes cuenta? Regístrate");
    private final LoginController controller;
    private RegisterWindow registerWindow;

    /**
     * Constructor de la ventana de login.
     *
     * @param controller Controlador de login asociado.
     */
    public LoginWindow(LoginController controller) {
        this.controller = controller;
        initLoginWindow();
        setupButtonListeners();
    }

    private void initLoginWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        lblUsuario.setBounds(50, 80, 300, 25);
        txtUsuario.setBounds(50, 120, 300, 25);
        lblClave.setBounds(50, 160, 300, 25);
        txtClave.setBounds(50, 200, 300, 25);
        btnIngresar.setBounds(50, 250, 300, 30);
        btnRegistrarse.setBounds(50, 290, 300, 30);

        frame.getContentPane().add(lblUsuario);
        frame.getContentPane().add(txtUsuario);
        frame.getContentPane().add(lblClave);
        frame.getContentPane().add(txtClave);
        frame.getContentPane().add(btnIngresar);
        frame.getContentPane().add(btnRegistrarse);

        frame.getRootPane().setDefaultButton(btnIngresar);

        lblUsuario.setVisible(true);
        txtUsuario.setVisible(true);
        lblClave.setVisible(true);
        txtClave.setVisible(true);
        btnIngresar.setVisible(true);
        btnRegistrarse.setVisible(true);
    }

    /**
     * Muestra la ventana de login.
     */
    public void showWindow() {
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    /**
     * Cierra la ventana de login.
     */
    public void closeWindow() {
        frame.dispose();
    }

    /**
     * Limpia el campo de contraseña.
     */
    public void clearPassword() {
        txtClave.setText("");
    }

    public String getUsername() {
        return txtUsuario.getText();
    }

    public String getPassword() {
        return new String(txtClave.getPassword());
    }

    /**
     * Establece la ventana de registro asociada para navegación.
     *
     * @param registerWindow Instancia de la ventana de registro.
     */
    public void setRegisterWindow(RegisterWindow registerWindow) {
        this.registerWindow = registerWindow;
    }

    private void setupButtonListeners() {
        btnIngresar.addActionListener(e -> controller.attemptLogin());
        btnRegistrarse.addActionListener(e -> {
            frame.setVisible(false);
            if (registerWindow != null) {
                registerWindow.showWindow();
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}