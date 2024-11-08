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
             
            if (esLugarOcupado(conn, vehiculo.getTipoVehiculo(), vehiculo.getLugarId())) {
                System.out.println("El lugar está ocupado. No se puede registrar el vehículo.");
                return;
            }

            pstmt.setString(1, vehiculo.getMatricula());
            pstmt.setString(2, vehiculo.getMarca());
            pstmt.setString(3, vehiculo.getModelo());
            pstmt.setString(4, vehiculo.getColor());
            pstmt.setString(5, vehiculo.getObservaciones() != null ? vehiculo.getObservaciones() : "Sin golpes previos");
            pstmt.setTimestamp(6, Timestamp.valueOf(vehiculo.getHoraEntrada()));

            if (vehiculo.getHoraSalida() != null) {
                pstmt.setTimestamp(7, Timestamp.valueOf(vehiculo.getHoraSalida()));
            } else {
                pstmt.setNull(7, java.sql.Types.TIMESTAMP);
            }
            
            pstmt.setString(8, vehiculo.getTipoVehiculo());
            
            pstmt.executeUpdate();
            System.out.println("Vehículo registrado correctamente en la base de datos.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al registrar el vehículo en la base de datos.");
        }
    }
    
    public int obtenerIdLugarLibre(String tipoVehiculo) {
        String sql = "SELECT id FROM lugares WHERE tipoVehiculo = ? AND ocupado = 0 LIMIT 1";
        int lugarId = -1; // Valor predeterminado en caso de que no se encuentre un lugar libre

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, tipoVehiculo); // Establece el tipo de vehículo en la consulta

            try (ResultSet rs = pstmt.executeQuery()) { // Ejecuta la consulta y obtiene el resultado
                if (rs.next()) {
                    lugarId = rs.getInt("id"); // Obtiene el valor de la columna "id"
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al obtener el ID del lugar libre.");
        }

        return lugarId; // Devuelve el ID encontrado o -1 si no se encontró un lugar libre
    }

     
    
    
    
}