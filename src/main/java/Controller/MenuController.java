package Controller;

import Model.FinanceSystem;
import View.MainMenu;

import javax.swing.*;
import java.util.List;

/**
 * Controlador para gestionar las interacciones en el menú principal.
 */
public class MenuController {
    private MainMenu view;
    private final FinanceSystem financeSystem;

    /**
     * Constructor del controlador del menú.
     *
     * @param financeSystem Sistema financiero subyacente.
     */
    public MenuController(FinanceSystem financeSystem) {
        this.financeSystem = financeSystem;
    }

    /**
     * Establece la vista asociada a este controlador.
     *
     * @param view Ventana del menú principal.
     */
    public void setView(MainMenu view) {
        this.view = view;
    }

    /**
     * Maneja la solicitud de agregar un nuevo gasto.
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
     * Maneja la solicitud de agregar un nuevo ingreso.
     * Solicita al usuario el monto del ingreso.
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
        view.updateBalanceDisplay(financeSystem.getBalance());
    }

    /**
     * Muestra el historial de transacciones en una nueva ventana.
     */
    public void handleHistory() {
        List<Model.Transaction> history = financeSystem.getHistory();
        if (history.isEmpty()) {
            JOptionPane.showMessageDialog(view.getFrame(), "No hay transacciones registradas.");
        } else {
            View.HistoryWindow historyWindow = new View.HistoryWindow();
            historyWindow.loadData(history);
            historyWindow.showWindow();
        }
    }

    /**
     * Actualiza la visualización del saldo en la vista principal.
     */
    public void updateBalanceView() {
        view.updateBalanceDisplay(financeSystem.getBalance());
    }

    /**
     * Obtiene los gastos por categoría para el gráfico.
     *
     * @return Mapa de Categoría -> Monto.
     */
    public java.util.Map<String, Double> getExpensesByCategory() {
        return financeSystem.getExpensesByCategory();
    }
}