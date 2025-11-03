package Controlador;

import Modelo.SistemaFinanzas;
import Vista.MenuPrincipal;

import javax.swing.*;
import java.util.List;

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
                if (gastoNuevo <= 0) {
                    JOptionPane.showMessageDialog(Vista.getFrame(), "El monto debe ser mayor a 0");
                    return;
                }
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
                if (ingresoNuevo <= 0) {
                    JOptionPane.showMessageDialog(Vista.getFrame(), "El monto debe ser mayor a 0");
                    return;
                }
                Modelo.agregarIngreso(ingresoNuevo);
                actualizarSaldoVista();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(Vista.getFrame(), "Debe ingresar un número válido.");
            }
        }
    }
    
    public void manejarHistorial(){
        List<String> historial = Modelo.getHistorial();
        if (historial.isEmpty()) {
            JOptionPane.showMessageDialog(Vista.getFrame(), "No hay movimientos registrados", "Historial", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JTextArea textArea = new JTextArea(String.join("\n", historial));
            textArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new java.awt.Dimension(400, 300));
            JOptionPane.showMessageDialog(Vista.getFrame(), scrollPane, "Historial de Movimientos", JOptionPane.PLAIN_MESSAGE);
        }
    }
    
    public void actualizarSaldoVista() {
        Vista.actualizarDisplaySaldo(Modelo.getSaldo());
    }
}