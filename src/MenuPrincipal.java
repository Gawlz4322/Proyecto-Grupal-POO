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
        lblSaldo.setBounds(50, 40, 300, 25);
        btnGasto.setBounds(50, 80, 300, 25);
        btnIngreso.setBounds(50, 120, 300, 25);
        btnHistorial.setBounds(50, 160, 300, 25);
    }
}
