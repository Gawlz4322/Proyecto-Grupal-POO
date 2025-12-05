package View;

import Controller.RegisterController;

import javax.swing.*;

public class RegisterWindow {
    private final JFrame frame = new JFrame("Registro - Control de Finanzas Personales");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Contraseña:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JLabel lblConfirmarClave = new JLabel("Confirmar contraseña:");
    private final JPasswordField txtConfirmarClave = new JPasswordField();
    private final JButton btnRegistrar = new JButton("Registrarse");
    private final JButton btnVolver = new JButton("Volver al login");
    private final RegisterController controller;

    public RegisterWindow(RegisterController controller) {
        this.controller = controller;
        this.controller.setView(this);
        initRegisterWindow();
        setupButtonListeners();
    }

    private void initRegisterWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 450);
        frame.setLayout(null);
        initComponents();
    }

    private void initComponents() {
        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(lblConfirmarClave);
        frame.add(txtConfirmarClave);
        frame.add(btnRegistrar);
        frame.add(btnVolver);

        lblUsuario.setBounds(50, 60, 300, 25);
        txtUsuario.setBounds(50, 90, 300, 25);

        lblClave.setBounds(50, 130, 300, 25);
        txtClave.setBounds(50, 160, 300, 25);

        lblConfirmarClave.setBounds(50, 200, 300, 25);
        txtConfirmarClave.setBounds(50, 230, 300, 25);

        btnRegistrar.setBounds(50, 280, 300, 30);
        btnVolver.setBounds(50, 320, 300, 30);

        frame.getRootPane().setDefaultButton(btnRegistrar);
    }

    public void showWindow() {
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void closeWindow() {
        frame.dispose();
    }

    public void clearFields() {
        txtUsuario.setText("");
        txtClave.setText("");
        txtConfirmarClave.setText("");
    }

    public JFrame getFrame() {
        return frame;
    }

    public String getUsername() {
        return txtUsuario.getText();
    }

    public char[] getPassword() {
        return txtClave.getPassword();
    }

    public char[] getConfirmPassword() {
        return txtConfirmarClave.getPassword();
    }

    private void setupButtonListeners() {
        btnRegistrar.addActionListener(e -> controller.attemptRegister());
        btnVolver.addActionListener(e -> controller.returnToLogin());
    }
}