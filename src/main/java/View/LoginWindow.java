package View;

import Controller.LoginController;
import javax.swing.*;

/**
 * Ventana de inicio de sesión.
 * Permite a los usuarios ingresar sus credenciales o navegar al registro.
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
     * Inicializa la ventana de login.
     *
     * @param controller El controlador de login.
     */
    public LoginWindow(LoginController controller) {
        this.controller = controller;
        initLoginWindow();
        setupButtonListeners();
    }

    /**
     * Inicializa las propiedades básicas de la ventana de login.
     */
    private void initLoginWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null); // Centrar ventana
        initComponents();
    }

    /**
     * Inicializa y posiciona los componentes de la interfaz de usuario.
     */
    private void initComponents() {
        // Primero configurar posiciones
        lblUsuario.setBounds(50, 80, 300, 25);
        txtUsuario.setBounds(50, 120, 300, 25);
        lblClave.setBounds(50, 160, 300, 25);
        txtClave.setBounds(50, 200, 300, 25);
        btnIngresar.setBounds(50, 250, 300, 30);
        btnRegistrarse.setBounds(50, 290, 300, 30);

        // Luego añadir componentes al frame
        frame.getContentPane().add(lblUsuario);
        frame.getContentPane().add(txtUsuario);
        frame.getContentPane().add(lblClave);
        frame.getContentPane().add(txtClave);
        frame.getContentPane().add(btnIngresar);
        frame.getContentPane().add(btnRegistrarse);

        // Configurar botón por defecto
        frame.getRootPane().setDefaultButton(btnIngresar);

        // Asegurar que todos los componentes sean visibles
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
        frame.revalidate(); // Asegurar actualización del layout
        frame.repaint(); // Redibujar ventana
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

    /**
     * Obtiene el nombre de usuario ingresado en el campo de texto.
     *
     * @return El nombre de usuario.
     */
    public String getUsername() {
        return txtUsuario.getText();
    }

    /**
     * Obtiene la contraseña ingresada en el campo de contraseña.
     *
     * @return La contraseña.
     */
    public String getPassword() {
        return new String(txtClave.getPassword());
    }

    /**
     * Establece la referencia a la ventana de registro.
     *
     * @param registerWindow La ventana de registro.
     */
    public void setRegisterWindow(RegisterWindow registerWindow) {
        this.registerWindow = registerWindow;
    }

    /**
     * Configura los listeners para los botones de la ventana.
     */
    private void setupButtonListeners() {
        btnIngresar.addActionListener(e -> controller.attemptLogin());
        btnRegistrarse.addActionListener(e -> {
            frame.setVisible(false); // Ocultar ventana de login
            if (registerWindow != null) {
                registerWindow.showWindow(); // Mostrar ventana de registro
            }
        });
    }

    /**
     * Obtiene la instancia del JFrame principal de la ventana de login.
     *
     * @return El JFrame de la ventana de login.
     */
    public JFrame getFrame() {
        return frame;
    }
}