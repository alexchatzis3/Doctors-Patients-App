package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.model.Doctor;

import java.util.List;

/**
 * Data Access Object (DAO) interface for Doctor entities.
 * Defines standard CRUD operations for interacting with the database.
 */
public interface IDoctorDAO {

    /**
     * Inserts a new doctor into the database.
     *
     * @param doctor The Doctor object to insert.
     * @return The inserted Doctor, usually with its generated ID.
     * @throws DoctorDAOException if a database error occurs.
     */
    Doctor insert(Doctor doctor) throws DoctorDAOException;

    /**
     * Updates an existing doctor in the database.
     *
     * @param doctor The Doctor object containing updated data.
     * @return The updated Doctor object.
     * @throws DoctorDAOException if a database error occurs.
     */
    Doctor update(Doctor doctor) throws DoctorDAOException;

    /**
     * Deletes a doctor from the database by ID.
     *
     * @param id The unique identifier of the doctor to delete.
     * @throws DoctorDAOException if a database error occurs.
     */
    void delete(Integer id) throws DoctorDAOException;

    /**
     * Retrieves a doctor from the database by ID.
     *
     * @param id The unique identifier of the doctor.
     * @return The Doctor object if found, otherwise null.
     * @throws DoctorDAOException if a database error occurs.
     */
    Doctor getById(Integer id) throws DoctorDAOException;

    /**
     * Retrieves a list of doctors matching the given last name.
     *
     * @param lastname The last name to search for.
     * @return A list of Doctor objects with the specified last name.
     * @throws DoctorDAOException if a database error occurs.
     */
    List<Doctor> getByLastname(String lastname) throws DoctorDAOException;
}
