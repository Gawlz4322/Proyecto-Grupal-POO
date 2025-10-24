package Launcher;

import Controlador.LoginController;
import Modelo.SistemaFinanzas;
import Vista.VentanaLogin;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SistemaFinanzas modelo = new SistemaFinanzas();
            LoginController loginController = new LoginController(modelo);
            VentanaLogin vistaLogin = new VentanaLogin(loginController);
            loginController.setVista(vistaLogin);
            vistaLogin.mostrarVentana();
        });
    }
}