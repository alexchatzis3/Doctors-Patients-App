package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.IDoctorsPatientsDAO;
import gr.aueb.cf.dpapp.dao.exceptions.DoctorsPatientsDAOException;

import java.util.List;

/**
 * Implementation of the IDoctorsPatientsService interface.
 * Handles business logic for assigning and retrieving patients
 * for a specific doctor.
 */
public class DoctorsPatientsServiceImpl implements IDoctorsPatientsService {

    /** DAO instance for performing database operations on doctor-patient associations. */
    private final IDoctorsPatientsDAO doctorsPatientsDAO;

    /**
     * Constructor with Dependency Injection of the DoctorsPatients DAO.
     *
     * @param doctorsPatientsDAO The IDoctorsPatientsDAO implementation to be used.
     */
    public DoctorsPatientsServiceImpl(IDoctorsPatientsDAO doctorsPatientsDAO) {
        this.doctorsPatientsDAO = doctorsPatientsDAO;
    }

    /**
     * Assigns a patient to a doctor.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    @Override
    public void assign(int doctorId, int patientId) throws DoctorsPatientsDAOException {
        doctorsPatientsDAO.insert(doctorId, patientId);
    }

    /**
     * Removes a patient from a doctor.
     *
     * @param doctorId  The unique identifier of the doctor.
     * @param patientId The unique identifier of the patient.
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    @Override
    public void remove(int doctorId, int patientId) throws DoctorsPatientsDAOException {
        doctorsPatientsDAO.delete(doctorId, patientId);
    }

    /**
     * Retrieves all patients assigned to a specific doctor.
     *
     * @param doctorId The unique identifier of the doctor.
     * @return A list of String arrays containing patient information (ID, first name, last name).
     * @throws DoctorsPatientsDAOException if a database error occurs.
     */
    @Override
    public List<String[]> getPatientsByDoctorId(int doctorId) throws DoctorsPatientsDAOException {
        return doctorsPatientsDAO.getPatientsByDoctorId(doctorId);
    }
}
