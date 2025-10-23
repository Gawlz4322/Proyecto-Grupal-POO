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
        //añadir aquí preguntas iniciales?
    }
}
