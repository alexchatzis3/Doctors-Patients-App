package gr.aueb.cf.dpapp.dto;

/**
 * Data Transfer Object (DTO) for updating an existing patient.
 * Extends BaseDTO to include firstname and lastname, and adds the unique ID
 * to identify which patient to update.
 */
public class PatientUpdateDTO extends BaseDTO {

    /**
     * The unique identifier of the patient to update.
     */
    private Integer id;

    /**
     * Default no-argument constructor.
     */
    public PatientUpdateDTO() {
        // Empty constructor for frameworks or tools
    }

    /**
     * Constructs a PatientUpdateDTO with the given ID, first name, and last name.
     *
     * @param id        The unique identifier of the patient.
     * @param firstname The patient's first name.
     * @param lastname  The patient's last name.
     */
    public PatientUpdateDTO(Integer id, String firstname, String lastname) {
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
     * Returns a string representation of the PatientUpdateDTO.
     *
     * @return A string containing the patient's ID, first name, and last name.
     */
    @Override
    public String toString() {
        return "{" + id + ", " +
                getFirstname() + ", " + getLastname() +
                '}';
    }
}
