import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

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
					vehiculo.getObservaciones() != null ? vehiculo.getObservaciones() : "Sin golpes previos");
			pstmt.setTimestamp(6, Timestamp.valueOf(vehiculo.getHoraEntrada()));

			if (vehiculo.getHoraSalida() != null) {
				pstmt.setTimestamp(7, Timestamp.valueOf(vehiculo.getHoraSalida()));
			} else {
				pstmt.setNull(7, java.sql.Types.TIMESTAMP);
			}
			pstmt.setString(8, vehiculo.getMatricula());// 8

			pstmt.executeUpdate();
			System.out.println("Vehículo registrado correctamente en la base de datos.");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error al registrar el vehículo en la base de datos.");
		}
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
		String selectSql = "SELECT hora_salida FROM vehiculos WHERE matricula = ?";
		String updateHoraSalidaSql = "UPDATE vehiculos SET hora_salida = ? WHERE matricula = ?";
		String updateLugarSql = "UPDATE lugares SET ocupado = 0, matricula = NULL WHERE matricula = ?";

		try (Connection conn = DatabaseConnection.getConnection()) {
			// Consultar la hora de salida del vehículo
			try (PreparedStatement pstmtSelect = conn.prepareStatement(selectSql)) {
				pstmtSelect.setString(1, matricula);

				try (ResultSet rs = pstmtSelect.executeQuery()) {
					if (rs.next()) {
						Timestamp horaSalidaRegistrada = rs.getTimestamp("hora_salida");

						if (horaSalidaRegistrada == null) {
							// Si la hora de salida es nula, la actualizamos con la hora proporcionada
							try (PreparedStatement pstmtUpdateHoraSalida = conn.prepareStatement(updateHoraSalidaSql)) {
								pstmtUpdateHoraSalida.setTimestamp(1, horaSalida);
								pstmtUpdateHoraSalida.setString(2, matricula);
								pstmtUpdateHoraSalida.executeUpdate();
								System.out.println("Hora de salida registrada correctamente.");
							}
						} else {
							System.out.println("La hora de salida ya está registrada.");
						}

						// 2. Actualizar la tabla de lugares, colocando ocupado = 0 y matricula = null
						try (PreparedStatement pstmtUpdateLugar = conn.prepareStatement(updateLugarSql)) {
							pstmtUpdateLugar.setString(1, matricula);
							pstmtUpdateLugar.executeUpdate();
							System.out.println("Lugar actualizado: ocupado = 0, matricula = NULL.");
						}
					} else {
						System.out.println("No se encontró un vehículo con la matrícula proporcionada.");
					}
				}
			}
		}
	}

}