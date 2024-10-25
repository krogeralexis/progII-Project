package estacionamiento;

import java.util.ArrayList;
import java.util.List;

public class EspacioEstacionamiento {
    private int id;
    private String tipo; // "auto", "camioneta"
    private boolean ocupado; // Si el espacio está completamente ocupado
    private Vehiculo vehiculoAsignado; // Para autos o camionetas
    private List<Moto> motosAsignadas; // Para motos compartiendo espacio

    public EspacioEstacionamiento(int id, String tipo) {
        this.id = id;
        this.tipo = tipo;
        this.ocupado = false;
        this.vehiculoAsignado = null;
        this.motosAsignadas = new ArrayList<>();
    }

    public boolean estaOcupado() {
        if (tipo.equals("camioneta")) {
            return motosAsignadas.size() == 3 || (vehiculoAsignado != null && motosAsignadas.size() > 0);
        }
        if (tipo.equals("auto")) {
            return motosAsignadas.size() == 2 || vehiculoAsignado != null;
        }
        return ocupado;
    }

    public boolean asignarVehiculo(Vehiculo vehiculo) {
        if (vehiculo instanceof Moto) {
            Moto moto = (Moto) vehiculo;

            if (moto.getCilindrada() > 960) {
                // Si la moto tiene más de 960 cm³, cuenta como auto
                if (vehiculoAsignado == null && motosAsignadas.size() == 0) {
                    this.vehiculoAsignado = moto;
                    this.ocupado = true;
                    return true;
                } else {
                    return false; // No hay espacio para otra moto grande
                }
            } else {
                // Moto pequeña puede compartir espacio
                if (tipo.equals("camioneta") && motosAsignadas.size() < 3) {
                    motosAsignadas.add(moto);
                    return true;
                } else if (tipo.equals("auto") && motosAsignadas.size() < 2) {
                    motosAsignadas.add(moto);
                    return true;
                }
            }
        } else if (vehiculo instanceof Auto) {
            Auto auto = (Auto) vehiculo;
            if (auto.getLargo() > 4.6) {
                // Si el auto mide más de 4,6 metros, cuenta como camioneta
                if (tipo.equals("camioneta") && vehiculoAsignado == null && motosAsignadas.size() == 0) {
                    this.vehiculoAsignado = auto;
                    this.ocupado = true;
                    return true;
                }
            } else {
                // Asignar el auto si hay espacio
                if (tipo.equals("camioneta")) {
                    if (vehiculoAsignado == null && motosAsignadas.size() < 2) {
                        this.vehiculoAsignado = auto;
                        return true;
                    } else if (vehiculoAsignado == null && motosAsignadas.isEmpty()) {
                        this.vehiculoAsignado = auto;
                        return true;
                    }
                } else if (tipo.equals("auto") && vehiculoAsignado == null && motosAsignadas.isEmpty()) {
                    this.vehiculoAsignado = auto;
                    return true;
                }
            }
        }

        return false; // No se puede asignar el vehículo al espacio
    }

    public void liberarEspacio() {
        this.vehiculoAsignado = null;
        this.motosAsignadas.clear();
        this.ocupado = false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public String getTipo() {
        return tipo;
    }

    public Vehiculo getVehiculoAsignado() {
        return vehiculoAsignado;
    }

    public List<Moto> getMotosAsignadas() {
        return motosAsignadas;
    }
}
