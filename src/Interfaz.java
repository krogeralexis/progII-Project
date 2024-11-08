import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class Interfaz extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private String[] opciones = {"Moto", "Auto", "Camioneta"};
	private JTextField lblObservaciones;
	private JTextField lblColor;
	private JTextField lblModelo;
	private JTextField lblMarca;
	private JTextField lblMatricula;
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
		lblNewLabel_1.setBounds(36, 63, 46, 14);
		panel_2.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Marca");
		lblNewLabel_1_1.setBounds(36, 87, 46, 14);
		panel_2.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Modelo");
		lblNewLabel_1_1_1.setBounds(36, 112, 46, 14);
		panel_2.add(lblNewLabel_1_1_1);
		
		JLabel lblNewLabel_1_1_2 = new JLabel("Observaciones");
		lblNewLabel_1_1_2.setBounds(36, 161, 78, 14);
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
		
		lblObservaciones = new JTextField();
		lblObservaciones.setBounds(134, 158, 86, 20);
		panel_2.add(lblObservaciones);
		lblObservaciones.setColumns(10);
		
		lblColor = new JTextField();
		lblColor.setBounds(134, 134, 86, 20);
		panel_2.add(lblColor);
		lblColor.setColumns(10);
		
		lblModelo = new JTextField();
		lblModelo.setBounds(134, 109, 86, 20);
		panel_2.add(lblModelo);
		lblModelo.setColumns(10);
		
		lblMarca = new JTextField();
		lblMarca.setBounds(134, 84, 86, 20);
		panel_2.add(lblMarca);
		lblMarca.setColumns(10);
		
		lblMatricula = new JTextField();
		lblMatricula.setBounds(134, 60, 86, 20);
		panel_2.add(lblMatricula);
		lblMatricula.setColumns(10);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("Salida", null, panel, null);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Consultas", null, panel_1, null);
	}
}
