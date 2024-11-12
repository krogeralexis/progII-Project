import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

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
        String sqlCheckMatricula = "SELECT matricula FROM lugares WHERE matricula = ?";

        // Conexión a la base de datos
        try (Connection conn = DatabaseConnection.getConnection()) {
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Error: No se pudo establecer conexión con la base de datos.", "Error de Conexión", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            // Verificar si la matrícula ya está registrada
            try (PreparedStatement pstmtCheckMatricula = conn.prepareStatement(sqlCheckMatricula)) {
                pstmtCheckMatricula.setString(1, matricula); // Establece la matrícula para la búsqueda
                try (ResultSet rsMatricula = pstmtCheckMatricula.executeQuery()) {
                    if (rsMatricula.next()) {
                        // Si la matrícula ya existe, mostrar un mensaje y cancelar el proceso
                        JOptionPane.showMessageDialog(null, "Error: La matrícula " + matricula + " ya está registrada.", "Matrícula Duplicada", JOptionPane.ERROR_MESSAGE);
                        return null; // Cancelar el alta
                    }
                }
            }

            // Preparar la consulta para buscar un lugar libre
            try (PreparedStatement pstmtSelect = conn.prepareStatement(sqlSelect)) {
                pstmtSelect.setString(1, tipoVehiculo); // Establece el tipo de vehículo en la consulta

                try (ResultSet rs = pstmtSelect.executeQuery()) {
                    if (rs.next()) {
                        int lugarId = rs.getInt("id"); // Obtiene el valor de la columna "id"
                        lugarIdStr = String.valueOf(lugarId);
                        JOptionPane.showMessageDialog(null, "Lugar encontrado: ID = " + lugarId, "Resultado de Búsqueda", JOptionPane.INFORMATION_MESSAGE);

                        // Preparar la actualización para marcar el lugar como ocupado y asignar la matrícula
                        try (PreparedStatement pstmtUpdate = conn.prepareStatement(sqlUpdate)) {
                            pstmtUpdate.setString(1, matricula); // Establece la matrícula
                            pstmtUpdate.setInt(2, lugarId); // Establece el ID del lugar
                            pstmtUpdate.executeUpdate(); // Ejecuta la actualización
                        }
                        // Establecer el lugar en el objeto vehículo (si es necesario)
                        vehiculo.setLugar(lugarIdStr);
                    } else {
                        JOptionPane.showMessageDialog(null, "No se encontraron lugares libres para el tipo de vehículo: " + tipoVehiculo, "Sin Lugares Disponibles", JOptionPane.WARNING_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al obtener el ID del lugar libre o actualizar el lugar.", "Error de Operación", JOptionPane.ERROR_MESSAGE);
        }

        return lugarIdStr; // Devuelve el ID del lugar encontrado o null si no se encontró lugar libre
    }
}
