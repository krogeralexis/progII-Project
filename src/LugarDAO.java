import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LugarDAO {

    /**
     * Obtiene el ID de un lugar libre en la base de datos para un tipo específico de vehículo y registra la matrícula.
     *
     * @param tipoVehiculo el tipo de vehículo para el que se desea buscar un lugar (por ejemplo, "auto", "camioneta", "moto").
     * @param matricula la matrícula del vehículo que ocupará el lugar.
     * @return el ID del lugar libre encontrado o null si no hay lugares libres disponibles para ese tipo.
     * @throws SQLException si ocurre un error al realizar la consulta en la base de datos.
     */
    public static String obtenerIdLugarLibre(String tipoVehiculo, String matricula) {
        Vehiculo vehiculo = new Vehiculo(null, null, null, null, null, null, null, null);
        tipoVehiculo = vehiculo.getTipoVehiculo();
        String sqlSelect = "SELECT id FROM lugares WHERE tipo = ? AND ocupado = 0 LIMIT 1";
        String sqlUpdate = "UPDATE lugares SET ocupado = 1, matricula = ? WHERE id = ?";
        String lugarIdStr = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {

            pstmtSelect.setString(1, tipoVehiculo); // Establece el tipo de vehículo en la consulta

            try (ResultSet rs = pstmtSelect.executeQuery()) { // Ejecuta la consulta y obtiene el resultado
                if (rs.next()) {
                    int lugarId = rs.getInt("id"); // Obtiene el valor de la columna "id"
                    lugarIdStr = String.valueOf(lugarId);

                    // Actualiza el registro para ocupar el lugar y asignar la matrícula
                    try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                        pstmtUpdate.setString(1, vehiculo.getMatricula()); // Establece la matrícula
                        pstmtUpdate.setInt(2, lugarId); // Establece el ID del lugar
                        pstmtUpdate.executeUpdate(); // Ejecuta la actualización
                    }
                    
                    vehiculo.setLugar(lugarIdStr);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del lugar libre o actualizar el lugar.");
        }

        return vehiculo.setLugar(lugarIdStr); // Devuelve el ID encontrado o null si no se encontró un lugar libre
    }
}
