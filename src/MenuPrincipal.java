import javax.swing.*;
import java.awt.*;
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
        //aun no se crean los métodos.
        iniciarVentanaMenuPrincipal();
        iniciarComponentes();
        redireccionadorBotones();
    }

    private void iniciarVentanaMenuPrincipal() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
        frame.setVisible(true);
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
        btnGasto.addActionListener(e -> {
            String gasto = JOptionPane.showInputDialog(frame, "Monto del gasto:");
            if (gasto != null && !gasto.isEmpty()) {
                try {
                    double g = Double.parseDouble(gasto);
                    saldo -= g;
                    historial.add("Gasto: -$" + g);
                    //actualizar saldo, metodo aun no creado
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Debe ingresar un número válido", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MenuPrincipal::new);
    }
}
