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
     * Parsea las cadenas de texto para extraer tipo, categoría y monto.
     *
     * @param historyList Lista de cadenas del historial.
     */
    public void loadData(List<String> historyList) {
        tableModel.setRowCount(0); // Limpiar tabla
        for (String entry : historyList) {
            parseAndAddRow(entry);
        }
    }

    private void parseAndAddRow(String entry) {
        String type = "";
        String category = "-";
        String amount = "";

        try {
            if (entry.startsWith("Gasto")) {
                type = "Gasto";
                // Formato: "Gasto (Categoria): -$Monto"
                int catStart = entry.indexOf("(") + 1;
                int catEnd = entry.indexOf(")");
                if (catStart > 0 && catEnd > catStart) {
                    category = entry.substring(catStart, catEnd);
                }
                int amountStart = entry.indexOf("$");
                if (amountStart != -1) {
                    amount = entry.substring(amountStart); // Incluye el $
                }
            } else if (entry.startsWith("Ingreso")) {
                type = "Ingreso";
                // Formato: "Ingreso: +$Monto"
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
            // Si falla el parsing, mostrar la línea completa en la primera columna
            tableModel.addRow(new Object[] { entry, "-", "-" });
        }
    }

    public void showWindow() {
        frame.setVisible(true);
    }
}
