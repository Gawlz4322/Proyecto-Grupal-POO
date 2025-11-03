package Vista;

import Controlador.LoginController;
import javax.swing.*;

public class VentanaLogin {
    private final JFrame frame = new JFrame("Control de Finanzas Personales (CFP)");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");
    private final JButton btnRegistrarse = new JButton("¿No tienes cuenta? Regístrate");
    private final LoginController controller;
    private VentanaRegistro ventanaRegistro;

    public VentanaLogin(LoginController controller) {
        this.controller = controller;
        iniciarVentanaLogin();
        redireccionadorBotones();
    }

    private void iniciarVentanaLogin() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(null);
        frame.setLocationRelativeTo(null);  // Centrar la ventana
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        // Primero configurar las posiciones
        lblUsuario.setBounds(50, 80, 300, 25);
        txtUsuario.setBounds(50, 120, 300, 25);
        lblClave.setBounds(50, 160, 300, 25);
        txtClave.setBounds(50, 200, 300, 25);
        btnIngresar.setBounds(50, 250, 300, 30);
        btnRegistrarse.setBounds(50, 290, 300, 30);

        // Luego agregar los componentes al frame
        frame.getContentPane().add(lblUsuario);
        frame.getContentPane().add(txtUsuario);
        frame.getContentPane().add(lblClave);
        frame.getContentPane().add(txtClave);
        frame.getContentPane().add(btnIngresar);
        frame.getContentPane().add(btnRegistrarse);

        // Configurar el botón por defecto
        frame.getRootPane().setDefaultButton(btnIngresar);
        
        // Asegurarse de que todos los componentes sean visibles
        lblUsuario.setVisible(true);
        txtUsuario.setVisible(true);
        lblClave.setVisible(true);
        txtClave.setVisible(true);
        btnIngresar.setVisible(true);
        btnRegistrarse.setVisible(true);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.revalidate();  // Asegurarse de que el layout se actualice
        frame.repaint();     // Redibujar la ventana
        frame.setVisible(true);
    }

    public void cerrarVentana() {
        frame.dispose();
    }

    public void limpiarClave() {
        txtClave.setText("");
    }

    public String getUsuario() {
        return txtUsuario.getText();
    }

    public String getClave() {
        return new String(txtClave.getPassword());
    }

    public void setVentanaRegistro(VentanaRegistro ventanaRegistro) {
        this.ventanaRegistro = ventanaRegistro;
    }

    private void redireccionadorBotones() {
        btnIngresar.addActionListener(e -> controller.intentarLogin());
        btnRegistrarse.addActionListener(e -> {
            frame.setVisible(false);  // Ocultar ventana de login
            if (ventanaRegistro != null) {
                ventanaRegistro.mostrarVentana();  // Mostrar ventana de registro
            }
        });
    }

    public JFrame getFrame() {
        return frame;
    }
}