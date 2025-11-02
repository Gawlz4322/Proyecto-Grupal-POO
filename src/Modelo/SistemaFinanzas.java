package Modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaFinanzas {
    public static final List<Usuario> USUARIOS = new ArrayList<>();
    private double saldo = 0;
    private final List<String> historial = new ArrayList<>();

    public SistemaFinanzas() {
        inicializarUsuarios();
    }
    public void inicializarUsuarios(){
        USUARIOS.add(new Usuario("admin", "1234", "Administrador"));
        USUARIOS.add(new Usuario("Juanin", "abcd", "Juanin Juan Harry"));
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
