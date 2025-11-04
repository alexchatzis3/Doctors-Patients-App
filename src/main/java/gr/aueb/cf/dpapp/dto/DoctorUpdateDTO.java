package gr.aueb.cf.dpapp.dto;

/**
 * Data Transfer Object (DTO) for updating an existing doctor.
 * Extends BaseDTO to include firstname and lastname, and adds the unique ID
 * to identify which doctor to update.
 */
public class DoctorUpdateDTO extends BaseDTO {

    /**
     * The unique identifier of the doctor to update.
     */
    private Integer id;

    /**
     * Default no-argument constructor.
     */
    public DoctorUpdateDTO() {
        // Empty constructor for frameworks or tools
    }

    /**
     * Constructs a DoctorUpdateDTO with the given ID, first name, and last name.
     *
     * @param id        The unique identifier of the doctor.
     * @param firstname The doctor's first name.
     * @param lastname  The doctor's last name.
     */
    public DoctorUpdateDTO(Integer id, String firstname, String lastname) {
        super(firstname, lastname);
        this.id = id;
    }

    /**
     * Returns the doctor's unique ID.
     *
     * @return the doctor's ID.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the doctor's unique ID.
     *
     * @param id The ID to assign.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns a string representation of the DoctorUpdateDTO.
     *
     * @return A string containing the doctor's ID, first name, and last name.
     */
    @Override
    public String toString() {
        return "{" + id + ", "
                + getFirstname() + ", "
                + getLastname() +
                '}';
    }
}
