package gr.aueb.cf.dpapp.dto;

/**
 * Data Transfer Object (DTO) for inserting a new doctor.
 * Extends the BaseDTO to inherit common person fields such as
 * firstname and lastname.
 */
public class DoctorInsertDTO extends BaseDTO {

    /**
     * Default no-argument constructor.
     * Useful for frameworks or tools that instantiate objects via reflection.
     */
    public DoctorInsertDTO() {
        // Empty constructor
    }

    /**
     * Constructs a DoctorInsertDTO with the given first and last name.
     *
     * @param firstname The doctor's first name.
     * @param lastname  The doctor's last name.
     */
    public DoctorInsertDTO(String firstname, String lastname) {
        super(firstname, lastname);
    }

    /**
     * Returns a string representation of the DoctorInsertDTO.
     *
     * @return A string containing the first and last name, separated by a comma.
     */
    @Override
    public String toString() {
        return this.getFirstname() + ", " + this.getLastname();
    }
}
