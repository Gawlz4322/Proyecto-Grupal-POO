package Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que almacena los datos financieros de un usuario individual.
 * Contiene el saldo y el historial de transacciones.
 */
public class FinanceData {
    private String userId;

    @SerializedName("saldo")
    private double balance;

    @SerializedName("transacciones")
    private List<Transaction> transactions;

    /**
     * Constructor para inicializar datos financieros de un usuario.
     *
     * @param userId Identificador del usuario.
     */
    public FinanceData(String userId) {
        this.userId = userId;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    /**
     * Obtiene el ID del usuario.
     * 
     * @return ID del usuario.
     */
    public String getUserId() {
        return userId;
    }

    /**
     * Obtiene el saldo actual.
     * 
     * @return Saldo actual.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Establece el saldo actual.
     * 
     * @param balance Nuevo saldo.
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * Obtiene la lista de transacciones.
     * 
     * @return Lista de transacciones.
     */
    public List<Transaction> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    /**
     * Establece la lista de transacciones.
     * 
     * @param transactions Nueva lista de transacciones.
     */
    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    /**
     * Agrega una nueva transacción a la lista.
     * 
     * @param transaction Transacción a agregar.
     */
    public void addTransaction(Transaction transaction) {
        if (this.transactions == null) {
            this.transactions = new ArrayList<>();
        }
        this.transactions.add(transaction);
    }
}
