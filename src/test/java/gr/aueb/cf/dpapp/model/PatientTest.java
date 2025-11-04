package gr.aueb.cf.dpapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Patient} class.
 *
 * This class tests the constructors, getters, setters, and the toString method
 * of the Patient model.
 */
class PatientTest {

    /**
     * Tests the default constructor, setters, and getters of the Patient class.
     * Ensures that after setting values, the getters return the expected results.
     */
    @Test
    void defaultConstructorGettersAndSetters() {
        Patient patient = new Patient();

        patient.setId(1);
        assertEquals(1, patient.getId());

        patient.setFirstname("Alex");
        assertEquals("Alex", patient.getFirstname());

        patient.setLastname("Chatzis");
        assertEquals("Chatzis", patient.getLastname());
    }

    /**
     * Tests the overloaded constructor and the toString method of the Patient class.
     * Ensures that the object is correctly initialized and that toString returns
     * the expected formatted string.
     */
    @Test
    void overloadedConstructorAndToString() {
        Patient patient = new Patient(1, "Anna", "Ioannou");
        assertEquals(1, patient.getId());
        assertEquals("Anna", patient.getFirstname());
        assertEquals("Ioannou", patient.getLastname());

        String expected = "id=1, firstname=Anna, lastname=Ioannou";
        assertEquals(expected, patient.toString());
    }
}
