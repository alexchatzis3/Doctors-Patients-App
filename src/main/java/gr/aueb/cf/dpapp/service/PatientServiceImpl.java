package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.IPatientDAO;
import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.dto.PatientInsertDTO;
import gr.aueb.cf.dpapp.dto.PatientUpdateDTO;
import gr.aueb.cf.dpapp.model.Patient;
import gr.aueb.cf.dpapp.service.exceptions.PatientNotFoundException;

import java.util.List;

/**
 * Implementation of the IPatientService interface.
 * Handles business logic for Patient entities, including validation
 * and mapping between DTOs and model entities.
 */
public class PatientServiceImpl implements IPatientService {

    /** DAO instance for performing database operations on Patient entities. */
    private final IPatientDAO patientDAO;

    /**
     * Constructor with Dependency Injection of the Patient DAO.
     *
     * @param patientDAO The IPatientDAO implementation to be used.
     */
    public PatientServiceImpl(IPatientDAO patientDAO) {
        this.patientDAO = patientDAO;
    }

    /**
     * Inserts a new patient using a DTO.
     *
     * @param dto The PatientInsertDTO containing first and last name.
     * @return The inserted Patient object.
     * @throws PatientDAOException if a database error occurs.
     */
    @Override
    public Patient insertPatient(PatientInsertDTO dto) throws PatientDAOException {
        Patient patient = mapToPatient(dto);
        return patientDAO.insert(patient);
    }

    /**
     * Updates an existing patient using a DTO.
     *
     * @param dto The PatientUpdateDTO containing ID and updated data.
     * @return The updated Patient object.
     * @throws PatientDAOException if a database error occurs.
     * @throws PatientNotFoundException if the patient does not exist.
     */
    @Override
    public Patient updatePatient(PatientUpdateDTO dto) throws PatientDAOException, PatientNotFoundException {
        Patient patient = mapToPatient(dto);

        if (patientDAO.getById(patient.getId()) == null) {
            throw new PatientNotFoundException(patient);
        }
        return patientDAO.update(patient);
    }

    /**
     * Deletes a patient by ID.
     *
     * @param id The unique identifier of the patient.
     * @throws PatientDAOException if a database error occurs.
     * @throws PatientNotFoundException if the patient does not exist.
     */
    @Override
    public void deletePatient(Integer id) throws PatientDAOException, PatientNotFoundException {
        if (patientDAO.getById(id) == null) {
            throw new PatientNotFoundException("Patient not found.");
        }
        patientDAO.delete(id);
    }

    /**
     * Retrieves a patient by ID.
     *
     * @param id The unique identifier of the patient.
     * @return The Patient object if found.
     * @throws PatientDAOException if a database error occurs.
     * @throws PatientNotFoundException if the patient does not exist.
     */
    @Override
    public Patient getPatientById(Integer id) throws PatientDAOException, PatientNotFoundException {
        Patient patient = patientDAO.getById(id);

        if (patient == null) {
            throw new PatientNotFoundException("Patient with id: " + id + " was not found.");
        }
        return patient;
    }

    /**
     * Retrieves a list of patients whose last names start with the given string.
     *
     * @param lastname The last name to search for (prefix match).
     * @return A list of matching Patient objects.
     * @throws PatientDAOException if a database error occurs.
     */
    @Override
    public List<Patient> getPatientsByLastname(String lastname) throws PatientDAOException {
        return patientDAO.getByLastname(lastname);
    }

    /**
     * Maps a PatientInsertDTO to a Patient entity.
     *
     * @param dto The DTO to map.
     * @return A Patient entity with null ID.
     */
    private Patient mapToPatient(PatientInsertDTO dto) {
        return new Patient(null, dto.getFirstname(), dto.getLastname());
    }

    /**
     * Maps a PatientUpdateDTO to a Patient entity.
     *
     * @param dto The DTO to map.
     * @return A Patient entity with the provided ID.
     */
    private Patient mapToPatient(PatientUpdateDTO dto) {
        return new Patient(dto.getId(), dto.getFirstname(), dto.getLastname());
    }
}
