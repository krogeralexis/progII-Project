/*
 * import java.sql.Timestamp; import java.time.LocalDateTime; import
 * java.util.Scanner;
 * 
 * public class MainApp { private static VehiculoDAO vehiculoDAO = new
 * VehiculoDAO(); private static Scanner scanner = new Scanner(System.in);
 * 
 * public static void main(String[] args) {
 * System.out.println("Bienvenido al sistema de gestión de estacionamiento");
 * 
 * while (true) { System.out.println("\nSeleccione una opción:");
 * System.out.println("1. Registrar un nuevo vehículo");
 * System.out.println("2. Salir");
 * 
 * int opcion = scanner.nextInt(); scanner.nextLine(); // Limpiar el buffer
 * 
 * if (opcion == 1) { registrarVehiculo(); } else if (opcion == 2) {
 * System.out.println("Saliendo del sistema..."); break; } else {
 * System.out.println("Opción no válida. Inténtelo de nuevo."); } } }
 * 
 * private static void registrarVehiculo() {
 * System.out.println("\nIngrese los datos del vehículo:");
 * 
 * System.out.print("Tipo: "); String tipoVehiculo = scanner.nextLine();
 * 
 * System.out.print("Matrícula: "); String matricula = scanner.nextLine();
 * 
 * System.out.print("Marca: "); String marca = scanner.nextLine();
 * 
 * System.out.print("Modelo: "); String modelo = scanner.nextLine();
 * 
 * System.out.print("Color: "); String color = scanner.nextLine();
 * 
 * System.out.print("Observaciones: "); String observaciones =
 * scanner.nextLine();
 * 
 * System.out.println("Registrando la hora de entrada en el sistema...");
 * LocalDateTime horaEntrada = LocalDateTime.now();
 * 
 * // Puedes asignar `null` a la hora de salida, ya que aún no ha salido
 * LocalDateTime horaSalida = null;
 * 
 * // Crear el objeto Vehiculo con los datos ingresados Vehiculo vehiculo = new
 * Vehiculo(tipoVehiculo.toLowerCase(), matricula.toUpperCase(), marca, modelo,
 * color, observaciones, horaEntrada, horaSalida);
 * 
 * // Intentar registrar el vehículo en la base de datos
 * vehiculoDAO.registrarVehiculo(vehiculo); } }
 */