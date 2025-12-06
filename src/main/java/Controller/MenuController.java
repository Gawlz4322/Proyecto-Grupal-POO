package Controller;

import Model.FinanceSystem;
import View.MainMenu;

import javax.swing.*;
import java.util.List;

public class MenuController {
    private MainMenu view;
    private final FinanceSystem financeSystem;

    public MenuController(FinanceSystem financeSystem) {
        this.financeSystem = financeSystem;
    }

    public void setView(MainMenu view) {
        this.view = view;
    }

    // Maneja la adición de gastos con validación de monto y categoría
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

    // Maneja la adición de ingresos con validación de monto
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

    // Muestra el historial de transacciones en una tabla
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