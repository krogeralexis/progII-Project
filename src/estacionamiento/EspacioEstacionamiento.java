package estacionamiento;

public class EspacioEstacionamiento {
    private int id;
    private String tipo; // "auto", "camioneta", "moto"
    private boolean ocupado;
    private Vehiculo vehiculoAsignado;

    public EspacioEstacionamiento(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.ocupado = false;
        this.vehiculoAsignado = null;
    }

    public boolean estaOcupado() {
        return ocupado;
    }

    public void asignarVehiculo(Vehiculo vehiculo) {
        this.vehiculoAsignado = vehiculo;
        this.ocupado = true;
    }

    public void liberarEspacio() {
        this.vehiculoAsignado = null;
        this.ocupado = false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }
}

