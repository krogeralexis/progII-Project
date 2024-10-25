package estacionamiento;

public class Camioneta extends Vehiculo {
    private String tipoCamioneta; // "panorámica" o "furgón"

    public Camioneta(String matricula, String marca, String modelo, String color, String tipoCamioneta) {
        super(matricula, marca, modelo, color);
        this.tipoCamioneta = tipoCamioneta;
    }

    // Getter y Setter para tipoCamioneta
    public String getTipoCamioneta() {
        return tipoCamioneta;
    }
}

