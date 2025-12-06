package Model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa una transacción financiera (Ingreso o Gasto).
 */
public class Transaction {
    private String type; // "Ingreso" o "Gasto"
    private double amount;
    private String category;
    private String date; // Guardamos como String ISO o formateado para simplificar JSON por ahora
    private String description;

    public Transaction(String type, double amount, String category, String description) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.description = description;
        this.date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Devuelve una representación en cadena de la transacción.
     * 
     * @return Cadena con detalles de la transacción.
     */
    @Override
    public String toString() {
        return String.format("%s: $%.2f (%s) - %s", type, amount, category, date);
    }
}
