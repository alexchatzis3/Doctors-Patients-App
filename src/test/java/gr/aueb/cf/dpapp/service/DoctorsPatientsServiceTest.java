package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.DoctorsPatientsDAOImpl;
import gr.aueb.cf.dpapp.dao.IDoctorsPatientsDAO;
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
 * Unit tests for the {@link DoctorsPatientsServiceImpl} class.
 *
 * <p>This class tests the service layer responsible for managing
 * relationships between doctors and patients, including assigning,
 * removing, and retrieving doctor-patient links.</p>
 */
class DoctorsPatientsServiceTest {

    /** DAO instance for testing. */
    private static final IDoctorsPatientsDAO doctorsPatientsDAO = new DoctorsPatientsDAOImpl();

    /** Service instance under test. */
    private static IDoctorsPatientsService doctorsPatientsService;

    /**
     * Initializes the service and clears any existing test data.
     * Runs once before all tests.
     *
     * @throws SQLException if database access fails
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        doctorsPatientsService = new DoctorsPatientsServiceImpl(doctorsPatientsDAO);
        DBHelper.eraseData();
    }

    /**
     * Prepares the database with dummy doctors and patients
     * before each test.
     *
     * @throws DoctorsPatientsDAOException if data insertion fails
     */
    @BeforeEach
    public void setup() throws DoctorsPatientsDAOException {
        createDummyData();
    }

    /**
     * Cleans up the database after each test.
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
     * Tests assigning a patient to a doctor
     * and retrieving the assigned patients.
     *
     * @throws DoctorsPatientsDAOException if DAO operation fails
     */
    @Test
    void persistAndGetDoctorPatient() throws DoctorsPatientsDAOException {
        doctorsPatientsService.assign(2, 2);
        List<String[]> doctorPatients = doctorsPatientsService.getPatientsByDoctorId(2);
        assertEquals(1, doctorPatients.size());
    }

    /**
     * Tests removing an existing doctor-patient relationship
     * and verifying it no longer exists.
     *
     * @throws DoctorsPatientsDAOException if DAO operation fails
     */
    @Test
    void deleteDoctorPatient() throws DoctorsPatientsDAOException {
        doctorsPatientsService.assign(1, 1);
        doctorsPatientsService.remove(1, 1);

        List<String[]> patients = doctorsPatientsService.getPatientsByDoctorId(1);
        assertEquals(0, patients.size());
    }

    /**
     * Tests retrieving multiple patients for a doctor
     * and verifies that the retrieved patients match the expected data.
     *
     * @throws DoctorsPatientsDAOException if DAO operation fails
     */
    @Test
    void getPatientsByDoctorId() throws DoctorsPatientsDAOException {
        doctorsPatientsService.assign(1, 1);
        doctorsPatientsService.assign(1, 2);

        List<String[]> patientsDoctor = doctorsPatientsService.getPatientsByDoctorId(1);
        assertEquals(2, patientsDoctor.size());

        boolean hasDimitra = patientsDoctor.stream()
                .anyMatch(p -> p[1].equals("Dimitra") && p[2].equals("Papadopoulou"));
        boolean hasPetros = patientsDoctor.stream()
                .anyMatch(p -> p[1].equals("Petros") && p[2].equals("Konstantinou"));
        assertTrue(hasDimitra);
        assertTrue(hasPetros);
    }
}
