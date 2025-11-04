package gr.aueb.cf.dpapp.viewcontroller;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import gr.aueb.cf.dpapp.Main;
import gr.aueb.cf.dpapp.dao.DoctorDAOImpl;
import gr.aueb.cf.dpapp.dao.IDoctorDAO;
import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.dto.DoctorInsertDTO;
import gr.aueb.cf.dpapp.dto.DoctorReadOnlyDTO;
import gr.aueb.cf.dpapp.model.Doctor;
import gr.aueb.cf.dpapp.service.DoctorServiceImpl;
import gr.aueb.cf.dpapp.service.IDoctorService;
import gr.aueb.cf.dpapp.validator.DoctorValidator;

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
 * The {@code DoctorsInsertFrame} class represents the graphical user interface (GUI)
 * used to input and insert a new doctor into the system.
 * <p>
 * The form allows the user to enter the doctor's firstname and lastname,
 * validates the input fields, displays validation errors, and interacts with
 * the {@link IDoctorService} to perform the database insertion.
 * </p>
 */
public class DoctorsInsertFrame extends JFrame {

	// Wiring
	private final IDoctorDAO doctorDAO = new DoctorDAOImpl();
	private final IDoctorService doctorService = new DoctorServiceImpl(doctorDAO);

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField firstnameText;
	private JTextField lastnameText;
	private JLabel errorFirstname;
	private JLabel errorLastname;

	/**
	 * Constructs the DoctorsInsertFrame window,
	 * initializing the layout, components and event listeners.
	 * <p>
	 * The form resets the text fields whenever the window becomes active.
	 * </p>
	 */
	public DoctorsInsertFrame() {
		setTitle("Insert New Doctor");
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
				String inputFirstname;

				inputFirstname = firstnameText.getText().trim();

				if (inputFirstname.equals("")) {
					errorFirstname.setText("Firstname is required");
				}

				if (!inputFirstname.equals("")) {
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

				String inputLastname;
				inputLastname = lastnameText.getText().trim();

				if (inputLastname.equals("")) {
					errorLastname.setText("Lastname is required");
				}

				if (!inputLastname.equals("")) {
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
				DoctorInsertDTO insertDTO = new DoctorInsertDTO();
				String firstnameMessage;
				String lastnameMessage;
				Doctor doctor;

				try {
					// Data binding
					insertDTO.setFirstname(firstnameText.getText().trim());
					insertDTO.setLastname(lastnameText.getText().trim());

					// Validation
					errors = DoctorValidator.validate(insertDTO);

					if (!errors.isEmpty()) {
						firstnameMessage = errors.getOrDefault("firstname", "");
						lastnameMessage = errors.containsKey("lastname") ? errors.get("lastname"): "";

						errorFirstname.setText(firstnameMessage);

						if (!lastnameMessage.isEmpty()) {
							errorLastname.setText(lastnameMessage);
						}

						if (lastnameMessage.isEmpty()) {
							errorLastname.setText("");
						}

						return;
					}

					doctor = doctorService.insertDoctor(insertDTO);
					DoctorReadOnlyDTO readOnlyDTO = mapToReadOnlyDTO(doctor);

					JOptionPane.showMessageDialog(null, "Doctor with lastname: "
							+ readOnlyDTO.getLastname() + " was successfully inserted.", "Insert Doctor", JOptionPane.INFORMATION_MESSAGE);
				} catch(DoctorDAOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(null, "Insertion Error", "Error", JOptionPane.ERROR_MESSAGE);
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
				Main.getDoctorsMenuFrame().setEnabled(true);
				Main.getDoctorsInsertFrame().setVisible(false);
			}
		});
		closeBtn.setForeground(Color.BLUE);
		closeBtn.setFont(new Font("Tahoma", Font.BOLD, 12));
		closeBtn.setBounds(322, 203, 104, 33);
		contentPane.add(closeBtn);

	}

	/**
	 * Converts a {@link Doctor} entity into a {@link DoctorReadOnlyDTO}
	 * to prevent unintended data modifications at presentation level.
	 *
	 * @param doctor The doctor entity after insertion.
	 * @return A read-only DTO with id, firstname, and lastname.
	 */
	private DoctorReadOnlyDTO mapToReadOnlyDTO(Doctor doctor) {
		return new DoctorReadOnlyDTO(doctor.getId(), doctor.getFirstname(), doctor.getLastname());
	}

}
