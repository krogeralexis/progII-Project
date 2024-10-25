package estacionamiento;

public class Moto extends Vehiculo {
    private int cilindrada;

    public Moto(String matricula, String marca, String modelo, String color, int cilindrada) {
        super(matricula, marca, modelo, color);
        this.cilindrada = cilindrada;
    }

    // Getter y Setter para cilindrada
    public int getCilindrada() {
        return cilindrada;
    }
}

