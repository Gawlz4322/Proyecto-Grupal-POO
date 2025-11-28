package Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Contiene los datos financieros de un usuario específico.
 * Incluye el saldo actual y el historial de transacciones.
 */
public class FinanceData {
    private String userId;

    @SerializedName("saldo")
    private double balance;

    @SerializedName("historial")
    private List<String> history;

    /**
     * Crea una nueva instancia de datos financieros para un usuario.
     * Inicializa el saldo en 0 y el historial vacío.
     *
     * @param userId El ID del usuario asociado.
     */
    public FinanceData(String userId) {
        this.userId = userId;
        this.balance = 0.0;
        this.history = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public List<String> getHistory() {
        return history;
    }

    public void setHistory(List<String> history) {
        this.history = history;
    }
}
