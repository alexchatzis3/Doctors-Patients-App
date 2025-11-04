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
import gr.aueb.cf.dpapp.dao.DoctorDAOImpl;
import gr.aueb.cf.dpapp.dao.IDoctorDAO;
import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.dto.DoctorReadOnlyDTO;
import gr.aueb.cf.dpapp.dto.DoctorUpdateDTO;
import gr.aueb.cf.dpapp.model.Doctor;
import gr.aueb.cf.dpapp.service.DoctorServiceImpl;
import gr.aueb.cf.dpapp.service.IDoctorService;
import gr.aueb.cf.dpapp.service.exceptions.DoctorNotFoundException;
import gr.aueb.cf.dpapp.validator.DoctorValidator;

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
 * The {@code DoctorsUpdateDeleteFrame} class implements a Swing-based GUI that allows
 * users to search, select, update, and delete doctor records.
 * <p>
 * The search is performed by lastname. Once a doctor is selected from the table,
 * their firstname and lastname can be modified. The user may also delete the selected doctor.
 * </p>
 * <p>
 * This class communicates with the service layer ({@link IDoctorService})
 * and performs validation through {@link DoctorValidator}.
 * Error messages appear directly under the input fields.
 * </p>
 */
public class DoctorsUpdateDeleteFrame extends JFrame {

	// Wiring - Dependency injection
	private final IDoctorDAO doctorDAO = new DoctorDAOImpl();
	private final IDoctorService doctorService = new DoctorServiceImpl(doctorDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTable doctorsTable;
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
	 * Constructs the frame and initializes the GUI components.
	 * The table is refreshed each time the window is opened or activated.
	 */
	public DoctorsUpdateDeleteFrame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				lastnameSearchText.setText("");
				buildTable();
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
			@Override
			public void windowActivated(WindowEvent e) {
				lastnameSearchText.setText("");
				buildTable();
				idText.setText("");
				firstnameText.setText("");
				lastnameText.setText("");
			}
		});
		setTitle("Update / Delete Doctor");
		setIconImage(Toolkit.getDefaultToolkit().getImage(Thread.currentThread().getContextClassLoader().getResource("medical.png")));
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 682, 376);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		doctorsTable = new JTable();
		doctorsTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				idText.setText((String) model.getValueAt(doctorsTable.getSelectedRow(), 0));
				firstnameText.setText((String) model.getValueAt(doctorsTable.getSelectedRow(), 1));
				lastnameText.setText((String) model.getValueAt(doctorsTable.getSelectedRow(), 2));
			}
		});
		doctorsTable.setModel(new DefaultTableModel(
				new Object[][] {},
				new String[] {"ID", "Firstname", "Lastname"}
		));

		model = (DefaultTableModel) doctorsTable.getModel();

		JScrollPane scrollPane = new JScrollPane(doctorsTable);
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
				Doctor doctor;

				if (idText.getText().trim().isEmpty()) return;

				try {
					DoctorUpdateDTO updateDTO = new DoctorUpdateDTO();
					updateDTO.setId(Integer.parseInt(idText.getText().trim()));
					updateDTO.setFirstname(firstnameText.getText().trim());
					updateDTO.setLastname(lastnameText.getText().trim());

					errors = DoctorValidator.validate(updateDTO);

					if (!errors.isEmpty()) {
						firstnameMessage = errors.getOrDefault("firstname", "");
						lastnameMessage = errors.getOrDefault("lastname", "");

						errorFirstname.setText(firstnameMessage);
						errorLastname.setText(lastnameMessage);

						return;
					}

					doctor = doctorService.updateDoctor(updateDTO);

					DoctorReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(doctor);

					JOptionPane.showMessageDialog(null, "Doctor with id: " + readOnlyDTO.getId() + " was updated.", "Update",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (DoctorDAOException | DoctorNotFoundException ex) {
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
				int response;

				try {
					if (idText.getText().trim().isEmpty()) return;
					int inputId = Integer.parseInt(idText.getText().trim());

					response = JOptionPane.showConfirmDialog(null, "Are you sure?", "Warning", JOptionPane.YES_NO_OPTION);
					if (response == JOptionPane.YES_OPTION) {
						doctorService.deleteDoctor(inputId);
						JOptionPane.showMessageDialog(null, "Doctor was deleted successfully",
								"Delete", JOptionPane.INFORMATION_MESSAGE);
					}
				} catch (DoctorDAOException | DoctorNotFoundException ex) {
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
				Main.getDoctorsMenuFrame().setEnabled(true);
				Main.getDoctorsUpdateDeleteFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(new Color(0, 0, 255));
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
		closeBtn.setBounds(560, 291, 98, 38);
		contentPane.add(closeBtn);

	}

	/**
	 * Loads doctors filtered by lastname into the table. If the search field is empty,
	 * all doctors are displayed.
	 */
	private void buildTable() {

		Vector<String> vector;
		List<DoctorReadOnlyDTO> readOnlyDTOS = new ArrayList<>();
		DoctorReadOnlyDTO readOnlyDTO;

		try{
			String searchStr = lastnameSearchText.getText().trim();

			List<Doctor> doctors = doctorService.getDoctorsByLastname(searchStr);

			for (Doctor doctor: doctors) {
				readOnlyDTO = mapToReadOnlyDTO(doctor);
				readOnlyDTOS.add(readOnlyDTO);
			}

			for (int i = model.getRowCount() - 1; i >= 0; i--) {
				model.removeRow(i);
			}

			for (DoctorReadOnlyDTO doctorReadOnlyDTO : readOnlyDTOS) {
				vector = new Vector<>(3);
				vector.add(String.valueOf(doctorReadOnlyDTO.getId()));
				vector.add(doctorReadOnlyDTO.getFirstname());
				vector.add(doctorReadOnlyDTO.getLastname());
				model.addRow(vector);
			}

		} catch (DoctorDAOException ex) {
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

	/**
	 * Validates the firstname and displays an error text if the field is empty.
	 *
	 * @param inputFirstname the firstname typed by the user
	 */
	private void validateFirstname(String inputFirstname) {
		if (inputFirstname.equals("")) {
			errorFirstname.setText("Firstname is required");
		} else {
			errorFirstname.setText("");
		}
	}

	/**
	 * Validates the lastname and displays an error text if the field is empty.
	 *
	 * @param inputLastname the lastname typed by the user
	 */
	private void validateLastname(String inputLastname) {
		if (inputLastname.equals("")) {
			errorLastname.setText("Lastname is required");
		} else {
			errorLastname.setText("");
		}
	}

	/**
	 * Converts a Doctor model to a read-only DTO for display purposes.
	 *
	 * @param doctor the doctor model object
	 * @return a {@link DoctorReadOnlyDTO} containing id, firstname, lastname
	 */
	private DoctorReadOnlyDTO mapToReadOnlyDTO(Doctor doctor) {
		return new DoctorReadOnlyDTO(doctor.getId(), doctor.getFirstname(), doctor.getLastname());
	}
}
