package estacionamiento;

public class Moto extends Vehiculo {
    private double cilindrada; // En cm³

    public Moto(String patente, double cilindrada) {
        super(patente);
        this.cilindrada = cilindrada;
    }

    public double getCilindrada() {
        return cilindrada;
    }
}

