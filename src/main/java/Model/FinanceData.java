package Model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class FinanceData {
    private String userId;

    @SerializedName("saldo")
    private double balance;

    @SerializedName("transacciones")
    private List<Transaction> transactions;

    public FinanceData(String userId) {
        this.userId = userId;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
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

    public List<Transaction> getTransactions() {
        if (transactions == null) {
            transactions = new ArrayList<>();
        }
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        if (this.transactions == null) {
            this.transactions = new ArrayList<>();
        }
        this.transactions.add(transaction);
    }
}
