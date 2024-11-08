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
        
}