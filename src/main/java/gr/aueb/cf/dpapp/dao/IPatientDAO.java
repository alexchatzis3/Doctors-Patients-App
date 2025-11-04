package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.model.Patient;

import java.util.List;

/**
 * Data Access Object (DAO) interface for Patient entities.
 * Defines standard CRUD operations for interacting with the database.
 */
public interface IPatientDAO {

    /**
     * Inserts a new patient into the database.
     *
     * @param patient The Patient object to insert.
     * @return The inserted Patient, usually with its generated ID.
     * @throws PatientDAOException if a database error occurs.
     */
    Patient insert(Patient patient) throws PatientDAOException;

    /**
     * Updates an existing patient in the database.
     *
     * @param patient The Patient object containing updated data.
     * @return The updated Patient object.
     * @throws PatientDAOException if a database error occurs.
     */
    Patient update(Patient patient) throws PatientDAOException;

    /**
     * Deletes a patient from the database by ID.
     *
     * @param id The unique identifier of the patient to delete.
     * @throws PatientDAOException if a database error occurs.
     */
    void delete(Integer id) throws PatientDAOException;

    /**
     * Retrieves a patient from the database by ID.
     *
     * @param id The unique identifier of the patient.
     * @return The Patient object if found, otherwise null.
     * @throws PatientDAOException if a database error occurs.
     */
    Patient getById(Integer id) throws PatientDAOException;

    /**
     * Retrieves a list of patients matching the given last name.
     *
     * @param lastname The last name to search for.
     * @return A list of Patient objects with the specified last name.
     * @throws PatientDAOException if a database error occurs.
     */
    List<Patient> getByLastname(String lastname) throws PatientDAOException;
}
