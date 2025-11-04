package gr.aueb.cf.dpapp.service.exceptions;

import gr.aueb.cf.dpapp.model.Doctor;

import java.io.Serial;

/**
 * Custom exception thrown when a Doctor entity is not found
 * during service layer operations.
 */
public class DoctorNotFoundException extends Exception {

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new DoctorNotFoundException for a specific Doctor object.
     *
     * @param doctor The Doctor object that was not found.
     */
    public DoctorNotFoundException(Doctor doctor) {
        super("Doctor with id: " + doctor.getId() + " was not found.");
    }

    /**
     * Constructs a new DoctorNotFoundException with a custom message.
     *
     * @param s The detail message explaining the exception.
     */
    public DoctorNotFoundException(String s) {
        super(s);
    }
}
