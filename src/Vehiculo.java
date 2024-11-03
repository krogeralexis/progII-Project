import java.time.LocalDateTime;

import java.time.LocalDateTime;

public class Vehiculo {
    private String matricula;
    private String marca;
    private String modelo;
    private String color;
    private String observaciones;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;


    public Vehiculo(String matricula, String marca, String modelo, String color, String observaciones, LocalDateTime horaEntrada, LocalDateTime horaSalida) {
        this.matricula = matricula;
        this.marca = marca;
        this.modelo = modelo;
        this.color = color;
        this.observaciones = observaciones;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }
    // Getters y setters
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getModelo() {
		return modelo;
	}
	public void setModelo(String modelo) {
		this.modelo = modelo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public LocalDateTime getHoraEntrada() {
		return horaEntrada;
	}
	public void setHoraEntrada(LocalDateTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}
	public LocalDateTime getHoraSalida() {
		return horaSalida;
	}
	public void setHoraSalida(LocalDateTime horaSalida) {
		this.horaSalida = horaSalida;
	}

}
