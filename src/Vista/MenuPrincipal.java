package Vista;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;


public class MenuPrincipal {
    //menu principal, se inicializa después de login
    private final JFrame frame = new JFrame("Menú Principal - CFP");
    private final JLabel lblSaldo = new JLabel("Saldo actual: $0");
    private final JButton btnGasto = new JButton("Añadir gasto");
    private final JButton btnIngreso = new JButton("Añadir ingreso");
    private final JButton btnHistorial = new JButton("Ver historial");

    public MenuPrincipal() {
        iniciarVentanaMenuPrincipal();
        iniciarComponentes();
        redireccionadorBotones();
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
       //(se mueven añadirGasto y añadirIngreso a sistema finanzas. Hay que rehacer esto
        btnHistorial.addActionListener(e -> {
            if(historial.isEmpty()){
                JOptionPane.showMessageDialog(frame, "No hay movimientos registrados","Historial",JOptionPane.INFORMATION_MESSAGE);
            }else{
                JOptionPane.showMessageDialog(frame, String.join("\n", historial), "Historial", JOptionPane.INFORMATION_MESSAGE);
            }
        });
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
    public String pedirInput(String mensaje) {
        return JOptionPane.showInputDialog(frame, mensaje);
    }
}
