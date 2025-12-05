package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana para visualizar el historial de transacciones en una tabla.
 */
public class HistoryWindow {
    private final JFrame frame = new JFrame("Historial de Transacciones");
    private final JTable table;
    private final DefaultTableModel tableModel;

    public HistoryWindow() {
        // Configurar columnas: Fecha (Simulada/Orden), Tipo, Categoría, Monto
        String[] columnNames = { "Tipo", "Categoría", "Monto" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Hacer la tabla de solo lectura
            }
        };
        table = new JTable(tableModel);
        initWindow();
    }

    private void initWindow() {
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        // Añadir tabla en un ScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Botón de cerrar
        JButton btnClose = new JButton("Cerrar");
        btnClose.addActionListener(e -> frame.dispose());
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnClose);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Carga los datos del historial en la tabla.
     *
     * @param historyList Lista de transacciones.
     */
    public void loadData(List<Model.Transaction> historyList) {
        tableModel.setRowCount(0); // Limpiar tabla
        for (Model.Transaction t : historyList) {
            tableModel.addRow(new Object[] { t.getType(), t.getCategory(), String.format("$%.2f", t.getAmount()) });
        }
    }

    public void showWindow() {
        frame.setVisible(true);
    }
}
