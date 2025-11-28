package Modelo;

import Controlador.FinanceStore;
import java.util.List;

public class SistemaFinanzas {
    private FinanceData currentData;
    private final FinanceStore financeStore;

    public SistemaFinanzas(FinanceStore financeStore) {
        this.financeStore = financeStore;
    }

    public void iniciarSesion(Usuario usuario) {
        this.currentData = financeStore.load(usuario.getUsername());
    }

    public void agregarGasto(double monto, String categoria) {
        if (currentData == null)
            throw new IllegalStateException("No hay usuario logueado");
        if (monto > 0) {
            currentData.setSaldo(currentData.getSaldo() - monto);
            currentData.getHistorial().add("Gasto (" + categoria + "): -$" + monto);
            financeStore.save(currentData);
        }
    }

    public void agregarIngreso(double monto) {
        if (currentData == null)
            throw new IllegalStateException("No hay usuario logueado");
        if (monto > 0) {
            currentData.setSaldo(currentData.getSaldo() + monto);
            currentData.getHistorial().add("Ingreso: +$" + monto);
            financeStore.save(currentData);
        }
    }

    public double getSaldo() {
        return currentData != null ? currentData.getSaldo() : 0;
    }

    public List<String> getHistorial() {
        return currentData != null ? currentData.getHistorial() : List.of();
    }
}
