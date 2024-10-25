package estacionamiento;

import java.time.LocalDateTime;

public class SesionEstacionamiento {
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private Vehiculo vehiculo;
    private EspacioEstacionamiento espacioAsignado;
    private double montoAdeudado;

    public SesionEstacionamiento(LocalDateTime horaEntrada, Vehiculo vehiculo, EspacioEstacionamiento espacioAsignado) {
        this.horaEntrada = horaEntrada;
        this.vehiculo = vehiculo;
        this.espacioAsignado = espacioAsignado;
    }

    public void registrarSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
        calcularMontoAdeudado();
    }

    private void calcularMontoAdeudado() {
        // Aquí deberías implementar la lógica para calcular el monto en base al tiempo de estacionamiento
        // y el tipo de vehículo.
    }

    // Getters y Setters
    public LocalDateTime getHoraEntrada() {
        return horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return horaSalida;
    }

    public Vehiculo getVehiculo() {
        return vehiculo;
    }

    public EspacioEstacionamiento getEspacioAsignado() {
        return espacioAsignado;
    }

    public double getMontoAdeudado() {
        return montoAdeudado;
    }
}

