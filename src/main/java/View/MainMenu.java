package View;

import javax.swing.*;
import Controller.MenuController;
import java.awt.Component;

public class MainMenu {
    private final JFrame frame = new JFrame("Menú Principal - CFP");
    private final JLabel lblSaldo = new JLabel("Saldo actual: $0");
    private final JButton btnGasto = new JButton("Añadir gasto");
    private final JButton btnIngreso = new JButton("Añadir ingreso");
    private final JButton btnHistorial = new JButton("Ver historial");
    private final MenuController controller;

    public MainMenu(MenuController controller) {
        this.controller = controller;
        this.controller.setView(this);
        initMainMenuWindow();
        initComponents();
        setupButtonListeners();
    }

    public Component getFrame() {
        return frame;
    }

    private void initMainMenuWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }

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

    private void setupButtonListeners() {
        btnGasto.addActionListener(e -> controller.handleExpense());
        btnIngreso.addActionListener(e -> controller.handleIncome());
        btnHistorial.addActionListener(e -> controller.handleHistory());
    }

    public void updateBalanceDisplay(double balance) {
        lblSaldo.setText("Saldo actual: $" + balance);
    }

    public void showWindow() {
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public String requestInput(String message) {
        return JOptionPane.showInputDialog(frame, message);
    }

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