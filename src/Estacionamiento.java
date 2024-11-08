import java.time.LocalDateTime;
import java.util.List;

public class Estacionamiento extends Vehiculo{
    public Estacionamiento(String matricula, String marca, String tipoVehiculo, String modelo, String color,
			String observaciones, LocalDateTime horaEntrada, LocalDateTime horaSalida) {
		super(matricula, marca, tipoVehiculo, modelo, color, observaciones, horaEntrada, horaSalida);
		// TODO Auto-generated constructor stub
	}

	private List<LugarEstacionamiento> lugaresMotos;
    private List<LugarEstacionamiento> lugaresAutos;
    private List<LugarEstacionamiento> lugaresCamionetas;

    // Métodos para asignar y liberar lugar
    public LugarEstacionamiento asignarLugar(String tipoVehiculo) {
        
    	
    }

    public void liberarLugar(int lugarId) {
        // lógica para liberar lugar
    }
}
