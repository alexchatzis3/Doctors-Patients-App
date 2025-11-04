package gr.aueb.cf.dpapp.dto;

/**
 * Data Transfer Object (DTO) representing a doctor-patient association.
 * Extends BaseDTO to include common person fields (firstname, lastname),
 * and adds doctorId and patientId to identify the association.
 */
public class DoctorsPatientsDTO extends BaseDTO {

    /**
     * The unique identifier of the doctor.
     */
    private Integer doctorId;

    /**
     * The unique identifier of the patient.
     */
    private Integer patientId;

    /**
     * Default no-argument constructor.
     */
    public DoctorsPatientsDTO() {
        // Empty constructor for frameworks or tools
    }

    /**
     * Constructs a DoctorsPatientsDTO with the given first name, last name,
     * doctor ID, and patient ID.
     *
     * @param firstname The person's first name.
     * @param lastname  The person's last name.
     * @param doctorId  The unique ID of the doctor.
     * @param patientId The unique ID of the patient.
     */
    public DoctorsPatientsDTO(String firstname, String lastname, Integer doctorId, Integer patientId) {
        super(firstname, lastname);
        this.doctorId = doctorId;
        this.patientId = patientId;
    }

    /**
     * Returns the doctor's unique ID.
     *
     * @return the doctor's ID.
     */
    public Integer getDoctorId() {
        return doctorId;
    }

    /**
     * Sets the doctor's unique ID.
     *
     * @param doctorId The ID to assign.
     */
    public void setDoctorId(Integer doctorId) {
        this.doctorId = doctorId;
    }

    /**
     * Returns the patient's unique ID.
     *
     * @return the patient's ID.
     */
    public Integer getPatientId() {
        return patientId;
    }

    /**
     * Sets the patient's unique ID.
     *
     * @param patientId The ID to assign.
     */
    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }
}
