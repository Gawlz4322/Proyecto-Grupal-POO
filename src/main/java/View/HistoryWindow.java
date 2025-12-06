package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Ventana que muestra la tabla con el historial de todas las transacciones.
 */
public class HistoryWindow {
    private final JFrame frame = new JFrame("Historial de Transacciones");
    private final JTable table;
    private final DefaultTableModel tableModel;

    /**
     * Constructor de la ventana de historial.
     */
    public HistoryWindow() {
        String[] columnNames = { "Tipo", "Categoría", "Monto" };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        initWindow();
    }

    private void initWindow() {
        frame.setSize(500, 400);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);

        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

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

    /**
     * Muestra la ventana de historial.
     */
    public void showWindow() {
        frame.setVisible(true);
    }
}
