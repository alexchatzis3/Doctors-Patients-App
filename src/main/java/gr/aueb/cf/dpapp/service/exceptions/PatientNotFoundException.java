package gr.aueb.cf.dpapp.service.exceptions;

import gr.aueb.cf.dpapp.model.Patient;

import java.io.Serial;

/**
 * Custom exception thrown when a Patient entity is not found
 * during service layer operations.
 */
public class PatientNotFoundException extends Exception {

    /**
     * Serial version UID for serialization compatibility.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new PatientNotFoundException for a specific Patient object.
     *
     * @param patient The Patient object that was not found.
     */
    public PatientNotFoundException(Patient patient) {
        super("Patient with id: " + patient.getId() + " was not found.");
    }

    /**
     * Constructs a new PatientNotFoundException with a custom message.
     *
     * @param s The detail message explaining the exception.
     */
    public PatientNotFoundException(String s) {
        super(s);
    }
}
