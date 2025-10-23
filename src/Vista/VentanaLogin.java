package Vista;

import javax.swing.*;

// TODO: register. Solo soporta login por ahora
public class VentanaLogin {
    private final JFrame frame = new JFrame("Control de Finanzas Personales (CFP)");
    private final JLabel lblUsuario = new JLabel("Modelo.Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");

    public VentanaLogin() {
        iniciarVentanaLogin();
        redireccionadorBotones();
    }

    private void iniciarVentanaLogin(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        //cambiar a new GridLayout(x, y, z, w). Estetica, dejar para el final
        frame.setLayout(null);
        inicializarBotones();
        mostrarVentana();
    }
    public void mostrarVentana(){
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    private void inicializarBotones(){
        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(btnIngresar);

        lblUsuario.setBounds(50, 80,300,25);
        txtUsuario.setBounds(50, 120,300,25);
        lblClave.setBounds(50, 160,300,25);
        txtClave.setBounds(50, 200,300,25);
        btnIngresar.setBounds(50, 240,300,25);

        frame.getRootPane().setDefaultButton(btnIngresar);
    }
    public boolean preguntar(){
        int respuesta = JOptionPane.showConfirmDialog(frame, pregunta, titulo, JOptionPane.YES_NO_OPTION);
        return respuesta == JOptionPane.YES_OPTION;
    }

    public String getUsuario(){
        return txtUsuario.getText();
    }
    public String getClave(){
        return new String(txtClave.getText());
    }

    public void redireccionadorBotones(){
        btnIngresar.addActionListener(e -> login());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaLogin::new);
    }
}
