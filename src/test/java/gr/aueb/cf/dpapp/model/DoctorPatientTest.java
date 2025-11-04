package gr.aueb.cf.dpapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DoctorPatient} class.
 *
 * This class tests the constructors, getters, setters, and the toString method
 * of the DoctorPatient model.
 */
class DoctorPatientTest {

    /**
     * Tests the default constructor, setters, and getters of the DoctorPatient class.
     * Ensures that after setting values, the getters return the expected results.
     */
    @Test
    void defaultConstructorGettersAndSetters() {
        DoctorPatient doctorPatient = new DoctorPatient();

        doctorPatient.setD_id(1);
        assertEquals(1, doctorPatient.getD_id());

        doctorPatient.setP_id(1);
        assertEquals(1, doctorPatient.getP_id());
    }

    /**
     * Tests the overloaded constructor and the toString method of the DoctorPatient class.
     * Ensures that the object is correctly initialized and that toString returns
     * the expected formatted string.
     */
    @Test
    void overloadedConstructorAndToString() {
        DoctorPatient doctorPatient = new DoctorPatient(1, 1);
        assertEquals(1, doctorPatient.getD_id());
        assertEquals(1, doctorPatient.getP_id());

        String expected = "doctor_id=1, patient_id=1";
        assertEquals(expected, doctorPatient.toString());
    }
}
