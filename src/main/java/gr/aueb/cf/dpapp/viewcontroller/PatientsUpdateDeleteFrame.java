package gr.aueb.cf.dpapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import gr.aueb.cf.dpapp.Main;
import gr.aueb.cf.dpapp.dao.IPatientDAO;
import gr.aueb.cf.dpapp.dao.PatientDAOImpl;
import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.dto.PatientReadOnlyDTO;
import gr.aueb.cf.dpapp.dto.PatientUpdateDTO;
import gr.aueb.cf.dpapp.model.Patient;
import gr.aueb.cf.dpapp.service.IPatientService;
import gr.aueb.cf.dpapp.service.PatientServiceImpl;
import gr.aueb.cf.dpapp.service.exceptions.PatientNotFoundException;
import gr.aueb.cf.dpapp.validator.PatientValidator;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.border.BevelBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

/**
 * The {@code PatientsUpdateDeleteFrame} class implements a Swing frame
 * for updating or deleting existing patients in the system.
 * <p>
 * The frame displays a table of patients searchable by lastname.
 * Selecting a patient loads their information into editable fields.
 * The user can update or delete the selected patient.
 * </p>
 * <p>
 * Validation is performed via {@link PatientValidator} and any errors
 * are displayed next to the corresponding input fields.
 * </p>
 */
public class PatientsUpdateDeleteFrame extends JFrame {

	// Wiring Dependencies
	private final IPatientDAO patientDAO = new PatientDAOImpl();
	private final IPatientService patientService = new PatientServiceImpl(patientDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable patientsTable;
	private DefaultTableModel model = new DefaultTableModel();
	private JLabel lastnameSearchLabel;
	private JTextField lastnameSearchText;
	private JButton btnSearch;
	private JLabel idLabel;
	private JTextField idText;
	private JLabel firstnameLabel;
	private JTextField firstnameText;
	private JLabel lastnameLabel;
	private JTextField lastnameText;
	private JPanel panel;
	private JLabel errorFirstname;
	private JLabel errorLastname;
	private JButton updateBtn;
	private JButton deleteBtn;
	private JButton closeBtn;

	/**
	 * Constructs the Patients Update/Delete Frame.
	 * Initializes all UI components and configures event listeners.
	 */
	public PatientsUpdateDeleteFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lastnameSearchText.setText("");
				buildTable(); // initial rendering
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
			@Override
			public void windowActivated(WindowEvent e) {
				lastnameSearchText.setText("");
				buildTable(); // refresh after update/delete
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
		});

		setTitle("Update / Delete Patient");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("medical.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 682, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		patientsTable = new JTable();
		patientsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				idText.setText((String) model.getValueAt(patientsTable.getSelectedRow(), 0));
				firstnameText.setText((String) model.getValueAt(patientsTable.getSelectedRow(), 1));
				lastnameText.setText((String) model.getValueAt(patientsTable.getSelectedRow(), 2));
			}
		});

		patientsTable.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"ID", "Firstname", "Lastname"}
		));

		model = (DefaultTableModel) patientsTable.getModel();
		JScrollPane scrollPane = new JScrollPane(patientsTable);
		scrollPane.setBounds(25, 45, 348, 284);
		contentPane.add(scrollPane);

		lastnameSearchLabel = new JLabel("Lastname");
		lastnameSearchLabel.setForeground(new Color(128, 0, 0));
		lastnameSearchLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		lastnameSearchLabel.setBounds(25, 10, 69, 12);
		contentPane.add(lastnameSearchLabel);

		lastnameSearchText = new JTextField();
		lastnameSearchText.setBounds(104, 7, 154, 18);
		contentPane.add(lastnameSearchText);
		lastnameSearchText.setColumns(10);

		btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				buildTable();
			}
		});
		btnSearch.setForeground(new Color(0, 0, 255));
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSearch.setBounds(289, 6, 84, 20);
		contentPane.add(btnSearch);

		idLabel = new JLabel("Id");
		idLabel.setForeground(new Color(0, 0, 255));
		idLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		idLabel.setBounds(409, 65, 44, 12);
		contentPane.add(idLabel);

		idText = new JTextField();
		idText.setEditable(false);
		idText.setBounds(483, 62, 51, 18);
		contentPane.add(idText);
		idText.setColumns(10);

		firstnameLabel = new JLabel("Firstname");
		firstnameLabel.setForeground(new Color(0, 0, 255));
		firstnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		firstnameLabel.setBounds(409, 99, 64, 12);
		contentPane.add(firstnameLabel);

		firstnameText = new JTextField();
		firstnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validateFirstname(firstnameText.getText().trim());
			}
		});
		firstnameText.setBounds(483, 96, 154, 18);
		contentPane.add(firstnameText);
		firstnameText.setColumns(10);

		lastnameLabel = new JLabel("Lastname");
		lastnameLabel.setForeground(new Color(0, 0, 255));
		lastnameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lastnameLabel.setBounds(409, 141, 64, 12);
		contentPane.add(lastnameLabel);

		lastnameText = new JTextField();
		lastnameText.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				validateLastname(lastnameText.getText().trim());
			}
		});
		lastnameText.setBounds(483, 138, 154, 18);
		contentPane.add(lastnameText);
		lastnameText.setColumns(10);

		panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(383, 45, 275, 159);
		contentPane.add(panel);
		panel.setLayout(null);

		errorFirstname = new JLabel("");
		errorFirstname.setForeground(new Color(255, 0, 0));
		errorFirstname.setFont(new Font("Tahoma", Font.PLAIN, 8));
		errorFirstname.setBounds(100, 72, 154, 18);
		panel.add(errorFirstname);

		errorLastname = new JLabel("");
		errorLastname.setForeground(new Color(255, 0, 0));
		errorLastname.setFont(new Font("Tahoma", Font.PLAIN, 8));
		errorLastname.setBounds(100, 113, 154, 18);
		panel.add(errorLastname);

		updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Map<String, String> errors;
				String firstnameMessage;
				String lastnameMessage;
				Patient patient;

				if (idText.getText().trim().isEmpty()) return;

				try {
					PatientUpdateDTO updateDTO = new PatientUpdateDTO();
					updateDTO.setId(Integer.parseInt(idText.getText().trim()));
					updateDTO.setFirstname(firstnameText.getText().trim());
					updateDTO.setLastname(lastnameText.getText().trim());

					errors = PatientValidator.validate(updateDTO);

					if (!errors.isEmpty()) {
						firstnameMessage = errors.getOrDefault("firstname", "");
						lastnameMessage = errors.getOrDefault("lastname", "");

						errorFirstname.setText(firstnameMessage);
						errorLastname.setText(lastnameMessage);
						return;
					}

					patient = patientService.updatePatient(updateDTO);
					PatientReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(patient);

					JOptionPane.showMessageDialog(null,
							"Patient with id: " + readOnlyDTO.getId() + " was updated.",
							"Update", JOptionPane.INFORMATION_MESSAGE);

				} catch (PatientDAOException | PatientNotFoundException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		updateBtn.setForeground(new Color(0, 0, 255));
		updateBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		updateBtn.setBounds(383, 229, 98, 38);
		contentPane.add(updateBtn);

		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (idText.getText().trim().isEmpty()) return;
					int inputId = Integer.parseInt(idText.getText().trim());

					int response = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						patientService.deletePatient(inputId);
						JOptionPane.showMessageDialog(null, "Patient was deleted successfully",
								"Delete", JOptionPane.INFORMATION_MESSAGE);
					}

				} catch (PatientDAOException | PatientNotFoundException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		deleteBtn.setForeground(Color.BLUE);
		deleteBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		deleteBtn.setBounds(560, 229, 98, 38);
		contentPane.add(deleteBtn);

		closeBtn = new JButton("Close");
		closeBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Main.getPatientsMenuFrame().setEnabled(true);
				Main.getPatientsUpdateDeleteFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		closeBtn.setBounds(560, 291, 98, 38);
		contentPane.add(closeBtn);
	}

	/**
	 * Builds the patients table by retrieving all patients filtered by lastname.
	 */
	private void buildTable() {
		Vector<String> vector;
		List<PatientReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
		PatientReadOnlyDTO readOnlyDTO;

		try {
			String searchStr = lastnameSearchText.getText().trim();
			List<Patient> patients = patientService.getPatientsByLastname(searchStr);

			for (Patient patient : patients) {
				readOnlyDTO = mapToReadOnlyDTO(patient);
				readOnlyDTOS.add(readOnlyDTO);
			}

			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}

			for (PatientReadOnlyDTO patientReadOnlyDTO : readOnlyDTOS) {
				vector = new Vector<>(3);
				vector.add(String.valueOf(patientReadOnlyDTO.getId()));
				vector.add(patientReadOnlyDTO.getFirstname());
				vector.add(patientReadOnlyDTO.getLastname());
				model.addRow(vector);
			}

		} catch (PatientDAOException ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Validates the firstname input field and displays an error message if empty.
	 *
	 * @param inputFirstname the value to validate
	 */
	private void validateFirstname(String inputFirstname) {
		if (inputFirstname.equals("")) {
			errorFirstname.setText("Firstname is required");
		} else {
			errorFirstname.setText("");
		}
	}

	/**
	 * Validates the lastname input field and displays an error message if empty.
	 *
	 * @param inputLastname the value to validate
	 */
	private void validateLastname(String inputLastname) {
		if (inputLastname.equals("")) {
			errorLastname.setText("Lastname is required");
		} else {
			errorLastname.setText("");
		}
	}

	/**
	 * Maps a Patient entity to a read-only DTO for display purposes.
	 *
	 * @param patient the Patient entity
	 * @return the PatientReadOnlyDTO
	 */
	private PatientReadOnlyDTO mapToReadOnlyDTO(Patient patient) {
		return new PatientReadOnlyDTO(patient.getId(), patient.getFirstname(), patient.getLastname());
	}
}
