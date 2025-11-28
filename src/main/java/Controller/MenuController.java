package Controller;

import Model.FinanceSystem;
import View.MainMenu;

import javax.swing.*;
import java.util.List;

/**
 * Controlador para el menú principal.
 * Gestiona las acciones de añadir gastos, ingresos y ver el historial.
 */
public class MenuController {
    private MainMenu view;
    private final FinanceSystem financeSystem;

    /**
     * Inicializa el controlador del menú.
     *
     * @param financeSystem El sistema de finanzas.
     */
    public MenuController(FinanceSystem financeSystem) {
        this.financeSystem = financeSystem;
    }

    /**
     * Establece la vista asociada a este controlador.
     *
     * @param view La ventana del menú principal.
     */
    public void setView(MainMenu view) {
        this.view = view;
    }

    /**
     * Maneja la acción de añadir un nuevo gasto.
     * Solicita al usuario el monto y la categoría.
     */
    public void handleExpense() {
        String expenseString = view.requestInput("Monto del gasto:");
        if (expenseString != null && !expenseString.isEmpty()) {
            try {
                double newExpense = Double.parseDouble(expenseString);
                if (newExpense <= 0) {
                    JOptionPane.showMessageDialog(view.getFrame(), "El monto debe ser mayor a 0");
                    return;
                }

                String[] categories = { "Comida", "Transporte", "Entretenimiento", "Servicios", "Otros" };
                String category = view.requestOption("Seleccione una categoría:", categories);
                if (category == null)
                    return;

                financeSystem.addExpense(newExpense, category);
                updateBalanceView();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getFrame(), "Debe ingresar un número válido.");
            }
        }
    }

    /**
     * Maneja la acción de añadir un nuevo ingreso.
     * Solicita al usuario el monto.
     */
    public void handleIncome() {
        String incomeString = view.requestInput("Monto del ingreso:");
        if (incomeString != null && !incomeString.isEmpty()) {
            try {
                double newIncome = Double.parseDouble(incomeString);
                if (newIncome <= 0) {
                    JOptionPane.showMessageDialog(view.getFrame(), "El monto debe ser mayor a 0");
                    return;
                }
                financeSystem.addIncome(newIncome);
                updateBalanceView();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(view.getFrame(), "Debe ingresar un número válido.");
            }
        }
    }

    /**
     * Maneja la acción de ver el historial de movimientos.
     * Muestra una lista de transacciones en una tabla.
     */
    public void handleHistory() {
        List<String> history = financeSystem.getHistory();
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(view.getFrame(), "No hay transacciones registradas.");
        } else {
            // Usar la nueva ventana con tabla
            View.HistoryWindow historyWindow = new View.HistoryWindow();
            historyWindow.loadData(history);
            historyWindow.showWindow();
        }
    }

    /**
     * Actualiza la vista con el saldo actual.
     */
    public void updateBalanceView() {
        view.updateBalanceDisplay(financeSystem.getBalance());
    }
}