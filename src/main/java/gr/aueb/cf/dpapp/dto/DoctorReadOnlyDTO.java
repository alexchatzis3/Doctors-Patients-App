package gr.aueb.cf.dpapp.dto;

/**
 * Data Transfer Object (DTO) for reading doctor data.
 * Extends BaseDTO to include firstname and lastname, and adds the unique ID.
 * This DTO is primarily used for displaying doctor information in a read-only manner.
 */
public class DoctorReadOnlyDTO extends BaseDTO {

    /**
     * The unique identifier of the doctor.
     */
    private Integer id;

    /**
     * Default no-argument constructor.
     */
    public DoctorReadOnlyDTO() {
        // Empty constructor for frameworks or tools
    }

    /**
     * Constructs a DoctorReadOnlyDTO with the given ID, first name, and last name.
     *
     * @param id        The doctor's unique identifier.
     * @param firstname The doctor's first name.
     * @param lastname  The doctor's last name.
     */
    public DoctorReadOnlyDTO(Integer id, String firstname, String lastname) {
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
     * Returns a string representation of the DoctorReadOnlyDTO.
     *
     * @return A string containing the doctor's ID, first name, and last name.
     */
    @Override
    public String toString() {
        return "{" + id + ", " + getFirstname()
                + ", " + getLastname() + '}';
    }
}
