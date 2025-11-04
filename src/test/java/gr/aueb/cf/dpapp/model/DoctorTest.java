package gr.aueb.cf.dpapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link Doctor} class.
 *
 * This class tests the constructors, getters, setters, and the toString method
 * of the Doctor model.
 */
class DoctorTest {

    /**
     * Tests the default constructor, setters, and getters of the Doctor class.
     * Ensures that after setting values, the getters return the expected results.
     */
    @Test
    void defaultConstructorGettersAndSetters() {
        Doctor doctor = new Doctor();

        doctor.setId(1);
        assertEquals(1, doctor.getId());

        doctor.setFirstname("Alex");
        assertEquals("Alex", doctor.getFirstname());

        doctor.setLastname("Chatzis");
        assertEquals("Chatzis", doctor.getLastname());
    }

    /**
     * Tests the overloaded constructor and the toString method of the Doctor class.
     * Ensures that the object is correctly initialized and that toString returns
     * the expected formatted string.
     */
    @Test
    void overloadedConstructorAndToString() {
        Doctor doctor = new Doctor(1, "Anna", "Ioannou");
        assertEquals(1, doctor.getId());
        assertEquals("Anna", doctor.getFirstname());
        assertEquals("Ioannou", doctor.getLastname());

        String expected = "id=1, firstname=Anna, lastname=Ioannou";
        assertEquals(expected, doctor.toString());
    }
}
