package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HistoryWindow {
    private final JFrame frame = new JFrame("Historial de Transacciones");
    private final JTable table;
    private final DefaultTableModel tableModel;

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

<<<<<<< HEAD
    /**
     * Carga los datos del historial en la tabla.
     *
     * @param historyList Lista de transacciones.
     */
    public void loadData(List<Model.Transaction> historyList) {
        tableModel.setRowCount(0); // Limpiar tabla
        for (Model.Transaction t : historyList) {
            tableModel.addRow(new Object[] { t.getType(), t.getCategory(), String.format("$%.2f", t.getAmount()) });
=======
    public void loadData(List<String> historyList) {
        tableModel.setRowCount(0);
        for (String entry : historyList) {
            parseAndAddRow(entry);
        }
    }

    // Parsea strings de transacciones al formato de tabla
    // Formato esperado: "Gasto (Categoria): -$monto" o "Ingreso: +$monto"
    private void parseAndAddRow(String entry) {
        String type = "";
        String category = "-";
        String amount = "";

        try {
            if (entry.startsWith("Gasto")) {
                type = "Gasto";
                int catStart = entry.indexOf("(") + 1;
                int catEnd = entry.indexOf(")");
                if (catStart > 0 && catEnd > catStart) {
                    category = entry.substring(catStart, catEnd);
                }
                int amountStart = entry.indexOf("$");
                if (amountStart != -1) {
                    amount = entry.substring(amountStart);
                }
            } else if (entry.startsWith("Ingreso")) {
                type = "Ingreso";
                int amountStart = entry.indexOf("$");
                if (amountStart != -1) {
                    amount = entry.substring(amountStart);
                }
            } else {
                type = "Otro";
                category = entry;
            }
            tableModel.addRow(new Object[] { type, category, amount });
        } catch (Exception e) {
            // Si falla el parsing, mostrar entrada completa
            tableModel.addRow(new Object[] { entry, "-", "-" });
>>>>>>> 6d9acecfaddd34d9bba5d6a8d5965f2958148f2d
        }
    }

    public void showWindow() {
        frame.setVisible(true);
    }
}
