package gr.aueb.cf.dpapp.validator;

import gr.aueb.cf.dpapp.dto.BaseDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for validating Doctor-related DTOs.
 *
 * <p>No instances of this class should be created.</p>
 */
public class DoctorValidator {

    /**
     * Private constructor to prevent instantiation.
     */
    private DoctorValidator() {
    }

    /**
     * Validates a Doctor DTO (or any DTO extending BaseDTO).
     * Checks the first name and last name for length and whitespace rules.
     *
     * @param <T> The type of DTO extending BaseDTO.
     * @param dto The DTO to validate.
     * @return A map of field names to error messages. Empty if no errors.
     */
    public static <T extends BaseDTO> Map<String, String> validate(T dto) {
        Map<String, String> errors = new HashMap<>();

        // Validate first name length
        if (dto.getFirstname().length() < 2 || dto.getFirstname().length() > 32) {
            errors.put("firstname", "Firstname should be between 2 and 32 characters");
        }

        // Validate last name length
        if (dto.getLastname().length() < 2 || dto.getLastname().length() > 32) {
            errors.put("lastname", "Lastname should be between 2 and 32 characters");
        }

        // Check for whitespaces in first name
        if (dto.getFirstname().matches("^.*\\s+.*$")) {
            errors.put("firstname", "Firstname should not include whitespaces.");
        }

        // Check for whitespaces in last name
        if (dto.getLastname().matches("^.*\\s+.*$")) {
            errors.put("lastname", "Lastname should not include whitespaces.");
        }

        return errors;
    }
}
