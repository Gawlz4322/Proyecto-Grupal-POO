package Model;

import Controller.FinanceStore;
import java.util.Collections;
import java.util.List;

/**
 * Sistema principal de lógica de negocio para las finanzas personales.
 * Coordina las operaciones entre el usuario y el almacenamiento de datos.
 */
public class FinanceSystem {
    private FinanceData currentData;
    private final FinanceStore financeStore;

    /**
     * Constructor del sistema financiero.
     *
     * @param financeStore Almacén de datos financieros.
     */
    public FinanceSystem(FinanceStore financeStore) {
        this.financeStore = financeStore;
    }

    /**
     * Carga los datos financieros del usuario al iniciar sesión.
     *
     * @param user Usuario que inicia sesión.
     */
    public void login(User user) {
        this.currentData = financeStore.load(user.getUsername());
    }

    /**
     * Registra un gasto y actualiza el saldo del usuario actual.
     *
     * @param amount   Monto del gasto.
     * @param category Categoría del gasto.
     */
    public void addExpense(double amount, String category) {
        if (currentData == null)
            throw new IllegalStateException("No user logged in");
        if (amount > 0) {
            currentData.setBalance(currentData.getBalance() - amount);
            currentData.addTransaction(new Transaction("Gasto", amount, category, "Gasto en " + category));
            financeStore.save(currentData);
        }
    }

    /**
     * Registra un ingreso y actualiza el saldo del usuario actual.
     *
     * @param amount Monto del ingreso.
     */
    public void addIncome(double amount) {
        if (currentData == null)
            throw new IllegalStateException("No user logged in");
        if (amount > 0) {
            currentData.setBalance(currentData.getBalance() + amount);
            currentData.addTransaction(new Transaction("Ingreso", amount, "Ingreso", "Ingreso de dinero"));
            financeStore.save(currentData);
        }
    }

    /**
     * Obtiene el saldo actual del usuario logueado.
     *
     * @return Saldo actual, o 0 si no hay usuario.
     */
    public double getBalance() {
        return currentData != null ? currentData.getBalance() : 0;
    }

    /**
     * Obtiene el historial de transacciones del usuario.
     *
     * @return Lista de transacciones, o lista vacía si no hay usuario.
     */
    public List<Transaction> getHistory() {
        return currentData != null ? currentData.getTransactions() : Collections.emptyList();
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
