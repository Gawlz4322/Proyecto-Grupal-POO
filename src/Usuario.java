public class Usuario {
    private String username;
    private String password;
    private String nombre;

    public Usuario(String username, String password, String nombre) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
    }
    //no se ocupa aún
    public boolean validarCredenciales(String u, String p){
        return this.username.equals(u) && this.password.equals(p);
    }
}
