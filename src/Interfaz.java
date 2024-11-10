import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
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
	private JTextField txtIdLugarSalida;
	private JLabel lblLugar;

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
					LugarDAO.obtenerIdLugarLibre(comboBox.getSelectedItem().toString().toLowerCase(), txtMatricula.getText());
					lblLugar.setText(vehiculo.getLugar());
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
		lblNewLabel_3.setBounds(51, 65, 100, 14);
		panel.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Id Lugar");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(51, 104, 100, 14);
		panel.add(lblNewLabel_4);
		
		txtMatSalida = new JTextField();
		txtMatSalida.setBounds(125, 63, 86, 20);
		panel.add(txtMatSalida);
		txtMatSalida.setColumns(10);
		
		txtIdLugarSalida = new JTextField();
		txtIdLugarSalida.setBounds(125, 102, 86, 20);
		panel.add(txtIdLugarSalida);
		txtIdLugarSalida.setColumns(10);
		
		JButton btnSalida = new JButton("Ingresar Salida");
		btnSalida.setBounds(343, 62, 112, 23);
		panel.add(btnSalida);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Consultas", null, panel_1, null);
	}
}
