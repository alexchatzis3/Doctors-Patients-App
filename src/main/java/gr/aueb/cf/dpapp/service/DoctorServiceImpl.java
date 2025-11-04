package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.IDoctorDAO;
import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.dto.DoctorInsertDTO;
import gr.aueb.cf.dpapp.dto.DoctorUpdateDTO;
import gr.aueb.cf.dpapp.model.Doctor;
import gr.aueb.cf.dpapp.service.exceptions.DoctorNotFoundException;

import java.util.List;

/**
 * Implementation of the IDoctorService interface.
 * Handles business logic for Doctor entities, including validation
 * and mapping between DTOs and model entities.
 */
public class DoctorServiceImpl implements IDoctorService {

    /** DAO instance for performing database operations on Doctor entities. */
    private final IDoctorDAO doctorDAO;

    /**
     * Constructor with Dependency Injection of the Doctor DAO.
     *
     * @param doctorDAO The IDoctorDAO implementation to be used.
     */
    public DoctorServiceImpl(IDoctorDAO doctorDAO) {
        this.doctorDAO = doctorDAO;
    }

    /**
     * Inserts a new doctor using a DTO.
     *
     * @param dto The DoctorInsertDTO containing first and last name.
     * @return The inserted Doctor object.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public Doctor insertDoctor(DoctorInsertDTO dto) throws DoctorDAOException {
        Doctor doctor = mapToDoctor(dto);
        return doctorDAO.insert(doctor);
    }

    /**
     * Updates an existing doctor using a DTO.
     *
     * @param dto The DoctorUpdateDTO containing ID and updated data.
     * @return The updated Doctor object.
     * @throws DoctorNotFoundException if the doctor does not exist.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public Doctor updateDoctor(DoctorUpdateDTO dto) throws DoctorNotFoundException, DoctorDAOException {
        Doctor doctor = mapToDoctor(dto);

        if (doctorDAO.getById(doctor.getId()) == null) {
            throw new DoctorNotFoundException(doctor);
        }
        return doctorDAO.update(doctor);
    }

    /**
     * Deletes a doctor by ID.
     *
     * @param id The unique identifier of the doctor.
     * @throws DoctorDAOException if a database error occurs.
     * @throws DoctorNotFoundException if the doctor does not exist.
     */
    @Override
    public void deleteDoctor(Integer id) throws DoctorDAOException, DoctorNotFoundException {
        if (doctorDAO.getById(id) == null) {
            throw new DoctorNotFoundException("Doctor not found");
        }
        doctorDAO.delete(id);
    }

    /**
     * Retrieves a doctor by ID.
     *
     * @param id The unique identifier of the doctor.
     * @return The Doctor object if found.
     * @throws DoctorNotFoundException if the doctor does not exist.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public Doctor getDoctorById(Integer id) throws DoctorNotFoundException, DoctorDAOException {
        Doctor doctor = doctorDAO.getById(id);

        if (doctor == null) {
            throw new DoctorNotFoundException("Doctor with id: " + id + " not found.");
        }
        return doctor;
    }

    /**
     * Retrieves a list of doctors whose last names start with the given string.
     *
     * @param lastname The last name to search for (prefix match).
     * @return A list of matching Doctor objects.
     * @throws DoctorDAOException if a database error occurs.
     */
    @Override
    public List<Doctor> getDoctorsByLastname(String lastname) throws DoctorDAOException {
        return doctorDAO.getByLastname(lastname);
    }

    /**
     * Maps a DoctorInsertDTO to a Doctor entity.
     *
     * @param dto The DTO to map.
     * @return A Doctor entity with null ID.
     */
    private Doctor mapToDoctor(DoctorInsertDTO dto) {
        return new Doctor(null, dto.getFirstname(), dto.getLastname());
    }

    /**
     * Maps a DoctorUpdateDTO to a Doctor entity.
     *
     * @param dto The DTO to map.
     * @return A Doctor entity with the provided ID.
     */
    private Doctor mapToDoctor(DoctorUpdateDTO dto) {
        return new Doctor(dto.getId(), dto.getFirstname(), dto.getLastname());
    }
}
