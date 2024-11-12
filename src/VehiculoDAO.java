import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.JOptionPane;

public class VehiculoDAO {

	public void registrarVehiculo(Vehiculo vehiculo) {
		String sql = "INSERT INTO vehiculos (matricula, marca, modelo, color, observaciones, hora_entrada, hora_salida, tipo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql)) {

			pstmt.setString(1, vehiculo.getMarca());
			pstmt.setString(2, vehiculo.getTipoVehiculo());
			pstmt.setString(3, vehiculo.getModelo());
			pstmt.setString(4, vehiculo.getColor());
			pstmt.setString(5,
					(vehiculo.getObservaciones() == null || vehiculo.getObservaciones().isEmpty())
							? "Sin golpes previos"
							: vehiculo.getObservaciones());
			pstmt.setTimestamp(6, Timestamp.valueOf(vehiculo.getHoraEntrada()));

			if (vehiculo.getHoraSalida() != null) {
				pstmt.setTimestamp(7, Timestamp.valueOf(vehiculo.getHoraSalida()));
			} else {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			pstmt.setString(8, vehiculo.getMatricula());// 8

			pstmt.executeUpdate();
			JOptionPane.showMessageDialog(null, "Vehículo registrado correctamente en la base de datos.", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Error al registrar el vehículo en la base de datos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);

		}
	}

	public Vehiculo obtenerVehiculoPorMatricula(String matricula) {
	    String sql = "SELECT matricula, marca, modelo, color, observaciones, hora_entrada, hora_salida, tipo "
	               + "FROM vehiculos WHERE matricula = ?";
	    Vehiculo vehiculo = null;

	    try (Connection conn = DatabaseConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql)) {
	        
	        pstmt.setString(1, matricula);
	        
	        try (ResultSet rs = pstmt.executeQuery()) {
	            if (rs.next()) {
	                String tipo = rs.getString("tipo");
	                String marca = rs.getString("marca");
	                String modelo = rs.getString("modelo");
	                String color = rs.getString("color");
	                String observaciones = rs.getString("observaciones");
	                LocalDateTime horaEntrada = rs.getTimestamp("hora_entrada").toLocalDateTime();
	                Timestamp horaSalidaTimestamp = rs.getTimestamp("hora_salida");
	                LocalDateTime horaSalida = (horaSalidaTimestamp != null) ? horaSalidaTimestamp.toLocalDateTime() : null;

	                vehiculo = new Vehiculo(tipo, matricula, marca, modelo, color, observaciones, horaEntrada, horaSalida);
	            } else {
	            	JOptionPane.showMessageDialog(null, "No se encontró un vehículo con la matrícula proporcionada.", 
                            "Error", JOptionPane.ERROR_MESSAGE);

	            }
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(null, "Error al obtener el vehículo de la base de datos.", 
                    "Error", JOptionPane.ERROR_MESSAGE);

	    }
	    return vehiculo;
	}
	
	public static int getTarifa(String tipoVehiculo, double horasEstacionado) {
	    int tarifa = 0;
	    int costoFinal = 0;

	    // Asignar la tarifa básica según el tipo de vehículo
	    switch (tipoVehiculo.toLowerCase()) {
	        case "moto":
	            if (horasEstacionado <= 0.5) {
	                // Si las horas son <= 0.5, cobrar tarifa reducida
	                costoFinal = 15;  // Tarifa para motos si el tiempo es menor o igual a 0.5 horas
	                return costoFinal; // Retorna inmediatamente para motos
	            } else {
	                tarifa = 25;  // Tarifa por hora para motos
	            }
	            break;
	        case "auto":
	            if (horasEstacionado <= 0.5) {
	                // Si las horas son <= 0.5, cobrar tarifa reducida
	                costoFinal = 25;  // Tarifa para autos si el tiempo es menor o igual a 0.5 horas
	                return costoFinal; // Retorna inmediatamente para autos
	            } else {
	                tarifa = 35;  // Tarifa por hora para autos
	            }
	            break;
	        case "camioneta":
	            if (horasEstacionado <= 0.5) {
	                // Si las horas son <= 0.5, cobrar tarifa reducida
	                costoFinal = 35;  // Tarifa para camionetas si el tiempo es menor o igual a 0.5 horas
	                return costoFinal; // Retorna inmediatamente para camionetas
	            } else {
	                tarifa = 45;  // Tarifa por hora para camionetas
	            }
	            break;
	        default:
	        	JOptionPane.showMessageDialog(null, "Tipo de vehículo desconocido.", 
                        "Error", JOptionPane.ERROR_MESSAGE);

	            return 0;  // Retorna 0 si el tipo de vehículo es desconocido
	    }

	    // Calculando el costo basado en las horas estacionadas
	    // Redondear hacia arriba según las reglas proporcionadas (si es mayor a media hora, cobra la hora completa)
	    int horasRedondeadas = (int) Math.ceil(horasEstacionado);

	    // Determinar el costo final
	    costoFinal = tarifa * horasRedondeadas;

	    // En caso de que sea un día completo (por ejemplo, si es más de 8 horas), asignar la tarifa completa
	    if (horasEstacionado >= 8) {
	        switch (tipoVehiculo.toLowerCase()) {
	            case "moto":
	                costoFinal = 130;  // Tarifa por día completo para motos
	                break;
	            case "auto":
	                costoFinal = 250;  // Tarifa por día completo para autos
	                break;
	            case "camioneta":
	                costoFinal = 350;  // Tarifa por día completo para camionetas
	                break;
	        }
	    }

	    return costoFinal; // Retorna el costo final calculado
	}



	/**
	 * Registra la hora de salida de un vehículo si su hora de salida es nula.
	 * Además, actualiza la tabla de lugares, poniendo ocupado = 0 y matricula =
	 * null cuando el vehículo sale.
	 *
	 * @param matricula  la matrícula del vehículo que se está buscando.
	 * @param horaSalida la hora de salida que se va a registrar.
	 * @throws SQLException si ocurre un error al realizar las consultas o
	 *                      actualizaciones en la base de datos.
	 */
	public void actualizarHoraSalidaYLugar(String matricula, Timestamp horaSalida) throws SQLException {
	    // 1. Verificar si la matrícula existe y tiene hora de salida nula
	    String selectSql = "SELECT hora_entrada, hora_salida, tipo FROM vehiculos WHERE matricula = ?";
	    String updateHoraSalidaSql = "UPDATE vehiculos SET hora_salida = ? WHERE matricula = ?";
	    String updateLugarSql = "UPDATE lugares SET ocupado = 0, matricula = NULL WHERE matricula = ?";
	    String updateCostoSql = "UPDATE vehiculos SET costo = ? WHERE matricula = ?";

	    try (Connection conn = DatabaseConnection.getConnection()) {
	        // Consultar la hora de entrada, salida y tipo del vehículo
	        try (PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {
	            pstmtSelect.setString(1, matricula);

	            try (ResultSet rs = pstmtSelect.executeQuery()) {
	                if (rs.next()) {
	                    // Obtener hora de entrada y tipo de vehículo
	                    Timestamp horaEntradaTimestamp = rs.getTimestamp("hora_entrada");
	                    LocalDateTime horaEntrada = horaEntradaTimestamp.toLocalDateTime();
	                    String tipo = rs.getString("tipo");
	                    Timestamp horaSalidaRegistrada = rs.getTimestamp("hora_salida");

	                    if (horaSalidaRegistrada == null) {
	                        // Si la hora de salida es nula, la actualizamos con la hora proporcionada
	                        try (PreparedStatement pstmtUpdateHoraSalida = conn.prepareStatement(updateHoraSalidaSql)) {
	                            pstmtUpdateHoraSalida.setTimestamp(1, horaSalida);
	                            pstmtUpdateHoraSalida.setString(2, matricula);
	                            pstmtUpdateHoraSalida.executeUpdate();
	                            JOptionPane.showMessageDialog(null, "Hora de salida registrada correctamente.", 
	                                    "Éxito", JOptionPane.INFORMATION_MESSAGE);

	                        }
	                    } else {
	                    	JOptionPane.showMessageDialog(null, "La hora de salida ya está registrada.", 
	                                "Error", JOptionPane.ERROR_MESSAGE);

	                    }

	                    // 2. Calcular el costo basado en la tarifa y el tiempo de estacionamiento
	                    LocalDateTime horaSalidaLocal = horaSalida.toLocalDateTime();
	                    double horasEstacionado = Duration.between(horaEntrada, horaSalidaLocal).toMinutes() / 60.0;  // Calcular tiempo en horas

	                    // Llamar al método getTarifa, que ahora incluye el cálculo de las fracciones y el redonde
	                    int costo = getTarifa(tipo, horasEstacionado);
	                    Vehiculo.setCosto(costo);

	                    // 3. Registrar el costo en la base de datos
	                    try (PreparedStatement pstmtUpdateCosto = conn.prepareStatement(updateCostoSql)) {
	                        pstmtUpdateCosto.setDouble(1, costo);
	                        pstmtUpdateCosto.setString(2, matricula);
	                        pstmtUpdateCosto.executeUpdate();
	                        
	                    }

	                    // 4. Actualizar la tabla de lugares, poniendo ocupado = 0 y matricula = null
	                    try (PreparedStatement pstmtUpdateLugar = conn.prepareStatement(updateLugarSql)) {
	                        pstmtUpdateLugar.setString(1, matricula);
	                        pstmtUpdateLugar.executeUpdate();
	                        
	                    }
	                } else {
	                	JOptionPane.showMessageDialog(null, "No se encontró un vehículo con la matrícula proporcionada.", 
	                              "Error", JOptionPane.ERROR_MESSAGE);

	                }
	            }
	        }
	    }
	}


}