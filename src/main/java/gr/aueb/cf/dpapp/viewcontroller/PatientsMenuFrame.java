package gr.aueb.cf.dpapp.viewcontroller;

import gr.aueb.cf.dpapp.Main;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Toolkit;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;

/**
 * The {@code PatientsMenuFrame} class represents the user interface menu
 * for managing patient data.
 * <p>
 * From this menu, the user can:
 * <ul>
 *     <li>View, update, and delete patients</li>
 *     <li>Insert new patients</li>
 *     <li>Return to the main menu</li>
 * </ul>
 */
public class PatientsMenuFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Constructs the Patients Menu frame and initializes all GUI components.
	 */
	public PatientsMenuFrame() {

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(Thread.currentThread().getContextClassLoader().getResource("medical.png")));
		setTitle("Patients' Menu");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton patientsViewBtn = new JButton("View Patients");
		patientsViewBtn.addActionListener(e -> {
			Main.getPatientsUpdateDeleteFrame().setVisible(true);
			Main.getPatientsMenuFrame().setEnabled(false);
		});
		patientsViewBtn.setForeground(Color.BLUE);
		patientsViewBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		patientsViewBtn.setBounds(137, 33, 147, 50);
		contentPane.add(patientsViewBtn);

		JButton patientsInsertBtn = new JButton("Insert Patient");
		patientsInsertBtn.addActionListener(e -> {
			Main.getPatientsInsertFrame().setVisible(true);
			Main.getPatientsMenuFrame().setEnabled(false);
		});
		patientsInsertBtn.setForeground(Color.BLUE);
		patientsInsertBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		patientsInsertBtn.setBounds(137, 105, 147, 50);
		contentPane.add(patientsInsertBtn);

		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(e -> {
			Main.getMainMenuFrame().setEnabled(true);
			Main.getPatientsMenuFrame().setVisible(false);
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		closeBtn.setBounds(324, 217, 102, 36);
		contentPane.add(closeBtn);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 185, 416, 1);
		contentPane.add(separator);
	}
}
