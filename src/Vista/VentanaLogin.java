package Vista;
import Modelo.SistemaFinanzas;
import Modelo.Usuario;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
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
    public void login(){
        String u = txtUsuario.getText();
        String p = new String(txtClave.getPassword());
        String nombre = validarCredenciales(u, p);
        if (!nombre.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Bienvenido "+ nombre);
            double saldoInicial = 0.0;
            
            //preguntas iniciales antes de mostrar el menú
            int gastos = JOptionPane.showConfirmDialog(frame, "¿Has realizado gastos?", "Gastos", JOptionPane.YES_NO_OPTION);
            if (gastos == JOptionPane.YES_OPTION) {
                String montoGastos = JOptionPane.showInputDialog(frame, "Ingrese el monto de gastos:");
                if (montoGastos != null && !montoGastos.trim().isEmpty()) {
                    try {
                        double gastoInicial = Double.parseDouble(montoGastos);
                        if (gastoInicial > 0) {
                            saldoInicial -= gastoInicial;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Monto de gasto inválido, se ignorará", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }

            int ingresos = JOptionPane.showConfirmDialog(frame, "¿Has recibido ingresos?", "Ingresos", JOptionPane.YES_NO_OPTION);
            if (ingresos == JOptionPane.YES_OPTION) {
                String montoIngresos = JOptionPane.showInputDialog(frame, "Ingrese el monto de ingresos:");
                if (montoIngresos != null && !montoIngresos.trim().isEmpty()) {
                    try {
                        double ingresoInicial = Double.parseDouble(montoIngresos);
                        if (ingresoInicial > 0) {
                            saldoInicial += ingresoInicial;
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Monto de ingreso inválido, se ignorará", "Advertencia", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
            
            frame.dispose();
            new MenuPrincipal(saldoInicial);
        } else{
            JOptionPane.showMessageDialog(frame, "Modelo.Usuario o clave incorrecta", "Error", JOptionPane.ERROR_MESSAGE);
            txtUsuario.setText("");
            txtUsuario.requestFocus();
            txtClave.setText("");
        }
    }

    public void redireccionadorBotones(){
        btnIngresar.addActionListener(__ -> login());
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaLogin::new);
    }
}
