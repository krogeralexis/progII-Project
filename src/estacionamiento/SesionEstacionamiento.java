package estacionamiento;

import java.time.Duration;
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
        // Calcular el tiempo de estacionamiento en minutos
        Duration duracion = Duration.between(horaEntrada, horaSalida);
        long minutosEstacionados = duracion.toMinutes();

        // Obtener la tarifa dependiendo del tipo de vehículo
        double tarifaPorMediaHora = 0;
        double tarifaPorHora = 0;
        double tarifaPorDia = 0;
        double tarifaPorMes = 0;

        if (vehiculo instanceof Moto) {
            tarifaPorMediaHora = 15;
            tarifaPorHora = 25;
            tarifaPorDia = 150;
            tarifaPorMes = 1500;
        } else if (vehiculo instanceof Auto) {
            tarifaPorMediaHora = 25;
            tarifaPorHora = 35;
            tarifaPorDia = 250;
            tarifaPorMes = 2500;
        } else if (vehiculo instanceof Camioneta) {
            tarifaPorMediaHora = 35;
            tarifaPorHora = 45;
            tarifaPorDia = 350;
            tarifaPorMes = 3500;
        }

        // Calcular el monto adeudado basado en el tiempo
        if (minutosEstacionados >= 43200) { // 30 días
            montoAdeudado = tarifaPorMes;
        } else if (minutosEstacionados >= 1440) { // 24 horas (1 día)
            montoAdeudado = (minutosEstacionados / 1440) * tarifaPorDia;
        } else if (minutosEstacionados >= 60) { // Horas
            montoAdeudado = (minutosEstacionados / 60) * tarifaPorHora;
            if (minutosEstacionados % 60 > 30) {
                montoAdeudado += tarifaPorHora; // Si supera los 30 minutos, cobrar la hora completa
            } else if (minutosEstacionados % 60 > 0) {
                montoAdeudado += tarifaPorMediaHora; // Si son más de 0 pero menos de 30 minutos, cobrar media hora
            }
        } else { // Menos de 1 hora, aplicar tarifa mínima de media hora
            montoAdeudado = tarifaPorMediaHora;
        }
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
