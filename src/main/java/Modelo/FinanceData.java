package Modelo;

import java.util.ArrayList;
import java.util.List;

public class FinanceData {
    private String userId;
    private double saldo;
    private List<String> historial;

    public FinanceData(String userId) {
        this.userId = userId;
        this.saldo = 0.0;
        this.historial = new ArrayList<>();
    }

    public String getUserId() {
        return userId;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<String> getHistorial() {
        return historial;
    }

    public void setHistorial(List<String> historial) {
        this.historial = historial;
    }
}
