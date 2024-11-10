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
        String sqlSelect = "SELECT id FROM lugares WHERE tipo = ? AND ocupado = 0 LIMIT 1";
        String sqlUpdate = "UPDATE lugares SET ocupado = 1, matricula = ? WHERE id = ?";
        String lugarIdStr = null;

        // Conexión a la base de datos
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                System.out.println("Error: No se pudo establecer conexión con la base de datos.");
                return null;
            } else {
                System.out.println("Conexión establecida exitosamente.");
            }

            // Preparar la consulta para buscar un lugar libre
            try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
                pstmtSelect.setString(1, tipoVehiculo); // Establece el tipo de vehículo en la consulta
                System.out.println("Ejecutando consulta SQL: " + pstmtSelect.toString());

                try (ResultSet rs = pstmtSelect.executeQuery()) {
                    System.out.println("Resultado de la consulta obtenido.");
                    if (rs.next()) {
                        int lugarId = rs.getInt("id"); // Obtiene el valor de la columna "id"
                        lugarIdStr = String.valueOf(lugarId);
                        System.out.println("Lugar encontrado: ID = " + lugarId);

                        // Preparar la actualización para marcar el lugar como ocupado y asignar la matrícula
                        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                            pstmtUpdate.setString(1, matricula); // Establece la matrícula
                            pstmtUpdate.setInt(2, lugarId); // Establece el ID del lugar
                            pstmtUpdate.executeUpdate(); // Ejecuta la actualización
                            System.out.println("Lugar ocupado y matrícula asignada.");
                        }
                        // Establecer el lugar en el objeto vehículo (si es necesario)
                        vehiculo.setLugar(lugarIdStr);
                    } else {
                        System.out.println("No se encontraron lugares libres para el tipo de vehículo: " + tipoVehiculo);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del lugar libre o actualizar el lugar.");
        }

        return lugarIdStr; // Devuelve el ID del lugar encontrado o null si no se encontró lugar libre
    }
}
