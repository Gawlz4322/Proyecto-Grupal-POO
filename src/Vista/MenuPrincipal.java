package Vista;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class MenuPrincipal {
    //menu principal, se inicializa después de login
    private final JFrame frame = new JFrame("Menú Principal - CFP");
    private double saldo = 0;
    private final List<String> historial = new ArrayList<>();
    private final JLabel lblSaldo = new JLabel("Saldo actual: $0");
    private final JButton btnGasto = new JButton("Añadir gasto");
    private final JButton btnIngreso = new JButton("Añadir ingreso");
    private final JButton btnHistorial = new JButton("Ver historial");

    public MenuPrincipal() {
        this(0.0);
    }

    public MenuPrincipal(double saldoInicial) {
        this.saldo = saldoInicial;
        iniciarVentanaMenuPrincipal();
        iniciarComponentes();
        redireccionadorBotones();
        actualizarDisplaySaldo();
        mostrarVentana();
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
    private void redireccionadorBotones(){
        btnGasto.addActionListener(actionEvent -> registrarGasto());
        btnIngreso.addActionListener(actionEvent -> registrarIngreso());
        btnHistorial.addActionListener(actionEvent -> mostrarHistorial());
    }

    private void registrarGasto() {
        String gasto = JOptionPane.showInputDialog(frame, "Monto del gasto:");
        if (gasto != null && !gasto.trim().isEmpty()) {
            try {
                double gastoNuevo = Double.parseDouble(gasto);
                if (gastoNuevo <= 0) {
                    JOptionPane.showMessageDialog(frame, "El gasto debe ser mayor a 0");
                    return;
                }
                saldo -= gastoNuevo;
                historial.add("Gasto: -$" + gastoNuevo);
                actualizarDisplaySaldo();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ingrese solo números");
            }
        }
    }

    private void registrarIngreso() {
        String ingreso = JOptionPane.showInputDialog(frame, "Monto del ingreso:");
        if (ingreso != null && !ingreso.trim().isEmpty()) {
            try {
                double ingresoNuevo = Double.parseDouble(ingreso);
                if (ingresoNuevo <= 0) {
                    JOptionPane.showMessageDialog(frame, "El ingreso debe ser mayor a 0");
                    return;
                }
                saldo += ingresoNuevo;
                historial.add("Ingreso: +$" + ingresoNuevo);
                actualizarDisplaySaldo();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Ingrese solo números");
            }
        }
    }

    private void mostrarHistorial() {
        if(historial.isEmpty()){
            JOptionPane.showMessageDialog(frame, "No hay movimientos");
        } else {
            JOptionPane.showMessageDialog(frame, String.join("\n", historial));
    }
    private void actualizarDisplaySaldo(){
        lblSaldo.setText("Saldo actual: $" + saldo);
    }
    private void mostrarVentana() {
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}
