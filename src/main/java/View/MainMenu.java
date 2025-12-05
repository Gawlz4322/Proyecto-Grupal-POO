package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import Controller.MenuController;
import java.awt.*;

/**
 * Ventana del menú principal de la aplicación.
 * Diseño moderno y profesional utilizando FlatLaf, Cards y SVG Icons.
 */
public class MainMenu {
    private final JFrame frame = new JFrame("Control de Finanzas Personales");
    private final JLabel lblSaldo = new JLabel("$0");
    private final MenuController controller;
    private JPanel chartPanelContainer;

    // Colores del tema (Nord Theme)
    private final Color ACCENT_COLOR = new Color(136, 192, 208); // Azul hielo
    private final Color TEXT_PRIMARY = new Color(236, 239, 244); // Blanco nieve
    private final Color TEXT_SECONDARY = new Color(216, 222, 233); // Gris claro

    public MainMenu(MenuController controller) {
        this.controller = controller;
        this.controller.setView(this);
        initMainMenuWindow();
        initComponents();
    }

    public Component getFrame() {
        return frame;
    }

    private void initMainMenuWindow() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 750);
        frame.setLayout(new BorderLayout(20, 20)); // Gaps externos
        frame.getContentPane().setBackground(new Color(46, 52, 64)); // Fondo base oscuro
    }

    private void initComponents() {
        // --- HEADER CARD ---
        CardPanel headerCard = new CardPanel();
        headerCard.setLayout(new BorderLayout());

        JLabel lblTitle = new JLabel("Resumen Financiero");
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblTitle.setForeground(TEXT_SECONDARY);

        lblSaldo.setFont(new Font("Segoe UI", Font.BOLD, 48));
        lblSaldo.setForeground(ACCENT_COLOR);

        JPanel titleContainer = new JPanel(new GridLayout(2, 1));
        titleContainer.setOpaque(false);
        titleContainer.add(lblTitle);
        titleContainer.add(lblSaldo);

        headerCard.add(titleContainer, BorderLayout.WEST);

        // Añadir padding externo al header
        JPanel headerWrapper = new JPanel(new BorderLayout());
        headerWrapper.setOpaque(false);
        headerWrapper.setBorder(new EmptyBorder(20, 20, 0, 20));
        headerWrapper.add(headerCard, BorderLayout.CENTER);

        frame.add(headerWrapper, BorderLayout.NORTH);

        // --- CENTER CARD (CHART) ---
        CardPanel chartCard = new CardPanel();
        chartCard.setLayout(new BorderLayout());

        chartPanelContainer = new JPanel(new BorderLayout());
        chartPanelContainer.setOpaque(false);

        JLabel lblPlaceholder = new JLabel("Cargando gráfico...", SwingConstants.CENTER);
        lblPlaceholder.setForeground(TEXT_SECONDARY);
        chartPanelContainer.add(lblPlaceholder, BorderLayout.CENTER);

        chartCard.add(chartPanelContainer, BorderLayout.CENTER);

        JPanel chartWrapper = new JPanel(new BorderLayout());
        chartWrapper.setOpaque(false);
        chartWrapper.setBorder(new EmptyBorder(0, 20, 20, 0)); // Padding
        chartWrapper.add(chartCard, BorderLayout.CENTER);

        frame.add(chartWrapper, BorderLayout.CENTER);

        // --- SIDEBAR (ACTIONS) ---
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.setOpaque(false);
        sidePanel.setBorder(new EmptyBorder(0, 0, 20, 20)); // Padding derecho e inferior
        sidePanel.setPreferredSize(new Dimension(240, 0));

        // Contenedor de botones (Card también para el menú?)
        // Mejor botones flotantes o en un panel transparente
        JPanel buttonContainer = new JPanel(new GridLayout(0, 1, 0, 15));
        buttonContainer.setOpaque(false);

        // Cargar iconos SVG
        FlatSVGIcon iconIncome = new FlatSVGIcon("icons/income.svg", 20, 20);
        FlatSVGIcon iconExpense = new FlatSVGIcon("icons/expense.svg", 20, 20);
        FlatSVGIcon iconHistory = new FlatSVGIcon("icons/history.svg", 20, 20);
        FlatSVGIcon iconLogout = new FlatSVGIcon("icons/logout.svg", 20, 20);

        JButton btnIngreso = createStyledButton("Registrar Ingreso", iconIncome);
        JButton btnGasto = createStyledButton("Registrar Gasto", iconExpense);
        JButton btnHistorial = createStyledButton("Ver Historial", iconHistory);
        JButton btnSalir = createStyledButton("Cerrar Sesión", iconLogout);

        btnIngreso.addActionListener(e -> controller.handleIncome());
        btnGasto.addActionListener(e -> controller.handleExpense());
        btnHistorial.addActionListener(e -> controller.handleHistory());
        btnSalir.addActionListener(e -> System.exit(0));

        buttonContainer.add(btnIngreso);
        buttonContainer.add(btnGasto);
        buttonContainer.add(btnHistorial);
        buttonContainer.add(Box.createVerticalStrut(20)); // Espacio
        buttonContainer.add(btnSalir);

        sidePanel.add(buttonContainer, BorderLayout.NORTH);

        frame.add(sidePanel, BorderLayout.EAST);
    }

    private JButton createStyledButton(String text, Icon icon) {
        JButton btn = new JButton(text);
        btn.setIcon(icon);
        btn.setIconTextGap(15);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(0, 45));
        // FlatLaf maneja el estilo, pero podemos forzar un poco
        return btn;
    }

    public void updateBalanceDisplay(double balance) {
        // Formato para pesos chilenos (sin decimales)
        lblSaldo.setText("$" + String.format("%,.0f", balance));
        updateChart();
    }

    private void updateChart() {
        chartPanelContainer.removeAll();

        org.jfree.data.general.DefaultPieDataset<String> dataset = new org.jfree.data.general.DefaultPieDataset<>();
        java.util.Map<String, Double> expenses = controller.getExpensesByCategory();

        if (expenses.isEmpty()) {
            JLabel noData = new JLabel("No hay gastos registrados aún", SwingConstants.CENTER);
            noData.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            noData.setForeground(Color.GRAY);
            chartPanelContainer.add(noData, BorderLayout.CENTER);
        } else {
            for (java.util.Map.Entry<String, Double> entry : expenses.entrySet()) {
                dataset.setValue(entry.getKey(), entry.getValue());
            }

            org.jfree.chart.JFreeChart chart = org.jfree.chart.ChartFactory.createPieChart(
                    null,
                    dataset,
                    true,
                    true,
                    false);

            org.jfree.chart.plot.PiePlot plot = (org.jfree.chart.plot.PiePlot) chart.getPlot();
            plot.setBackgroundPaint(null);
            plot.setOutlineVisible(false);
            plot.setLabelGenerator(null);
            plot.setShadowPaint(null);

            chart.setBackgroundPaint(null);
            chart.getLegend().setBackgroundPaint(null);
            chart.getLegend().setItemPaint(TEXT_SECONDARY);
            chart.setBorderVisible(false);

            org.jfree.chart.ChartPanel chartPanel = new org.jfree.chart.ChartPanel(chart);
            chartPanel.setOpaque(false);

            chartPanelContainer.add(chartPanel, BorderLayout.CENTER);
        }

        chartPanelContainer.revalidate();
        chartPanelContainer.repaint();
    }

    public void showWindow() {
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        updateChart();
    }

    public String requestInput(String message) {
        return JOptionPane.showInputDialog(frame, message);
    }

    public String requestOption(String message, String[] options) {
        return (String) JOptionPane.showInputDialog(frame, message, "Seleccionar", JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
    }
}