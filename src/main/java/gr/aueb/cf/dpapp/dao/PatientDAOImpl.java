package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.model.Patient;
import gr.aueb.cf.dpapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the IPatientDAO interface using JDBC.
 * Provides CRUD operations for Patient entities in the database.
 */
public class PatientDAOImpl implements IPatientDAO {

    /**
     * Inserts a new patient into the database.
     *
     * @param patient The Patient object to insert.
     * @return The inserted Patient object.
     * @throws PatientDAOException if a database error occurs.
     */
    @Override
    public Patient insert(Patient patient) throws PatientDAOException {
        String sql = "INSERT INTO patients (firstname, lastname) VALUES (?, ?)";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getFirstname());
            ps.setString(2, patient.getLastname());

            ps.executeUpdate();
            return patient;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PatientDAOException("SQL Error in insert patient: " + patient);
        }
    }

    /**
     * Updates an existing patient in the database.
     *
     * @param patient The Patient object with updated information.
     * @return The updated Patient object.
     * @throws PatientDAOException if a database error occurs.
     */
    @Override
    public Patient update(Patient patient) throws PatientDAOException {
        String sql = "UPDATE patients SET firstname = ?, lastname = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, patient.getFirstname());
            ps.setString(2, patient.getLastname());
            ps.setInt(3, patient.getId());

            ps.executeUpdate();
            return patient;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PatientDAOException("SQL Error in update patient: " + patient);
        }
    }

    /**
     * Deletes a patient from the database by ID.
     *
     * @param id The unique identifier of the patient to delete.
     * @throws PatientDAOException if a database error occurs.
     */
    @Override
    public void delete(Integer id) throws PatientDAOException {
        String sql = "DELETE FROM patients WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PatientDAOException("SQL Error in delete patient with id: " + id);
        }
    }

    /**
     * Retrieves a patient by their unique ID.
     *
     * @param id The unique identifier of the patient.
     * @return The Patient object if found, otherwise null.
     * @throws PatientDAOException if a database error occurs.
     */
    @Override
    public Patient getById(Integer id) throws PatientDAOException {
        String sql = "SELECT * FROM patients WHERE id = ?";
        Patient patient = null;

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                patient = new Patient(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                );
            }
            return patient;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PatientDAOException("SQL Error in getById with id: " + id);
        }
    }

    /**
     * Retrieves a list of patients whose last names start with the given string.
     *
     * @param lastname The last name to search for (prefix match).
     * @return A list of matching Patient objects.
     * @throws PatientDAOException if a database error occurs.
     */
    @Override
    public List<Patient> getByLastname(String lastname) throws PatientDAOException {
        String sql = "SELECT * FROM patients WHERE lastname LIKE ?";
        List<Patient> patients = new ArrayList<>();

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, lastname + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                patients.add(new Patient(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                ));
            }
            return patients;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new PatientDAOException("SQL Error in getByLastname with lastname: " + lastname);
        }
    }
}
