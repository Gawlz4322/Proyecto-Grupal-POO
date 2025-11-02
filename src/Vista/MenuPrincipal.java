package Vista;

import Modelo.SistemaFinanzas;
import javax.swing.*;
import Controlador.MenuController;
import java.awt.Component;

public class MenuPrincipal {
    private final JFrame frame = new JFrame("Menú Principal - CFP");
    private final JLabel lblSaldo = new JLabel("Saldo actual: $0");
    private final JButton btnGasto = new JButton("Añadir gasto");
    private final JButton btnIngreso = new JButton("Añadir ingreso");
    private final JButton btnHistorial = new JButton("Ver historial");
    private final MenuController controller;

    public MenuPrincipal(MenuController controller) {
        this.controller = controller;
        this.controller.setVista(this);
        iniciarVentanaMenuPrincipal();
        iniciarComponentes();
        redireccionadorBotones();
        mostrarVentana();
    }
    public Component getFrame(){
        return frame;
    }

    private void iniciarVentanaMenuPrincipal() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    private void iniciarComponentes() {
        frame.add(lblSaldo);
        frame.add(btnGasto);
        frame.add(btnIngreso);
        frame.add(btnHistorial);
        lblSaldo.setBounds(350, 40, 300, 25);
        btnGasto.setBounds(250, 100, 300, 25);
        btnIngreso.setBounds(250, 160, 300, 25);
        btnHistorial.setBounds(250, 220, 300, 25);
    }

    private void redireccionadorBotones() {
        btnGasto.addActionListener(e -> controller.manejarGasto());
        btnIngreso.addActionListener(e -> controller.manejarIngreso());
        btnHistorial.addActionListener(e -> {
            var historial = controller.getModeloFinanzas.getHistorial(); // ✅ Se obtiene desde el sistema
            if (historial.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "No hay movimientos registrados", "Historial", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(frame, String.join("\n", historial), "Historial", JOptionPane.INFORMATION_MESSAGE);
            }
        });
    }

    public void actualizarDisplaySaldo(double saldo) {
        lblSaldo.setText("Saldo actual: $" + saldo);
    }

    public void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public String pedirInput(String mensaje) {
        return JOptionPane.showInputDialog(frame, mensaje);
    }
}
