import java.time.Duration;

public class Tarifa {
    private static final double TARIFA_MOTO_DIA = 130;
    private static final double TARIFA_MOTO_HORA = 25;
    private static final double TARIFA_MOTO_MEDIA_HORA = 15;

    private static final double TARIFA_AUTO_DIA = 250;
    private static final double TARIFA_AUTO_HORA = 35;
    private static final double TARIFA_AUTO_MEDIA_HORA = 25;

    private static final double TARIFA_CAMIONETA_DIA = 350;
    private static final double TARIFA_CAMIONETA_HORA = 45;
    private static final double TARIFA_CAMIONETA_MEDIA_HORA = 35;

    public double calcularCosto(Vehiculo vehiculo) {
        Duration duration = Duration.between(vehiculo.getHoraEntrada(), vehiculo.getHoraSalida());
        long horas = duration.toHours();
        long minutos = duration.toMinutes() % 60;

        double costo = 0;

        if (horas >= 8) {
            costo += 130; // Cobro por día
        } else {
            if (vehiculo.getTipoVehiculo()== "auto") {
                costo += (minutos > 30 ? (minutos / 60 + 1) * 25 : (minutos > 0 ? 25 : 0));
            } else if (vehiculo.getTipoVehiculo()== "muto") {
                costo += (minutos > 30 ? (minutos / 60 + 1) * 35 : (minutos > 0 ? 35 : 0));
            } else if (vehiculo.getTipoVehiculo()== "camioneta") {
                costo += (minutos > 30 ? (minutos / 60 + 1) * 45 : (minutos > 0 ? 45 : 0));
            }
        }

        return costo;
    }
}

