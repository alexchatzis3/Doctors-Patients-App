package gr.aueb.cf.dpapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.dpapp.Main;
import gr.aueb.cf.dpapp.dao.IPatientDAO;
import gr.aueb.cf.dpapp.dao.PatientDAOImpl;
import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.dto.PatientInsertDTO;
import gr.aueb.cf.dpapp.dto.PatientReadOnlyDTO;
import gr.aueb.cf.dpapp.model.Patient;
import gr.aueb.cf.dpapp.service.IPatientService;
import gr.aueb.cf.dpapp.service.PatientServiceImpl;
import gr.aueb.cf.dpapp.validator.PatientValidator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Toolkit;
import javax.swing.border.BevelBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;

/**
 * The {@code PatientsInsertFrame} class implements a Swing form
 * for inserting new patient records into the system.
 * <p>
 * The form provides input fields for firstname and lastname,
 * performs validation through {@link PatientValidator}, and,
 * if validation is successful, inserts the patient using
 * {@link IPatientService}.
 * </p>
 * <p>
 * Validation messages are displayed under the corresponding fields.
 * </p>
 */
public class PatientsInsertFrame extends JFrame {

	// Wiring Dependencies
	private final IPatientDAO patientDAO = new PatientDAOImpl();
	private final IPatientService patientService = new PatientServiceImpl(patientDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameText;
	private JTextField lastnameText;
	private JLabel errorFirstname;
	private JLabel errorLastname;

	/**
	 * Constructs and initializes the patient Insert Frame GUI.
	 * When the window becomes active, all text fields are cleared.
	 */
	public PatientsInsertFrame() {
		setTitle("Insert New Patient");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent e) {
				firstnameText.setText("");
				lastnameText.setText("");
			}
		});
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("medical.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 10, 416, 141);
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel firstnameLabel = new JLabel("Firstname");
		firstnameLabel.setBounds(21, 34, 51, 21);
		panel.add(firstnameLabel);
		firstnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		firstnameLabel.setForeground(new Color(0, 0, 255));
		firstnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

		firstnameText = new JTextField();
		firstnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputFirstname = firstnameText.getText().trim();
				if (inputFirstname.equals("")) {
					errorFirstname.setText("Firstname is required");
				} else {
					errorFirstname.setText("");
				}
			}
		});
		firstnameText.setBounds(82, 35, 212, 19);
		panel.add(firstnameText);
		firstnameText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		firstnameText.setColumns(10);

		JLabel lastnameLabel = new JLabel("Lastname");
		lastnameLabel.setBounds(21, 81, 51, 21);
		panel.add(lastnameLabel);
		lastnameLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lastnameLabel.setForeground(Color.BLUE);
		lastnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));

		lastnameText = new JTextField();
		lastnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				String inputLastname = lastnameText.getText().trim();
				if (inputLastname.equals("")) {
					errorLastname.setText("Lastname is required");
				} else {
					errorLastname.setText("");
				}
			}
		});
		lastnameText.setBounds(82, 83, 212, 19);
		panel.add(lastnameText);
		lastnameText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lastnameText.setColumns(10);

		errorFirstname = new JLabel("");
		errorFirstname.setForeground(new Color(255, 0, 0));
		errorFirstname.setFont(new Font("Tahoma", Font.PLAIN, 8));
		errorFirstname.setBounds(82, 52, 212, 19);
		panel.add(errorFirstname);

		errorLastname = new JLabel("");
		errorLastname.setForeground(Color.RED);
		errorLastname.setFont(new Font("Tahoma", Font.PLAIN, 8));
		errorLastname.setBounds(82, 100, 212, 19);
		panel.add(errorLastname);

		JButton insertBtn = new JButton("Insert");
		insertBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Map<String, String> errors;
				PatientInsertDTO insertDTO = new PatientInsertDTO();
				Patient patient;
				String firstnameMessage;
				String lastnameMessage;

				try {
					// Data binding
					insertDTO.setFirstname(firstnameText.getText().trim());
					insertDTO.setLastname(lastnameText.getText().trim());

					// Validation
					errors = PatientValidator.validate(insertDTO);

					if (!errors.isEmpty()) {
						firstnameMessage = errors.getOrDefault("firstname", "");
						lastnameMessage = errors.getOrDefault("lastname", "");

						errorFirstname.setText(firstnameMessage);
						errorLastname.setText(lastnameMessage);

						return;
					}

					patient = patientService.insertPatient(insertDTO);
					PatientReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(patient);

					JOptionPane.showMessageDialog(null,
							"Patient with lastname " + readOnlyDTO.getLastname() + " was successfully inserted.",
							"Insert", JOptionPane.INFORMATION_MESSAGE);

				} catch (PatientDAOException ex) {
					JOptionPane.showMessageDialog(null,
							"Insertion Error",
							"ERROR",
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		insertBtn.setForeground(new Color(0, 0, 255));
		insertBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		insertBtn.setBounds(192, 203, 104, 33);
		contentPane.add(insertBtn);

		JButton closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getPatientsMenuFrame().setEnabled(true);
				Main.getPatientsInsertFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		closeBtn.setBounds(322, 203, 104, 33);
		contentPane.add(closeBtn);

	}

	/**
	 * Converts a {@link Patient} model to a read-only DTO suitable for display.
	 *
	 * @param patient the Patient entity
	 * @return a {@link PatientReadOnlyDTO} containing patient id, firstname, lastname
	 */
	private PatientReadOnlyDTO mapToReadOnlyDTO(Patient patient) {
		return new PatientReadOnlyDTO(patient.getId(), patient.getFirstname(), patient.getLastname());
	}

}
