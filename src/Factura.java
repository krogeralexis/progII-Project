import java.time.Duration;

public class Factura {
    private Vehiculo vehiculo;
    private double total;
    private static final double IVA = 0.22;

    public Factura(Vehiculo vehiculo, double total) {
        this.vehiculo = vehiculo;
        this.total = total * (1 + IVA);
    }

    public void imprimirFactura() {
        // Lógica para mostrar factura en consola o GUI
    }
}
	