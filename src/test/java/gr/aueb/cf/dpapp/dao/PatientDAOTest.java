package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.dao.util.DBHelper;
import gr.aueb.cf.dpapp.model.Patient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PatientDAOImpl} class.
 *
 * This class tests CRUD operations (insert, update, delete, select) of the
 * Patient DAO implementation using a temporary database setup via DBHelper.
 */
class PatientDAOTest {

    /** The DAO instance used for testing. */
    private static IPatientDAO patientDAO;

    /**
     * Initializes the DAO and clears any existing test data.
     * This method runs once before all tests.
     *
     * @throws SQLException if database access fails
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        patientDAO = new PatientDAOImpl();
        DBHelper.eraseData();
    }

    /**
     * Creates dummy data before each test.
     *
     * @throws PatientDAOException if insertion fails
     */
    @BeforeEach
    public void setup() throws PatientDAOException {
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
     * Creates dummy patient records for testing purposes.
     *
     * @throws PatientDAOException if insertion fails
     */
    public static void createDummyData() throws PatientDAOException {
        Patient patient = new Patient(null, "Alexandros", "Chatzis");
        patientDAO.insert(patient);

        patient = new Patient(null, "Kostas", "Georgiou");
        patientDAO.insert(patient);
    }

    /**
     * Tests inserting a patient and retrieving it by lastname.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void persistAndGetPatient() throws PatientDAOException {
        Patient patient = new Patient(null, "Christina", "Dimitriou");

        patientDAO.insert(patient);
        List<Patient> patients = patientDAO.getByLastname("Dim");
        assertEquals(1, patients.size());
    }

    /**
     * Tests updating a patient's information.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void updatePatient() throws PatientDAOException {
        Patient patient = new Patient(2, "Konstantina", "Georgiadou");

        patientDAO.update(patient);

        List<Patient> patients = patientDAO.getByLastname("Georgiadou");
        assertEquals(1, patients.size());
    }

    /**
     * Tests deleting a patient by ID.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void deletePatient() throws PatientDAOException {
        patientDAO.delete(1);

        Patient patient = patientDAO.getById(1);
        assertNull(patient);
    }

    /**
     * Tests retrieving a patient by a valid ID.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void getPatientByIdPositive() throws PatientDAOException {
        Patient patient = patientDAO.getById(1);
        assertEquals("Chatzis", patient.getLastname());
    }

    /**
     * Tests retrieving a patient by an invalid ID (should return null).
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void getPatientByIdNegative() throws PatientDAOException {
        Patient patient = patientDAO.getById(5);
        assertNull(patient);
    }

    /**
     * Tests retrieving patients by a partial lastname match.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void getPatientByLastname() throws PatientDAOException {
        List<Patient> patients = patientDAO.getByLastname("Ch");
        assertEquals(1, patients.size());
    }
}
