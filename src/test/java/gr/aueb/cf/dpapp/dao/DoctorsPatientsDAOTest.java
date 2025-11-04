package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorsPatientsDAOException;
import gr.aueb.cf.dpapp.dao.util.DBHelper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DoctorsPatientsDAOImpl} class.
 *
 * This class tests CRUD operations for the many-to-many relationship between
 * doctors and patients using the DoctorsPatients DAO implementation.
 * Test data is set up using {@link DBHelper}.
 */
class DoctorsPatientsDAOTest {

    /** The DAO instance used for testing. */
    private static IDoctorsPatientsDAO doctorsPatientsDAO;

    /**
     * Initializes the DAO and clears any existing test data.
     * This method runs once before all tests.
     *
     * @throws SQLException if database access fails
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        doctorsPatientsDAO = new DoctorsPatientsDAOImpl();
        DBHelper.eraseData();
    }

    /**
     * Creates dummy data before each test.
     *
     * @throws DoctorsPatientsDAOException if insertion fails
     */
    @BeforeEach
    public void setup() throws DoctorsPatientsDAOException {
        createDummyData();
    }

    /**
     * Clears the database after each test.
     *
     * @throws SQLException if database access fails
     */
    @AfterEach
    public void tearDown() throws SQLException {
        DBHelper.eraseData();
    }

    /**
     * Inserts dummy doctors and patients for testing purposes.
     *
     * @throws DoctorsPatientsDAOException if insertion fails
     */
    public static void createDummyData() throws DoctorsPatientsDAOException {
        try {
            DBHelper.insertDoctor(1, "Alexandros", "Chatzis");
            DBHelper.insertDoctor(2, "Nancy", "Chatzi");

            DBHelper.insertPatient(1, "Dimitra", "Papadopoulou");
            DBHelper.insertPatient(2, "Petros", "Konstantinou");
        } catch (SQLException e) {
            throw new DoctorsPatientsDAOException("Error inserting test data");
        }
    }

    /**
     * Tests inserting a doctor-patient relation and retrieving it by doctor ID.
     *
     * @throws DoctorsPatientsDAOException if DAO operation fails
     */
    @Test
    void persistAndGetDoctorPatient() throws DoctorsPatientsDAOException {
        doctorsPatientsDAO.insert(2, 2);
        List<String[]> doctorPatients = doctorsPatientsDAO.getPatientsByDoctorId(2);
        assertEquals(1, doctorPatients.size());
    }

    /**
     * Tests deleting a doctor-patient relation.
     *
     * @throws DoctorsPatientsDAOException if DAO operation fails
     */
    @Test
    void deleteDoctorPatient() throws DoctorsPatientsDAOException {
        doctorsPatientsDAO.insert(1, 1);
        doctorsPatientsDAO.delete(1, 1);

        List<String[]> patients = doctorsPatientsDAO.getPatientsByDoctorId(1);
        assertEquals(0, patients.size());
    }

    /**
     * Tests retrieving patients linked to a specific doctor ID.
     *
     * @throws DoctorsPatientsDAOException if DAO operation fails
     */
    @Test
    void getPatientsByDoctorId() throws DoctorsPatientsDAOException {
        doctorsPatientsDAO.insert(1, 1);
        doctorsPatientsDAO.insert(1, 2);

        List<String[]> patientsDoctor = doctorsPatientsDAO.getPatientsByDoctorId(1);
        assertEquals(2, patientsDoctor.size());

        boolean hasDimitra = patientsDoctor.stream()
                .anyMatch(p -> p[1].equals("Dimitra") && p[2].equals("Papadopoulou"));
        boolean hasPetros = patientsDoctor.stream()
                .anyMatch(p -> p[1].equals("Petros") && p[2].equals("Konstantinou"));

        assertTrue(hasDimitra);
        assertTrue(hasPetros);
    }
}
