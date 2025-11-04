package gr.aueb.cf.dpapp.model;

/**
 * Represents a mapping between a doctor and a patient.
 * This class models the many-to-many relationship between doctors and patients,
 * storing only their IDs.
 */
public class DoctorPatient {

    /**
     * The unique identifier of the doctor.
     */
    private Integer d_id;

    /**
     * The unique identifier of the patient.
     */
    private Integer p_id;

    /**
     * Default no-argument constructor.
     * Useful for frameworks or tools that instantiate objects via reflection.
     */
    public DoctorPatient() {
        // Empty constructor for flexible initialization
    }

    /**
     * Constructs a DoctorPatient instance with the given doctor and patient IDs.
     *
     * @param d_id The ID of the doctor.
     * @param p_id The ID of the patient.
     */
    public DoctorPatient(Integer d_id, Integer p_id) {
        this.d_id = d_id;
        this.p_id = p_id;
    }

    /**
     * Returns the doctor's ID.
     *
     * @return the doctor's unique identifier.
     */
    public Integer getD_id() {
        return d_id;
    }

    /**
     * Sets the doctor's ID.
     *
     * @param d_id The doctor's unique identifier to assign.
     */
    public void setD_id(Integer d_id) {
        this.d_id = d_id;
    }

    /**
     * Returns the patient's ID.
     *
     * @return the patient's unique identifier.
     */
    public Integer getP_id() {
        return p_id;
    }

    /**
     * Sets the patient's ID.
     *
     * @param p_id The patient's unique identifier to assign.
     */
    public void setP_id(Integer p_id) {
        this.p_id = p_id;
    }

    /**
     * Returns a formatted string representing the doctor-patient mapping.
     *
     * @return A string containing the doctor ID and patient ID.
     */
    @Override
    public String toString() {
        return "doctor_id=" + d_id +
                ", patient_id=" + p_id;
    }
}
