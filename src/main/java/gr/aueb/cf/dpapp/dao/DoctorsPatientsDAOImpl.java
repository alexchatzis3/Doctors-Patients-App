package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorsPatientsDAOException;
import gr.aueb.cf.dpapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the IDoctorsPatientsDAO interface using JDBC.
 * Provides operations for managing associations between doctors and patients.
 */
public class DoctorsPatientsDAOImpl implements IDoctorsPatientsDAO {

    /**
     * Inserts a doctor-patient association into the database.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    @Override
    public void insert(int doctorId, int patientId) throws DoctorsPatientsDAOException {
        String sql = "INSERT INTO doctorspatients (d_id, p_id) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.setInt(2, patientId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DoctorsPatientsDAOException("Error inserting doctor-patient link");
        }
    }

    /**
     * Deletes a doctor-patient association from the database.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    @Override
    public void delete(int doctorId, int patientId) throws DoctorsPatientsDAOException {
        String sql = "DELETE FROM doctorspatients WHERE d_id = ? AND p_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ps.setInt(2, patientId);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DoctorsPatientsDAOException("Error deleting doctor-patient link.");
        }
    }

    /**
     * Retrieves a list of patients associated with a given doctor ID.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of String arrays, each containing patient ID, first name, and last name.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    @Override
    public List<String[]> getPatientsByDoctorId(int doctorId) throws DoctorsPatientsDAOException {
        List<String[]> list = new ArrayList<>();

        String sql = """
                SELECT p.id, p.firstname, p.lastname
                FROM patients p
                JOIN doctorspatients dp ON p.id = dp.p_id
                WHERE dp.d_id = ?
                """;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, doctorId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(new String[]{
                        String.valueOf(rs.getInt("id")),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                });
            }

        } catch (SQLException e) {
            throw new DoctorsPatientsDAOException(
                    "Error retrieving patients list of doctor with id: " + doctorId
            );
        }
        return list;
    }
}
