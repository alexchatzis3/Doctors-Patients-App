package gr.aueb.cf.dpapp.dao.exceptions;

import java.io.Serial;

/**
 * Custom exception class for Patient DAO operations.
 * Thrown when an error occurs while performing database operations related to patients.
 */
public class PatientDAOException extends Exception {

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PatientDAOException with the specified detail message.
     *
     * @param s The detail message explaining the exception.
     */
    public PatientDAOException(String s) {
        super(s);
    }
}
