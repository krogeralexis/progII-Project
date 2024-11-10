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
import java.awt.event.ActionEvent;

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
	private double costo = 0;
	private JLabel lblCosto;

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

				if (txtMatricula.getText().isBlank() || txtMarca.getText().isBlank() || txtModelo.getText().isBlank()
						|| txtColor.getText().isBlank() || comboBox.getSelectedItem().toString().isBlank()) {

				} else {
					LocalDateTime horaEntrada = LocalDateTime.now();

					// Puedes asignar `null` a la hora de salida, ya que aún no ha salido
					LocalDateTime horaSalida = null;
					Vehiculo vehiculo = new Vehiculo(comboBox.getSelectedItem().toString().toLowerCase(),
							txtMatricula.getText().toString().toUpperCase(), txtMarca.getText().toString(),
							txtModelo.getText().toString(), txtColor.getText().toString(),
							txtObservaciones.getText().toString(), horaEntrada, horaSalida);

					// Intentar registrar el vehículo en la base de datos
					vehiculoDAO.registrarVehiculo(vehiculo);
					String idLugar = LugarDAO.obtenerIdLugarLibre(comboBox.getSelectedItem().toString().toLowerCase(),
							txtMatricula.getText());

					// Si se asignó un lugar, actualizar el label
					if (idLugar != null) {
						lblLugar.setText(idLugar); // Mostrar el ID del lugar en el label
					} else {
						lblLugar.setText("No hay lugares disponibles");
					}
				}
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
			    String matricula = txtMatSalida.getText();
			    String horaSalidaText = txtSalida.getText();

			    // Verificar que la matrícula y la hora de salida no estén vacías
			    if (matricula.isEmpty() || horaSalidaText.isEmpty()) {
			        JOptionPane.showMessageDialog(null, "Por favor, ingrese la matrícula y la hora de salida.");
			        return;
			    }

			    try {
			        // Obtener la fecha actual (sin hora)
			        LocalDateTime now = LocalDateTime.now();
			        // Crear una nueva fecha con la hora ingresada por el usuario (sin segundos)
			        String horaCompleta = now.toLocalDate() + " " + horaSalidaText + ":00"; // Añadir ":00" para los segundos

			        // Convertir la fecha y hora combinadas a un Timestamp
			        Timestamp horaSalida = Timestamp.valueOf(horaCompleta);

			        // Crear una instancia de VehiculoDAO y llamar al método para actualizar
			        VehiculoDAO vehiculoDAO = new VehiculoDAO();
			        vehiculoDAO.actualizarHoraSalidaYLugar(matricula, horaSalida);
			        String costoStr = String.valueOf(costo);
			        lblCosto.setText(costoStr);
			        // Mensaje de éxito
			        JOptionPane.showMessageDialog(null, "Hora de salida y lugar actualizados correctamente.");
			    } catch (IllegalArgumentException ex) {
			        // Manejar el error de formato de la hora
			        JOptionPane.showMessageDialog(null, "Formato de hora incorrecto. Asegúrese de usar 'HH:MM'.");
			    } catch (Exception ex) {
			        // Capturar cualquier otro error
			        ex.printStackTrace();
			        JOptionPane.showMessageDialog(null, "Ocurrió un error al actualizar la base de datos.");
			    }
			}

		});
		btnSalida.setBounds(343, 62, 112, 23);
		panel.add(btnSalida);

		JLabel lblNewLabel_4 = new JLabel("Costo:");
		lblNewLabel_4.setBounds(69, 142, 46, 14);
		panel.add(lblNewLabel_4);

		lblCosto = new JLabel("...");
		lblCosto.setBounds(125, 142, 46, 14);
		panel.add(lblCosto);

		JLabel lblNewLabel_5 = new JLabel("Hora de salida");
		lblNewLabel_5.setBounds(29, 104, 86, 14);
		panel.add(lblNewLabel_5);

		txtSalida = new JTextField();
		txtSalida.setColumns(10);
		txtSalida.setBounds(125, 101, 86, 20);
		panel.add(txtSalida);
		
	}
	
	public double calcularCosto(Vehiculo vehiculo) {
		Duration duration = Duration.between(vehiculo.getHoraEntrada(), vehiculo.getHoraSalida());
		long horas = duration.toHours();
		long minutos = duration.toMinutes() % 60;

		if (horas >= 8) {
			costo += 130; // Cobro por día
		} else {
			if (vehiculo.getTipoVehiculo() == "auto") {
				costo += (minutos > 30 ? (minutos / 60 + 1) * 25 : (minutos > 0 ? 25 : 0));
			} else if (vehiculo.getTipoVehiculo() == "moto") {
				costo += (minutos > 30 ? (minutos / 60 + 1) * 35 : (minutos > 0 ? 35 : 0));
			} else if (vehiculo.getTipoVehiculo() == "camioneta") {
				costo += (minutos > 30 ? (minutos / 60 + 1) * 45 : (minutos > 0 ? 45 : 0));
			}
		}

		return costo;
	}
}
