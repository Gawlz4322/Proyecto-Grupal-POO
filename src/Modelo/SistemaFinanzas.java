package Modelo;

import java.util.ArrayList;
import java.util.List;

public class SistemaFinanzas {
    public static final List<Usuario> USUARIOS = new ArrayList<>();
    public SistemaFinanzas() {
        inicializarUsuarios();
    }
    public void inicializarUsuarios(){
        USUARIOS.add(new Usuario("admin", "1234", "Administrador"));
        USUARIOS.add(new Usuario("Juanin", "abcd", "Juanin Juan Harry"));
    }
    private String validarCredenciales(String u, String p){
        for (Usuario usuarios : USUARIOS){
            if(usuarios.validarCredenciales(u,p)){
                return usuarios.getNombre();
            }
        }
        return "";
    }
}
