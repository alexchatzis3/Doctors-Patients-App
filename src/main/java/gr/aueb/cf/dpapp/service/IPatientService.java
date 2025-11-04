package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.dto.PatientInsertDTO;
import gr.aueb.cf.dpapp.dto.PatientUpdateDTO;
import gr.aueb.cf.dpapp.model.Patient;
import gr.aueb.cf.dpapp.service.exceptions.PatientNotFoundException;

import java.util.List;

/**
 * Service layer interface for Patient-related business operations.
 * Provides methods for creating, updating, deleting, and retrieving patients.
 */
public interface IPatientService {

    /**
     * Inserts a new patient using the provided DTO.
     *
     * @param dto The PatientInsertDTO containing first name and last name.
     * @return The inserted Patient object.
     * @throws PatientDAOException if a database error occurs.
     */
    Patient insertPatient(PatientInsertDTO dto) throws PatientDAOException;

    /**
     * Updates an existing patient using the provided DTO.
     *
     * @param dto The PatientUpdateDTO containing the ID and updated data.
     * @return The updated Patient object.
     * @throws PatientDAOException if a database error occurs.
     * @throws PatientNotFoundException if the patient does not exist.
     */
    Patient updatePatient(PatientUpdateDTO dto) throws PatientDAOException, PatientNotFoundException;

    /**
     * Deletes a patient by ID.
     *
     * @param id The unique identifier of the patient to delete.
     * @throws PatientDAOException if a database error occurs.
     * @throws PatientNotFoundException if the patient does not exist.
     */
    void deletePatient(Integer id) throws PatientDAOException, PatientNotFoundException;

    /**
     * Retrieves a patient by ID.
     *
     * @param id The unique identifier of the patient.
     * @return The Patient object if found.
     * @throws PatientDAOException if a database error occurs.
     * @throws PatientNotFoundException if the patient does not exist.
     */
    Patient getPatientById(Integer id) throws PatientDAOException, PatientNotFoundException;

    /**
     * Retrieves a list of patients whose last names match the given string.
     *
     * @param lastname The last name to search for (prefix match).
     * @return A list of matching Patient objects.
     * @throws PatientDAOException if a database error occurs.
     */
    List<Patient> getPatientsByLastname(String lastname) throws PatientDAOException;
}
