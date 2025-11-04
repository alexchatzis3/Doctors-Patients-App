package gr.aueb.cf.dpapp.dto;

/**
 * Abstract base class for Data Transfer Objects (DTOs) representing persons.
 * Contains common fields such as first name and last name.
 */
public abstract class BaseDTO {

    /**
     * The first name of the person.
     */
    private String firstname;

    /**
     * The last name of the person.
     */
    private String lastname;

    /**
     * Default no-argument constructor.
     * Useful for frameworks or tools that instantiate objects via reflection.
     */
    public BaseDTO() {
        // Empty constructor for flexible initialization
    }

    /**
     * Constructs a BaseDTO with the given first and last name.
     *
     * @param firstname The first name of the person.
     * @param lastname  The last name of the person.
     */
    public BaseDTO(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * Returns the first name of the person.
     *
     * @return The first name.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the first name of the person.
     *
     * @param firstname The first name to assign.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the last name of the person.
     *
     * @return The last name.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the last name of the person.
     *
     * @param lastname The last name to assign.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
