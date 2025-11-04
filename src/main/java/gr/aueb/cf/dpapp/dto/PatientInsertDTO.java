package gr.aueb.cf.dpapp.dto;

/**
 * Data Transfer Object (DTO) for inserting a new patient.
 * Extends BaseDTO to include common person fields such as firstname and lastname.
 */
public class PatientInsertDTO extends BaseDTO {

    /**
     * Default no-argument constructor.
     * Useful for frameworks or tools that instantiate objects via reflection.
     */
    public PatientInsertDTO() {
        // Empty constructor
    }

    /**
     * Constructs a PatientInsertDTO with the given first and last name.
     *
     * @param firstname The patient's first name.
     * @param lastname  The patient's last name.
     */
    public PatientInsertDTO(String firstname, String lastname) {
        super(firstname, lastname);
    }

    /**
     * Returns a string representation of the PatientInsertDTO.
     *
     * @return A string containing the first and last name, separated by a comma.
     */
    @Override
    public String toString() {
        return this.getFirstname() + ", " + this.getLastname();
    }
}
