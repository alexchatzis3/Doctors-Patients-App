package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.model.Doctor;
import gr.aueb.cf.dpapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of the IDoctorDAO interface using JDBC.
 * Provides CRUD operations for Doctor entities in the database.
 */
public class DoctorDAOImpl implements IDoctorDAO {

    /**
     * Inserts a new doctor into the database.
     *
     * @param doctor The Doctor object to insert.
     * @return The inserted Doctor object.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public Doctor insert(Doctor doctor) throws DoctorDAOException {
        String sql = "INSERT INTO doctors (firstname, lastname) VALUES (?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, doctor.getFirstname());
            ps.setString(2, doctor.getLastname());

            ps.executeUpdate();
            return doctor; // Can be enhanced to return generated ID

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DoctorDAOException("SQL error in insert doctor: " + doctor);
        }
    }

    /**
     * Updates an existing doctor in the database.
     *
     * @param doctor The Doctor object with updated information.
     * @return The updated Doctor object.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public Doctor update(Doctor doctor) throws DoctorDAOException {
        String sql = "UPDATE doctors SET firstname = ?, lastname = ? WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, doctor.getFirstname());
            ps.setString(2, doctor.getLastname());
            ps.setInt(3, doctor.getId());

            ps.executeUpdate();
            return doctor;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DoctorDAOException("SQL error in update doctor: " + doctor);
        }
    }

    /**
     * Deletes a doctor from the database by ID.
     *
     * @param id The unique identifier of the doctor to delete.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public void delete(Integer id) throws DoctorDAOException {
        String sql = "DELETE FROM doctors WHERE id = ?";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DoctorDAOException("SQL error in delete doctor with doctor id: " + id);
        }
    }

    /**
     * Retrieves a doctor by their unique ID.
     *
     * @param id The unique identifier of the doctor.
     * @return The Doctor object if found, otherwise null.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public Doctor getById(Integer id) throws DoctorDAOException {
        String sql = "SELECT * FROM doctors WHERE id = ?";
        Doctor doctor = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                doctor = new Doctor(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                );
            }
            return doctor;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DoctorDAOException("SQL error in getById with id: " + id);
        }
    }

    /**
     * Retrieves a list of doctors whose last names start with the given string.
     *
     * @param lastname The last name to search for (prefix match).
     * @return A list of matching Doctor objects.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public List<Doctor> getByLastname(String lastname) throws DoctorDAOException {
        String sql = "SELECT * FROM doctors WHERE lastname LIKE ?";
        List<Doctor> doctors = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, lastname + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                doctors.add(new Doctor(
                        rs.getInt("id"),
                        rs.getString("firstname"),
                        rs.getString("lastname")
                ));
            }
            return doctors;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DoctorDAOException("SQL error in getByLastname with lastname: " + lastname);
        }
    }
}
