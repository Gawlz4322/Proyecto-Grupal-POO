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
            currentData.getHistory().add("Gasto (" + category + "): -$" + amount);
            financeStore.save(currentData);
        }
    }

    // Registra un ingreso y actualiza el saldo
    public void addIncome(double amount) {
        if (currentData == null)
            throw new IllegalStateException("No user logged in");
        if (amount > 0) {
            currentData.setBalance(currentData.getBalance() + amount);
            currentData.getHistory().add("Ingreso: +$" + amount);
            financeStore.save(currentData);
        }
    }

    public double getBalance() {
        return currentData != null ? currentData.getBalance() : 0;
    }

    public List<String> getHistory() {
        return currentData != null ? currentData.getHistory() : Collections.emptyList();
    }
}
