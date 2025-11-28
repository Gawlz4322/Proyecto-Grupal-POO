package View;

import javax.swing.*;
import Controller.MenuController;
import java.awt.Component;

/**
 * Ventana del menú principal de la aplicación.
 * Muestra el saldo actual y ofrece opciones para gestionar gastos, ingresos e
 * historial.
 */
public class MainMenu {
    private final JFrame frame = new JFrame("Menú Principal - CFP");
    private final JLabel lblSaldo = new JLabel("Saldo actual: $0");
    private final JButton btnGasto = new JButton("Añadir gasto");
    private final JButton btnIngreso = new JButton("Añadir ingreso");
    private final JButton btnHistorial = new JButton("Ver historial");
    private final MenuController controller;

    /**
     * Inicializa la ventana del menú principal.
     *
     * @param controller El controlador del menú.
     */
    public MainMenu(MenuController controller) {
        this.controller = controller;
        this.controller.setView(this);
        initMainMenuWindow();
        initComponents();
        setupButtonListeners();
    }

    /**
     * Obtiene el marco principal de la ventana.
     *
     * @return El componente JFrame que representa la ventana principal.
     */
    public Component getFrame() {
        return frame;
    }

    /**
     * Inicializa la configuración básica de la ventana principal.
     */
    private void initMainMenuWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

    /**
     * Inicializa y posiciona los componentes de la interfaz de usuario en la
     * ventana.
     */
    private void initComponents() {
        frame.add(lblSaldo);
        frame.add(btnGasto);
        frame.add(btnIngreso);
        frame.add(btnHistorial);

        lblSaldo.setBounds(350, 40, 300, 25);
        btnGasto.setBounds(250, 100, 300, 25);
        btnIngreso.setBounds(250, 160, 300, 25);
        btnHistorial.setBounds(250, 220, 300, 25);
    }

    /**
     * Configura los escuchadores de eventos para los botones.
     */
    private void setupButtonListeners() {
        btnGasto.addActionListener(e -> controller.handleExpense());
        btnIngreso.addActionListener(e -> controller.handleIncome());
        btnHistorial.addActionListener(e -> controller.handleHistory());
    }

    /**
     * Actualiza la visualización del saldo en la interfaz.
     *
     * @param balance El nuevo saldo a mostrar.
     */
    public void updateBalanceDisplay(double balance) {
        lblSaldo.setText("Saldo actual: $" + balance);
    }

    /**
     * Muestra la ventana del menú principal.
     */
    public void showWindow() {
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    /**
     * Solicita una entrada de texto al usuario mediante un diálogo.
     *
     * @param message El mensaje a mostrar.
     * @return El texto ingresado por el usuario.
     */
    public String requestInput(String message) {
        return JOptionPane.showInputDialog(frame, message);
    }

    /**
     * Solicita al usuario seleccionar una opción de una lista.
     *
     * @param message El mensaje a mostrar.
     * @param options Las opciones disponibles.
     * @return La opción seleccionada.
     */
    public String requestOption(String message, String[] options) {
        return (String) JOptionPane.showInputDialog(
                frame,
                message,
                "Seleccionar",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);
    }
}