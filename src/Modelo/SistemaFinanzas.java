package Modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaFinanzas {
    private double saldo = 0;
    private final List<String> historial = new ArrayList<>();

    public SistemaFinanzas() {

    }

    public void agregarGasto(double monto) {
        if (monto > 0) {
            this.saldo -= monto;
            this.historial.add("Gasto: -$" + monto);
        }
    }
    public void agregarIngreso(double monto) {
        if (monto > 0) {
            this.saldo += monto;
            this.historial.add("Ingreso: +$" + monto);
        }
    }
    public double getSaldo() {
        return saldo;
    }
    public List<String> getHistorial() {
        return historial;
    }
}
