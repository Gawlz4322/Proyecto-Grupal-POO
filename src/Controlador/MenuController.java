package Controlador;

import Modelo.SistemaFinanzas;
import Vista.MenuPrincipal;

import javax.swing.*;

public class MenuController {
    private MenuPrincipal Vista;
    private final SistemaFinanzas Modelo;

    public MenuController(SistemaFinanzas Modelo) {
        this.Modelo = Modelo;
    }
    public void setVista(MenuPrincipal Vista) {
        this.Vista = Vista;
    }
    public void manejarGasto(){
        String stringGasto = Vista.pedirInput("Monto del gasto:");
        if(stringGasto != null && !stringGasto.isEmpty()){
            try {
                double gastoNuevo = Double.parseDouble(stringGasto);
                Modelo.agregarGasto(gastoNuevo);
                actualizarSaldoVista();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Vista.getFrame(), "Debe ingresar un número válido.");
            }
        }
    }
    public void manejarIngreso(){
        String stringIngreso = Vista.pedirInput("Monto del ingreso:");
        if(stringIngreso != null && !stringIngreso.isEmpty()){
            try {
                double ingresoNuevo = Double.parseDouble(stringIngreso);
                Modelo.agregarIngreso(ingresoNuevo);
                actualizarSaldoVista();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido.");
            }
        }
    }
    public void actualizarSaldoVista() {
        Vista.actualizarDisplaySaldo(Modelo.getSaldo());
    }
}