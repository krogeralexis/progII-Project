import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import com.toedter.calendar.JDateChooser;

public class Interfaz extends JFrame {

	private static VehiculoDAO vehiculoDAO = new VehiculoDAO();
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] opciones = { "Moto", "Auto", "Camioneta" };
	private JTextField txtObservaciones;
	private JTextField txtColor;
	private JTextField txtModelo;
	private JTextField txtMarca;
	private JTextField txtMatricula;
	private JTextField txtMatSalida;
	private JLabel lblLugar;
	private JTextField txtSalida;
	protected Vehiculo vehiculo;
	private float costo;
	private JDateChooser dateChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Interfaz frame = new Interfaz();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Interfaz() {
		setTitle("Estacionamiento ITS");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 400);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(new Color(192, 192, 192));
		tabbedPane.setBounds(0, 0, 584, 361);
		contentPane.add(tabbedPane);

		JPanel panel_2 = new JPanel();
		panel_2.setBackground(new Color(64, 128, 128));
		tabbedPane.addTab("Ingreso", null, panel_2, null);
		panel_2.setLayout(null);

		JLabel lblNewLabel = new JLabel("Ingreso de vehiculos");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel.setBounds(192, 11, 181, 31);
		panel_2.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Matricula");
		lblNewLabel_1.setBounds(36, 63, 78, 14);
		panel_2.add(lblNewLabel_1);

		JLabel lblNewLabel_1_1 = new JLabel("Marca");
		lblNewLabel_1_1.setBounds(36, 87, 46, 14);
		panel_2.add(lblNewLabel_1_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("Modelo");
		lblNewLabel_1_1_1.setBounds(36, 112, 46, 14);
		panel_2.add(lblNewLabel_1_1_1);

		JLabel lblNewLabel_1_1_2 = new JLabel("Observaciones");
		lblNewLabel_1_1_2.setBounds(36, 161, 103, 14);
		panel_2.add(lblNewLabel_1_1_2);

		JLabel lblNewLabel_1_2 = new JLabel("Color");
		lblNewLabel_1_2.setBounds(36, 137, 46, 14);
		panel_2.add(lblNewLabel_1_2);

		JComboBox<String> comboBox = new JComboBox<>(opciones);
		comboBox.setSelectedIndex(-1);
		comboBox.setBounds(134, 182, 117, 22);
		panel_2.add(comboBox);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("Tipo");
		lblNewLabel_1_1_2_1.setBounds(36, 186, 78, 14);
		panel_2.add(lblNewLabel_1_1_2_1);

		txtObservaciones = new JTextField();
		txtObservaciones.setBounds(134, 158, 86, 20);
		panel_2.add(txtObservaciones);
		txtObservaciones.setColumns(10);

		txtColor = new JTextField();
		txtColor.setBounds(134, 134, 86, 20);
		panel_2.add(txtColor);
		txtColor.setColumns(10);

		txtModelo = new JTextField();
		txtModelo.setBounds(134, 109, 86, 20);
		panel_2.add(txtModelo);
		txtModelo.setColumns(10);

		txtMarca = new JTextField();
		txtMarca.setBounds(134, 84, 86, 20);
		panel_2.add(txtMarca);
		txtMarca.setColumns(10);

		txtMatricula = new JTextField();
		txtMatricula.setBounds(134, 60, 86, 20);
		panel_2.add(txtMatricula);
		txtMatricula.setColumns(10);

		JButton btnRegistrar = new JButton("Registrar");
		btnRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String matricula = txtMatricula.getText().trim();
				String marca = txtMarca.getText().trim();
				String modelo = txtModelo.getText().trim();
				String color = txtColor.getText().trim();
				String observaciones = txtObservaciones.getText().trim();
				// Validación de matrícula (3 letras + 3 o 4 números)
				if (!Pattern.matches("^[A-S]{3}\\d{3,4}$", matricula.toUpperCase())) {
					JOptionPane.showMessageDialog(null, "La matrícula debe tener 3 letras y 3 o 4 números.");
					return;
				}

				// Verificar que los campos no estén vacíos
				if (txtMatricula.getText().isBlank() || txtMarca.getText().isBlank() || txtModelo.getText().isBlank()
						|| txtColor.getText().isBlank() || comboBox.getSelectedItem().toString().isBlank()) {
					JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.");
					return; // Detener el proceso si hay campos vacíos
				}

				// Verificar si hay lugar disponible antes de registrar el vehículo
				String idLugar = LugarDAO.obtenerIdLugarLibre(comboBox.getSelectedItem().toString().toLowerCase(),
						txtMatricula.getText());

				// Si no hay lugar disponible, mostrar mensaje de error y detener el proceso
				if (idLugar == null) {
					JOptionPane.showMessageDialog(null, "No hay lugares disponibles para este vehículo.");
					return; // Detener el proceso si no hay lugar
				}

				// Si hay lugar disponible, proceder con el registro del vehículo
				LocalDateTime horaEntrada = LocalDateTime.now();
				LocalDateTime horaSalida = null; // La hora de salida será null por ahora

				// Crear el objeto vehículo
				Vehiculo vehiculo = new Vehiculo(comboBox.getSelectedItem().toString().toLowerCase(),
						txtMatricula.getText().toString().toUpperCase(), txtMarca.getText().toString(),
						txtModelo.getText().toString(), txtColor.getText().toString(),
						txtObservaciones.getText().toString(), horaEntrada, horaSalida);

				// Intentar registrar el vehículo en la base de datos
				vehiculoDAO.registrarVehiculo(vehiculo);

				// Si se asignó un lugar, actualizar el label
				lblLugar.setText(idLugar); // Mostrar el ID del lugar en el label
			}

		});
		btnRegistrar.setBounds(320, 83, 89, 23);
		panel_2.add(btnRegistrar);

		JLabel lblNewLabel_2 = new JLabel("Lugar asig:");
		lblNewLabel_2.setBounds(36, 229, 89, 14);
		panel_2.add(lblNewLabel_2);

		lblLugar = new JLabel("...");
		lblLugar.setBounds(134, 229, 46, 14);
		panel_2.add(lblLugar);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 128, 128));
		tabbedPane.addTab("Salida", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel_3 = new JLabel("Matricula");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(44, 65, 71, 14);
		panel.add(lblNewLabel_3);

		txtMatSalida = new JTextField();
		txtMatSalida.setBounds(125, 63, 86, 20);
		panel.add(txtMatSalida);
		txtMatSalida.setColumns(10);

		JButton btnSalida = new JButton("Ingresar Salida");
		btnSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Obtener matrícula y hora de salida desde los campos de texto
				String matricula = txtMatSalida.getText();
				String horaSalidaText = txtSalida.getText();

				// Validar si la fecha fue seleccionada
				Date fechaSeleccionada = dateChooser.getDate();
				if (fechaSeleccionada == null) {
					JOptionPane.showMessageDialog(null, "Por favor, seleccione una fecha.");
					return;
				}

				// Convertir la fecha seleccionada a LocalDateTime
				LocalDateTime fechaSalida = new java.sql.Date(fechaSeleccionada.getTime()).toLocalDate().atStartOfDay();
				if (horaSalidaText.isEmpty()) {
					JOptionPane.showMessageDialog(null, "Por favor, ingrese una hora de salida.");
					return;
				}

				// Parsear la hora y minutos desde el campo txtSalida
				int hora = Integer.parseInt(horaSalidaText.substring(0, 2));
				int minutos = Integer.parseInt(horaSalidaText.substring(3, 5));

				// Combinar la fecha seleccionada con la hora de salida
				fechaSalida = fechaSalida.withHour(hora).withMinute(minutos);

				// Consultar la hora de entrada del vehículo en la base de datos
				Vehiculo vehiculo = vehiculoDAO.obtenerVehiculoPorMatricula(matricula);

				if (vehiculo == null) {
					JOptionPane.showMessageDialog(null, "No se encontró un vehículo con la matrícula proporcionada.");
					return;
				}

				LocalDateTime horaEntrada = vehiculo.getHoraEntrada();
				if (horaEntrada == null) {
					JOptionPane.showMessageDialog(null, "La hora de entrada del vehículo no está disponible.");
					return;
				}

				// Validar que la hora de salida no sea anterior a la de entrada
				if (fechaSalida.isBefore(horaEntrada)) {
					JOptionPane.showMessageDialog(null,
							"La fecha y hora de salida no puede ser anterior a la fecha de entrada.", "Error",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				// Procesar la salida, actualizar la base de datos y mostrar los datos del
				// vehículo
				try {
					// Si se seleccionó una fecha, combinar con la hora ingresada
					LocalDateTime horaSalidaFinal = fechaSalida;

					// Actualizar la hora de salida en la base de datos
					vehiculoDAO.actualizarHoraSalidaYLugar(matricula, Timestamp.valueOf(horaSalidaFinal));

					// Mostrar confirmación
					JOptionPane.showMessageDialog(null, "Salida registrada correctamente.");

					// Mostrar los datos del vehículo
					vehiculo = vehiculoDAO.obtenerVehiculoPorMatricula(matricula); // Actualizar el vehículo
					if (vehiculo != null) {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						String horaEntradaFormatted = vehiculo.getHoraEntrada().format(formatter);
						String horaSalidaFormatted = horaSalidaFinal.format(formatter);

						// Calcular el costo
						double horasEstacionado = Duration.between(vehiculo.getHoraEntrada(), horaSalidaFinal)
								.toMinutes() / 60.0;
						int costo = Vehiculo.getCosto();

						// Crear el mensaje con los datos del vehículo
						String mensaje = "Datos del vehículo:\n\n" + "Matrícula: " + vehiculo.getMatricula() + "\n"
								+ "Marca: " + vehiculo.getMarca() + "\n" + "Modelo: " + vehiculo.getModelo() + "\n"
								+ "Color: " + vehiculo.getColor() + "\n" + "Observaciones: "
								+ vehiculo.getObservaciones() + "\n" + "Hora de entrada: " + horaEntradaFormatted + "\n"
								+ "Hora de salida: " + horaSalidaFormatted + "\n" + "Costo: $" + costo;

						// Mostrar el mensaje
						JOptionPane.showMessageDialog(null, mensaje, "Información del vehículo",
								JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (Exception ex) {
					// Manejo de excepciones
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Error al procesar la salida.");
				}
			}
		});

		btnSalida.setBounds(314, 62, 141, 23);
		panel.add(btnSalida);

		JLabel lblNewLabel_5 = new JLabel("fecha y hora de salida");
		lblNewLabel_5.setBounds(10, 107, 120, 14);
		panel.add(lblNewLabel_5);

		txtSalida = new JTextField();
		txtSalida.setColumns(10);
		txtSalida.setBounds(235, 104, 86, 20);
		panel.add(txtSalida);

		dateChooser = new JDateChooser();
		dateChooser.setDate(new Date()); // Establece la fecha actual como predeterminada
		dateChooser.setBounds(145, 104, 80, 20);
		panel.add(dateChooser);

	}
}
