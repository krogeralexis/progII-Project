package estacionamiento;

public class Cliente {
    private String nombre;
    private String cedula;
    private String telefono;
    private String rut;
    private String email;

    public Cliente(String nombre, String cedula, String telefono, String rut, String email) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.telefono = telefono;
        this.rut = rut;
        this.email = email;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRut() {
        return rut;
    }

    public String getEmail() {
        return email;
    }
}

