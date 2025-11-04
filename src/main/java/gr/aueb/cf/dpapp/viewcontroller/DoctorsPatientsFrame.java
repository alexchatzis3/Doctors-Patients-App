package gr.aueb.cf.dpapp.viewcontroller;

import gr.aueb.cf.dpapp.Main;
import gr.aueb.cf.dpapp.dao.DoctorsPatientsDAOImpl;
import gr.aueb.cf.dpapp.dao.IDoctorsPatientsDAO;
import gr.aueb.cf.dpapp.dao.exceptions.DoctorsPatientsDAOException;
import gr.aueb.cf.dpapp.service.DoctorsPatientsServiceImpl;
import gr.aueb.cf.dpapp.service.IDoctorsPatientsService;
import gr.aueb.cf.dpapp.service.util.DBUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * A Swing frame that allows the management of doctor-patient relationships.
 * <p>
 * Users can:
 * <ul>
 *     <li>Assign a patient to a doctor</li>
 *     <li>Remove an existing doctor-patient relation</li>
 *     <li>View all patients linked to a selected doctor</li>
 * </ul>
 * </p>
 * <p>
 * The frame provides combo boxes for selecting doctors and patients and displays
 * the existing patients linked to the selected doctor in a table.
 * </p>
 */
public class DoctorsPatientsFrame extends JFrame {

    private final IDoctorsPatientsDAO doctorsPatientsDAO = new DoctorsPatientsDAOImpl();
    private final IDoctorsPatientsService doctorsPatientsService = new DoctorsPatientsServiceImpl(doctorsPatientsDAO);

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable doctorsPatientsTable;
    private DefaultTableModel tableModel;

    private JComboBox<String> doctorsCombo;
    private JComboBox<String> patientsCombo;
    private JButton assignBtn, removeBtn, closeBtn;

    private Map<String, Integer> doctorMap = new HashMap<>();
    private Map<String, Integer> patientMap = new HashMap<>();

    /**
     * Constructs the frame, initializes all UI components, and configures event listeners.
     */
    public DoctorsPatientsFrame() {
        setTitle("Doctors - Patients Links");
        setIconImage(Toolkit.getDefaultToolkit().getImage(
                Thread.currentThread().getContextClassLoader().getResource("medical.png")));

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setBounds(100, 100, 720, 420);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel doctorLabel = new JLabel("Doctor:");
        doctorLabel.setForeground(Color.BLUE);
        doctorLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        doctorLabel.setBounds(25, 15, 60, 20);
        contentPane.add(doctorLabel);

        JLabel patientLabel = new JLabel("Patient:");
        patientLabel.setForeground(Color.BLUE);
        patientLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
        patientLabel.setBounds(330, 15, 60, 20);
        contentPane.add(patientLabel);

        doctorsCombo = new JComboBox<>();
        doctorsCombo.setBounds(90, 15, 220, 22);
        contentPane.add(doctorsCombo);

        patientsCombo = new JComboBox<>();
        patientsCombo.setBounds(395, 15, 220, 22);
        contentPane.add(patientsCombo);

        assignBtn = new JButton("Assign");
        assignBtn.setBounds(25, 55, 120, 30);
        assignBtn.setForeground(Color.BLUE);
        assignBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(assignBtn);

        removeBtn = new JButton("Remove");
        removeBtn.setBounds(160, 55, 120, 30);
        removeBtn.setForeground(Color.BLUE);
        removeBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(removeBtn);

        closeBtn = new JButton("Close");
        closeBtn.setBounds(595, 340, 90, 30);
        closeBtn.setForeground(Color.BLUE);
        closeBtn.setFont(new Font("Tahoma", Font.BOLD, 13));
        contentPane.add(closeBtn);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(25, 100, 660, 220);
        contentPane.add(scrollPane);

        doctorsPatientsTable = new JTable();
        tableModel = new DefaultTableModel(new Object[]{"Patient ID", "Firstname", "Lastname"}, 0);
        doctorsPatientsTable.setModel(tableModel);
        scrollPane.setViewportView(doctorsPatientsTable);

        loadDoctors();
        reloadPatientsCombo();

        doctorsCombo.addActionListener(e -> buildTable());
        assignBtn.addActionListener(e -> assignDoctorToPatient());
        removeBtn.addActionListener(e -> removeSelectedLink());
        closeBtn.addActionListener(e -> {
            Main.getMainMenuFrame().setEnabled(true);
            Main.getMainMenuFrame().setVisible(true);
            Main.getDoctorsPatientsFrame().setVisible(false);
        });

        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowActivated(java.awt.event.WindowEvent e) {
                loadDoctors();
                reloadPatientsCombo();
                buildTable();
            }
        });
    }

    /**
     * Loads all doctors into the combo box and maps names to their IDs.
     */
    private void loadDoctors() {
        doctorMap.clear();
        doctorsCombo.removeAllItems();

        String sql = "SELECT id, firstname, lastname FROM doctors";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullname = rs.getString("firstname") + " " + rs.getString("lastname");
                doctorsCombo.addItem(fullname);
                doctorMap.put(fullname, id);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading doctors: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads all patients into the combo box and maps names to their IDs.
     */
    private void reloadPatientsCombo() {
        patientsCombo.removeAllItems();
        patientMap.clear();

        String sql = "SELECT id, firstname, lastname FROM patients ORDER BY firstname, lastname";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String fullname = rs.getString("firstname") + " " + rs.getString("lastname");
                patientsCombo.addItem(fullname);
                patientMap.put(fullname, id);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading patients: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Builds the patients table for the selected doctor.
     * Fetches all patients linked to the doctor from the database.
     */
    private void buildTable() {
        tableModel.setRowCount(0); // clear previous rows

        String selectedDoctor = (String) doctorsCombo.getSelectedItem();
        if (selectedDoctor == null) return;

        Integer doctorId = doctorMap.get(selectedDoctor);
        if (doctorId == null) return;

        String sql = """
                SELECT p.id, p.firstname, p.lastname
                FROM patients p
                JOIN doctorspatients dp ON p.id = dp.p_id
                WHERE dp.d_id = ?
                ORDER BY p.firstname, p.lastname
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("id"));
                row.add(rs.getString("firstname"));
                row.add(rs.getString("lastname"));
                tableModel.addRow(row);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading patients: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Assigns the selected patient to the selected doctor.
     * Displays feedback messages and refreshes the table and combo boxes.
     */
    private void assignDoctorToPatient() {
        String selectedDoctor = (String) doctorsCombo.getSelectedItem();
        String selectedPatient = (String) patientsCombo.getSelectedItem();

        if (selectedDoctor == null || selectedPatient == null) {
            JOptionPane.showMessageDialog(this, "Select both doctor and patient.",
                    "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int doctorId = doctorMap.get(selectedDoctor);
        int patientId = patientMap.get(selectedPatient);

        try {
            doctorsPatientsService.assign(doctorId, patientId);
            JOptionPane.showMessageDialog(this, "Doctor assigned successfully!");
            buildTable();
            reloadPatientsCombo();
        } catch (DoctorsPatientsDAOException e) {
            JOptionPane.showMessageDialog(null, "Error assigning: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Removes the doctor-patient relation for the selected patient in the table.
     */
    private void removeSelectedLink() {
        int row = doctorsPatientsTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Select a patient to remove.");
            return;
        }

        String selectedDoctor = (String) doctorsCombo.getSelectedItem();
        if (selectedDoctor == null) return;

        int doctorId = doctorMap.get(selectedDoctor);
        int patientId = (int) tableModel.getValueAt(row, 0);

        try {
            doctorsPatientsService.remove(doctorId, patientId);
            JOptionPane.showMessageDialog(this, "Relation removed successfully!");
            buildTable();
            reloadPatientsCombo();
        } catch (DoctorsPatientsDAOException e) {
            JOptionPane.showMessageDialog(this, "Error removing relation: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
