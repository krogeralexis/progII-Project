package estacionamiento;

public class Empleado {
    private String nombre;
    private String cedula;
    private String licencia;
    private String vencimientoCarneSalud;
    private List<String> reemplazos; // Lista de días y horas en que fue reemplazado

    public Empleado(String nombre, String cedula, String licencia, String vencimientoCarneSalud) {
        this.nombre = nombre;
        this.cedula = cedula;
        this.licencia = licencia;
        this.vencimientoCarneSalud = vencimientoCarneSalud;
        this.reemplazos = new ArrayList<>();
    }

    public void agregarReemplazo(String reemplazo) {
        this.reemplazos.add(reemplazo);
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public String getLicencia() {
        return licencia;
    }

    public String getVencimientoCarneSalud() {
        return vencimientoCarneSalud;
    }

    public List<String> getReemplazos() {
        return reemplazos;
    }
}

