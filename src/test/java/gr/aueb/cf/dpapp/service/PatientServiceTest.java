package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.IPatientDAO;
import gr.aueb.cf.dpapp.dao.PatientDAOImpl;
import gr.aueb.cf.dpapp.dao.exceptions.PatientDAOException;
import gr.aueb.cf.dpapp.dao.util.DBHelper;
import gr.aueb.cf.dpapp.dto.PatientInsertDTO;
import gr.aueb.cf.dpapp.dto.PatientUpdateDTO;
import gr.aueb.cf.dpapp.model.Patient;
import gr.aueb.cf.dpapp.service.exceptions.PatientNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link PatientServiceImpl} class.
 *
 * <p>This class tests the service layer methods for managing patients,
 * including insert, update, delete, and retrieval operations.
 * It uses a real DAO implementation connected to a test database
 * that is cleared before and after tests.</p>
 */
class PatientServiceTest {

    /** DAO instance for testing. */
    private static final IPatientDAO patientDAO = new PatientDAOImpl();

    /** Service instance under test. */
    private static IPatientService patientService;

    /**
     * Initializes the service and clears any test data.
     * Runs once before all tests.
     *
     * @throws SQLException if database access fails
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        patientService = new PatientServiceImpl(patientDAO);
        DBHelper.eraseData();
    }

    /**
     * Creates dummy patients before each test.
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
     * Inserts dummy patients for testing purposes.
     *
     * @throws PatientDAOException if insertion fails
     */
    public static void createDummyData() throws PatientDAOException {
        Patient patient = new Patient(null, "Giannis", "Antentokounmpo");
        patientDAO.insert(patient);

        patient = new Patient(null, "Pau", "Gasol");
        patientDAO.insert(patient);
    }

    /**
     * Tests inserting a patient via the service layer.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void persistAndGetPatient() throws PatientDAOException {
        PatientInsertDTO insertDTO = new PatientInsertDTO("Christina", "Dimitriou");
        patientService.insertPatient(insertDTO);

        List<Patient> patients = patientService.getPatientsByLastname("Dim");
        assertEquals(1, patients.size());
    }

    /**
     * Tests updating an existing patient via the service layer.
     *
     * @throws PatientDAOException if DAO operation fails
     * @throws PatientNotFoundException if the patient does not exist
     */
    @Test
    void updatePatient() throws PatientDAOException,PatientNotFoundException {
        PatientUpdateDTO updateDTO = new PatientUpdateDTO(2, "Konstantina", "Georgiadou");
        patientService.updatePatient(updateDTO);

        List<Patient> patients = patientService.getPatientsByLastname("Georgiadou");
        assertEquals(1, patients.size());
    }

    /**
     * Tests deleting an existing patient.
     *
     * @throws PatientDAOException if DAO operation fails
     * @throws PatientNotFoundException if the patient does not exist
     */
    @Test
    void deletePatientPositive() throws PatientDAOException, PatientNotFoundException {
        patientService.deletePatient(1);
        assertThrows(PatientNotFoundException.class, () -> patientService.deletePatient(1));
    }

    /**
     * Tests attempting to delete a non-existent patient.
     */
    @Test
    void deletePatientNegative() {
        assertThrows(PatientNotFoundException.class, () -> patientService.deletePatient(15));
    }

    /**
     * Tests retrieving an existing patient by ID.
     *
     * @throws PatientDAOException if DAO operation fails
     * @throws PatientNotFoundException if the patient does not exist
     */
    @Test
    void getPatientByIdPositive() throws PatientDAOException, PatientNotFoundException {
        Patient patient = patientService.getPatientById(1);
        assertEquals("Antentokounmpo", patient.getLastname());
    }

    /**
     * Tests retrieving a non-existent patient by ID.
     */
    @Test
    void getPatientByIdNegative() {
        assertThrows(PatientNotFoundException.class, () -> patientService.getPatientById(15));
    }

    /**
     * Tests retrieving patients by lastname using a partial match.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void getPatientByLastname() throws PatientDAOException {
        List<Patient> patients = patientService.getPatientsByLastname("Gas");
        assertEquals(1, patients.size());
    }

    /**
     * Tests retrieving patients by lastname when no matches exist.
     *
     * @throws PatientDAOException if DAO operation fails
     */
    @Test
    void getPatientByLastnameNegative() throws PatientDAOException {
        List<Patient> patients = patientService.getPatientsByLastname("Chatzis");
        assertEquals(0, patients.size());
    }
}
