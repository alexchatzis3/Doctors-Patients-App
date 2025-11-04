package gr.aueb.cf.dpapp.dto;

/**
 * Data Transfer Object (DTO) for reading patient data.
 * Extends BaseDTO to include firstname and lastname, and adds the unique ID.
 * This DTO is primarily used for displaying patient information in a read-only manner.
 */
public class PatientReadOnlyDTO extends BaseDTO {

    /**
     * The unique identifier of the patient.
     */
    private Integer id;

    /**
     * Default no-argument constructor.
     */
    public PatientReadOnlyDTO() {
        // Empty constructor for frameworks or tools
    }

    /**
     * Constructs a PatientReadOnlyDTO with the given ID, first name, and last name.
     *
     * @param id        The patient's unique identifier.
     * @param firstname The patient's first name.
     * @param lastname  The patient's last name.
     */
    public PatientReadOnlyDTO(Integer id, String firstname, String lastname) {
        super(firstname, lastname);
        this.id = id;
    }

    /**
     * Returns the patient's unique ID.
     *
     * @return the patient's ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the patient's unique ID.
     *
     * @param id The ID to assign.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the PatientReadOnlyDTO.
     *
     * @return A string containing the patient's ID, first name, and last name.
     */
    @Override
    public String toString() {
        return "{" + id + ", " + getFirstname() +
                ", " + getLastname() + '}';
    }
}
