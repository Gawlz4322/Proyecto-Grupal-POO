package Model;

import Controller.FinanceStore;
import java.util.Collections;
import java.util.List;

public class FinanceSystem {
    private FinanceData currentData;
    private final FinanceStore financeStore;

    public FinanceSystem(FinanceStore financeStore) {
        this.financeStore = financeStore;
    }

    // Carga los datos financieros del usuario al iniciar sesión
    public void login(User user) {
        this.currentData = financeStore.load(user.getUsername());
    }

    // Registra un gasto y actualiza el saldo
    public void addExpense(double amount, String category) {
        if (currentData == null)
            throw new IllegalStateException("No user logged in");
        if (amount > 0) {
            currentData.setBalance(currentData.getBalance() - amount);
            currentData.addTransaction(new Transaction("Gasto", amount, category, "Gasto en " + category));
            financeStore.save(currentData);
        }
    }

    // Registra un ingreso y actualiza el saldo
    public void addIncome(double amount) {
        if (currentData == null)
            throw new IllegalStateException("No user logged in");
        if (amount > 0) {
            currentData.setBalance(currentData.getBalance() + amount);
            currentData.addTransaction(new Transaction("Ingreso", amount, "Ingreso", "Ingreso de dinero"));
            financeStore.save(currentData);
        }
    }

    public double getBalance() {
        return currentData != null ? currentData.getBalance() : 0;
    }

    /**
     * Obtiene el historial de transacciones del usuario.
     *
     * @return Lista de transacciones, o lista vacía si no hay usuario.
     */
    public List<Transaction> getHistory() {
        return currentData != null ? currentData.getTransactions() : List.of();
    }

    /**
     * Obtiene un mapa con el total de gastos por categoría.
     *
     * @return Mapa de Categoría -> Total Gasto.
     */
    public java.util.Map<String, Double> getExpensesByCategory() {
        java.util.Map<String, Double> expenses = new java.util.HashMap<>();
        if (currentData != null) {
            for (Transaction t : currentData.getTransactions()) {
                if ("Gasto".equals(t.getType())) {
                    expenses.put(t.getCategory(), expenses.getOrDefault(t.getCategory(), 0.0) + t.getAmount());
                }
            }
        }
        return expenses;
    }
}
