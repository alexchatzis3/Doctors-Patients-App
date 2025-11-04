package gr.aueb.cf.dpapp.model;

/**
 * Represents a doctor entity in the system.
 * Contains identifying information such as a unique ID,
 * first name, and last name.
 */
public class Doctor {

    /**
     * The unique identifier of the doctor.
     */
    private Integer id;

    /**
     * The doctor's first name.
     */
    private String firstname;

    /**
     * The doctor's last name.
     */
    private String lastname;

    /**
     * Default no-argument constructor.
     * Used when creating a new doctor instance without predefined data.
     */
    public Doctor() {
        // Empty constructor for frameworks or manual initialization
    }

    /**
     * Constructs a doctor instance with the given values.
     *
     * @param id        The unique identifier of the doctor.
     * @param firstname The doctor's first name.
     * @param lastname  The doctor's last name.
     */
    public Doctor(Integer id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
    }

    /**
     * Returns the doctor's unique ID.
     *
     * @return the ID of the doctor.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the doctor's unique ID.
     *
     * @param id The ID to assign to the doctor.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the doctor's first name.
     *
     * @return the first name of the doctor.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the doctor's first name.
     *
     * @param firstname The first name to assign.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the doctor's last name.
     *
     * @return the last name of the doctor.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the doctor's last name.
     *
     * @param lastname The last name to assign.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns a formatted string representation of the doctor object.
     *
     * @return A string containing the doctor's ID, first name, and last name.
     */
    @Override
    public String toString() {
        return "id=" + id +
                ", firstname=" + firstname +
                ", lastname=" + lastname;
    }
}
