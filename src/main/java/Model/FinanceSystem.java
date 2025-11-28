package Model;

import Controller.FinanceStore;
import java.util.List;

/**
 * Sistema principal que gestiona la lógica de negocio de las finanzas.
 * Coordina la interacción entre los datos financieros y el almacenamiento.
 */
public class FinanceSystem {
    private FinanceData currentData;
    private final FinanceStore financeStore;

    /**
     * Inicializa el sistema de finanzas con un almacenamiento específico.
     *
     * @param financeStore El almacenamiento de datos financieros.
     */
    public FinanceSystem(FinanceStore financeStore) {
        this.financeStore = financeStore;
    }

    /**
     * Inicia sesión para un usuario cargando sus datos financieros.
     *
     * @param user El usuario que inicia sesión.
     */
    public void login(User user) {
        this.currentData = financeStore.load(user.getUsername());
    }

    /**
     * Agrega un gasto al saldo del usuario actual y registra la transacción.
     *
     * @param amount   El monto del gasto.
     * @param category La categoría del gasto.
     * @throws IllegalStateException Si no hay un usuario con sesión iniciada.
     */
    public void addExpense(double amount, String category) {
        if (currentData == null)
            throw new IllegalStateException("No user logged in");
        if (amount > 0) {
            currentData.setBalance(currentData.getBalance() - amount);
            currentData.getHistory().add("Gasto (" + category + "): -$" + amount);
            financeStore.save(currentData);
        }
    }

    /**
     * Agrega un ingreso al saldo del usuario actual y registra la transacción.
     *
     * @param amount El monto del ingreso.
     * @throws IllegalStateException Si no hay un usuario con sesión iniciada.
     */
    public void addIncome(double amount) {
        if (currentData == null)
            throw new IllegalStateException("No user logged in");
        if (amount > 0) {
            currentData.setBalance(currentData.getBalance() + amount);
            currentData.getHistory().add("Ingreso: +$" + amount);
            financeStore.save(currentData);
        }
    }

    /**
     * Obtiene el saldo actual del usuario.
     *
     * @return El saldo actual, o 0 si no hay usuario.
     */
    public double getBalance() {
        return currentData != null ? currentData.getBalance() : 0;
    }

    /**
     * Obtiene el historial de transacciones del usuario.
     *
     * @return Lista de transacciones, o lista vacía si no hay usuario.
     */
    public List<String> getHistory() {
        return currentData != null ? currentData.getHistory() : List.of();
    }
}
