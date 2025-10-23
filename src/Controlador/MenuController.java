package Controlador;

import Modelo.SistemaFinanzas;
import Vista.MenuPrincipal;

public class MenuController {
    private MenuPrincipal Vista;
    private final SistemaFinanzas Modelo;

    public MenuController(SistemaFinanzas Modelo) {
        this.Modelo = Modelo;
    }
}