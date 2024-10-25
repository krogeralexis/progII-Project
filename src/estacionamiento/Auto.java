package estacionamiento;

public class Auto extends Vehiculo {
    private double largo;

    public Auto(String matricula, String marca, String modelo, String color, double largo) {
        super(matricula, marca, modelo, color);
        this.largo = largo;
    }

    // Getter para largo
    public double getLargo() {
        return largo;
    }
}

