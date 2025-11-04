package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.dto.DoctorInsertDTO;
import gr.aueb.cf.dpapp.dto.DoctorUpdateDTO;
import gr.aueb.cf.dpapp.model.Doctor;
import gr.aueb.cf.dpapp.service.exceptions.DoctorNotFoundException;

import java.util.List;

/**
 * Service layer interface for Doctor-related business operations.
 * Provides methods for creating, updating, deleting, and retrieving doctors.
 */
public interface IDoctorService {

    /**
     * Inserts a new doctor using the provided DTO.
     *
     * @param dto The DoctorInsertDTO containing first name and last name.
     * @return The inserted Doctor object.
     * @throws DoctorDAOException if a database error occurs.
     */
    Doctor insertDoctor(DoctorInsertDTO dto) throws DoctorDAOException;

    /**
     * Updates an existing doctor using the provided DTO.
     *
     * @param dto The DoctorUpdateDTO containing the ID and updated data.
     * @return The updated Doctor object.
     * @throws DoctorNotFoundException if the doctor does not exist.
     * @throws DoctorDAOException if a database error occurs.
     */
    Doctor updateDoctor(DoctorUpdateDTO dto) throws DoctorNotFoundException, DoctorDAOException;

    /**
     * Deletes a doctor by ID.
     *
     * @param id The unique identifier of the doctor to delete.
     * @throws DoctorDAOException if a database error occurs.
     * @throws DoctorNotFoundException if the doctor does not exist.
     */
    void deleteDoctor(Integer id) throws DoctorDAOException, DoctorNotFoundException;

    /**
     * Retrieves a doctor by ID.
     *
     * @param id The unique identifier of the doctor.
     * @return The Doctor object if found.
     * @throws DoctorNotFoundException if the doctor does not exist.
     * @throws DoctorDAOException if a database error occurs.
     */
    Doctor getDoctorById(Integer id) throws DoctorNotFoundException, DoctorDAOException;

    /**
     * Retrieves a list of doctors whose last names match the given string.
     *
     * @param lastname The last name to search for (prefix match).
     * @return A list of matching Doctor objects.
     * @throws DoctorDAOException if a database error occurs.
     */
    List<Doctor> getDoctorsByLastname(String lastname) throws DoctorDAOException;
}
