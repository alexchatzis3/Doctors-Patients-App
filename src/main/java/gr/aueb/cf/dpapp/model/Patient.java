package gr.aueb.cf.dpapp.model;

/**
 * Represents a patient entity in the system.
 * Contains basic identifying information such as ID,
 * first name, and last name.
 */
public class Patient {

    /**
     * The unique identifier of the patient.
     */
    private Integer id;

    /**
     * The patient's first name.
     */
    private String firstname;

    /**
     * The patient's last name.
     */
    private String lastname;

    /**
     * Default no-argument constructor.
     * Useful when frameworks or tools instantiate the class.
     */
    public Patient() {
        // Empty constructor for flexible initialization
    }

    /**
     * Constructs a patient instance with the given values.
     *
     * @param id        The unique identifier of the patient.
     * @param firstname The patient's first name.
     * @param lastname  The patient's last name.
     */
    public Patient(Integer id, String firstname, String lastname) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
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
     * @param id The ID to assign to the patient.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Returns the patient's first name.
     *
     * @return the first name of the patient.
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Sets the patient's first name.
     *
     * @param firstname The first name to assign.
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * Returns the patient's last name.
     *
     * @return the last name of the patient.
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Sets the patient's last name.
     *
     * @param lastname The last name to assign.
     */
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    /**
     * Returns a formatted string representation of the patient object.
     *
     * @return A string containing the patient's ID, first name, and last name.
     */
    @Override
    public String toString() {
        return "id=" + id +
                ", firstname=" + firstname +
                ", lastname=" + lastname;
    }
}
