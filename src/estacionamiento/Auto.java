package estacionamiento;

public class Auto extends Vehiculo {
    private double largo; // En metros

    public Auto(String patente, double largo) {
        super(patente);
        this.largo = largo;
    }

    public double getLargo() {
        return largo;
    }
}

