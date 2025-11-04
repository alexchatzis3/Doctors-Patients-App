package gr.aueb.cf.dpapp.dao.exceptions;

import java.io.Serial;

/**
 * Custom exception class for Doctors-Patients DAO operations.
 * Thrown when an error occurs while performing database operations
 * related to the association between doctors and patients.
 */
public class DoctorsPatientsDAOException extends Exception {

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DoctorsPatientsDAOException with the specified detail message.
     *
     * @param s The detail message explaining the exception.
     */
    public DoctorsPatientsDAOException(String s) {
        super(s);
    }
}
