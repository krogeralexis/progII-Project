package estacionamiento;

import java.time.LocalDateTime;

public class Factura {
    private Cliente cliente;
    private double monto;
    private double iva;
    private LocalDateTime fechaEmision;

    public Factura(Cliente cliente, double monto, double iva) {
        this.cliente = cliente;
        this.monto = monto;
        this.iva = iva;
        this.fechaEmision = LocalDateTime.now();
    }

    // Getters y Setters
    public Cliente getCliente() {
        return cliente;
    }

    public double getMonto() {
        return monto;
    }

    public double getIva() {
        return iva;
    }

    public LocalDateTime getFechaEmision() {
        return fechaEmision;
    }
}
