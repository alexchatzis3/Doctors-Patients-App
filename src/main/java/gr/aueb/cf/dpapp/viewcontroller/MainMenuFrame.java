package gr.aueb.cf.dpapp.viewcontroller;

import gr.aueb.cf.dpapp.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JSeparator;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;

import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;

/**
 * The {@code MainMenuFrame} class represents the main menu window
 * of the Doctors/Patients Swing application.
 * <p>
 * From this menu, the user can navigate to:
 * <ul>
 *     <li>Doctors Management</li>
 *     <li>Patients Management</li>
 *     <li>Doctors-Patients Relationship Management</li>
 * </ul>
 * It also contains a footer with author information linking to GitHub.
 */
public class MainMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Constructs the main menu frame and initializes UI components.
	 */
	public MainMenuFrame() {

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Thread.currentThread().getContextClassLoader().getResource("medical.png")));
		setTitle("Doctors / Patients Swing Application");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JSeparator headerSeperator = new JSeparator();
		headerSeperator.setBounds(10, 61, 416, 1);
		contentPane.add(headerSeperator);

		JPanel header = new JPanel();
		header.setBackground(new Color(0, 0, 168));
		header.setBounds(0, 0, 436, 57);
		contentPane.add(header);
		header.setLayout(null);

		JLabel dpLabel = new JLabel("Main Menu");
		dpLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		dpLabel.setForeground(Color.WHITE);
		dpLabel.setBounds(10, 10, 105, 25);
		header.add(dpLabel);

		JButton doctorsBtn = new JButton("");
		doctorsBtn.addActionListener(e -> {
			Main.getDoctorsMenuFrame().setVisible(true);
			Main.getMainMenuFrame().setEnabled(false);
		});
		doctorsBtn.setBounds(10, 72, 35, 35);
		contentPane.add(doctorsBtn);

		JLabel doctorsLabel = new JLabel("Doctors");
		doctorsLabel.setForeground(Color.BLUE);
		doctorsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		doctorsLabel.setBounds(51, 72, 68, 35);
		contentPane.add(doctorsLabel);

		JButton patientsBtn = new JButton("");
		patientsBtn.addActionListener(e -> {
			Main.getPatientsMenuFrame().setVisible(true);
			Main.getMainMenuFrame().setEnabled(false);
		});
		patientsBtn.setBounds(10, 146, 35, 35);
		contentPane.add(patientsBtn);

		JLabel patientsLabel = new JLabel("Patients");
		patientsLabel.setForeground(Color.BLUE);
		patientsLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		patientsLabel.setBounds(51, 146, 68, 35);
		contentPane.add(patientsLabel);

		JPanel footer = new JPanel();
		footer.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		footer.setBounds(10, 215, 416, 38);
		contentPane.add(footer);
		footer.setLayout(null);

		JLabel authorLabel = new JLabel("Alexandros Chatzis");

		authorLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					java.awt.Desktop.getDesktop().browse(
							new java.net.URI("https://github.com/alexchatzis3")
					);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				authorLabel.setForeground(Color.RED);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				authorLabel.setForeground(Color.BLUE);
			}
		});

		ImageIcon penIcon = new ImageIcon(
				Objects.requireNonNull(MainMenuFrame.class.getResource("/ink-pen.png"))
		);
		Image scaledImage = penIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
		authorLabel.setIcon(new ImageIcon(scaledImage));
		authorLabel.setForeground(Color.BLUE);
		authorLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		authorLabel.setBounds(10, 10, 200, 18);
		footer.add(authorLabel);

		JButton doctorsPatientsBtn = new JButton("");
		doctorsPatientsBtn.addActionListener(e -> {
			Main.getDoctorsPatientsFrame().setVisible(true);
			Main.getMainMenuFrame().setEnabled(false);
		});
		doctorsPatientsBtn.setBounds(200, 111, 35, 35);
		contentPane.add(doctorsPatientsBtn);

		JLabel lblDoctorsPatients = new JLabel("Doctors-Patients Links");
		lblDoctorsPatients.setForeground(Color.BLUE);
		lblDoctorsPatients.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDoctorsPatients.setBounds(245, 111, 181, 35);
		contentPane.add(lblDoctorsPatients);
	}
}
