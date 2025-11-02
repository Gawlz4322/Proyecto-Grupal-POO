package Controlador;

import Modelo.SistemaFinanzas;
import Modelo.Usuario;
import Vista.MenuPrincipal;
import Vista.VentanaLogin;

import javax.swing.*;

public class LoginController {
    private VentanaLogin Vista;
    private final AuthService authService;
    private final SistemaFinanzas ModeloFinanzas;
    private final MenuPrincipal menuPrincipal;
    public LoginController(AuthService authService, SistemaFinanzas ModeloFinanzas, MenuPrincipal menuPrincipal) {
        this.ModeloFinanzas = ModeloFinanzas;
        this.menuPrincipal = menuPrincipal;
        this.authService = authService;
    }
    public void intentarLogin(){
        String u = Vista.getUsuario();
        char[] p = Vista.getClave().toCharArray();
        try {
            Usuario usuario = authService.login(u, p);
            JOptionPane.showMessageDialog(Vista.getFrame(), "Bienvenido, " + usuario.getUsername());
            Vista.cerrarVentana();
            menuPrincipal.mostrarVentana();
        } catch(IllegalArgumentException e){
            JOptionPane.showMessageDialog(Vista.getFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally{
            Contraseña.zero(p);
            Vista.limpiarClave();
        }
    }

    public void setVista(VentanaLogin Vista) {
        this.Vista = Vista;
    }
}
