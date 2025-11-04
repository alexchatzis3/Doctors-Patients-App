package gr.aueb.cf.dpapp.dao.exceptions;

import java.io.Serial;

/**
 * Custom exception class for Doctor DAO operations.
 * Thrown when an error occurs while performing database operations related to doctors.
 */
public class DoctorDAOException extends Exception {

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DoctorDAOException with the specified detail message.
     *
     * @param s The detail message explaining the exception.
     */
    public DoctorDAOException(String s) {
        super(s);
    }
}
