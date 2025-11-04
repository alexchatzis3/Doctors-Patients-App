package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorsPatientsDAOException;

import java.util.List;

/**
 * Service layer interface for managing associations between doctors and patients.
 * Provides methods to assign, remove, and retrieve patients of a specific doctor.
 */
public interface IDoctorsPatientsService {

    /**
     * Assigns a patient to a doctor.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    void assign(int doctorId, int patientId) throws DoctorsPatientsDAOException;

    /**
     * Removes a patient from a doctor.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    void remove(int doctorId, int patientId) throws DoctorsPatientsDAOException;

    /**
     * Retrieves all patients assigned to a specific doctor.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of String arrays containing patient information (ID, first name, last name).
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    List<String[]> getPatientsByDoctorId(int doctorId) throws DoctorsPatientsDAOException;
}
