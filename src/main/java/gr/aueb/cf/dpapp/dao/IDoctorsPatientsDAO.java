package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorsPatientsDAOException;

import java.util.List;

/**
 * Data Access Object (DAO) interface for managing the association
 * between doctors and patients.
 * Defines methods to insert, delete, and query doctor-patient relationships.
 */
public interface IDoctorsPatientsDAO {

    /**
     * Inserts a doctor-patient association into the database.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    void insert(int doctorId, int patientId) throws DoctorsPatientsDAOException;

    /**
     * Deletes a doctor-patient association from the database.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    void delete(int doctorId, int patientId) throws DoctorsPatientsDAOException;

    /**
     * Retrieves all patients associated with a given doctor ID.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of String arrays containing patient information.
     *         Each array may include patient ID, first name, and last name.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    List<String[]> getPatientsByDoctorId(int doctorId) throws DoctorsPatientsDAOException;
}
