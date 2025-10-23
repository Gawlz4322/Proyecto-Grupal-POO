package Controlador;

import Modelo.SistemaFinanzas;
import Vista.MenuPrincipal;
import Vista.VentanaLogin;

import javax.swing.*;

public class LoginController {
    private VentanaLogin Vista;
    private final SistemaFinanzas Modelo;
    public LoginController(SistemaFinanzas modelo){
        this.Modelo = modelo;
    }
    public void intentarLogin(){
        //mover desde VentanaLogin
        String u = Vista.getUsuario();
        String p = Vista.getClave();
        String nombre = Modelo.validarCredenciales(u, p);
        if (!nombre.isEmpty()){
            JOptionPane.showMessageDialog(null, "Bienvenido" + nombre); //null por mientras
            if (Vista.preguntar("¿Haz realizado gastos?", "Gastos")){
                manejarInput(Vista.pedirInput("Ingrese el monto de gastos:"), "gasto");
            }
            if (Vista.preguntar("¿Haz recibido ingresos?", "Ingresos")){
                manejarInput(Vista.pedirInput("Ingrese el monto de ingresos:"), "ingreso");
            }
        }else{
            JOptionPane.showMessageDialog(null, "Usuario o Clave no valido");
        }
    }
    private void manejarInput(String input, String tipo){
        if (input != null && !input.isEmpty()){
            try {
                double monto = Double.parseDouble(input);
                if (tipo.equals("gasto")) {
                    Modelo.agregarGasto(monto);
                } else if (tipo.equals("ingreso")) {
                    Modelo.agregarIngreso(monto);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Monto inválido, se omitirá.");
            }
        }
    }
}
