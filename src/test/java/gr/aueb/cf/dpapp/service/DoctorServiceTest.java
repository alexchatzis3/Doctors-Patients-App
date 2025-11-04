package gr.aueb.cf.dpapp.service;

import gr.aueb.cf.dpapp.dao.DoctorDAOImpl;
import gr.aueb.cf.dpapp.dao.IDoctorDAO;
import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.dao.util.DBHelper;
import gr.aueb.cf.dpapp.dto.DoctorInsertDTO;
import gr.aueb.cf.dpapp.dto.DoctorUpdateDTO;
import gr.aueb.cf.dpapp.model.Doctor;
import gr.aueb.cf.dpapp.service.exceptions.DoctorNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DoctorServiceImpl} class.
 *
 * This class tests the service layer methods for managing doctors,
 * including insert, update, delete, and retrieval operations.
 * It uses a real DAO implementation connected to a test database
 * that is cleared before and after tests.
 */
class DoctorServiceTest {

    /** DAO instance for testing. */
    private static final IDoctorDAO doctorDAO = new DoctorDAOImpl();

    /** Service instance under test. */
    private static IDoctorService doctorService;

    /**
     * Initializes the service and clears any test data.
     * Runs once before all tests.
     *
     * @throws SQLException if database access fails
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        doctorService = new DoctorServiceImpl(doctorDAO);
        DBHelper.eraseData();
    }

    /**
     * Creates dummy doctors before each test.
     *
     * @throws DoctorDAOException if insertion fails
     */
    @BeforeEach
    public void setup() throws DoctorDAOException {
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
     * Inserts dummy doctors for testing purposes.
     *
     * @throws DoctorDAOException if insertion fails
     */
    public static void createDummyData() throws DoctorDAOException {
        Doctor doctor = new Doctor(null, "Alexandros", "Chatzis");
        doctorDAO.insert(doctor);

        doctor = new Doctor(null, "Kostas", "Georgiou");
        doctorDAO.insert(doctor);
    }

    /**
     * Tests inserting a doctor via the service layer.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    public void insertDoctor() throws DoctorDAOException {
        DoctorInsertDTO insertDTO = new DoctorInsertDTO("Bob", "Dylan");
        doctorService.insertDoctor(insertDTO);

        List<Doctor> doctors = doctorService.getDoctorsByLastname("Dylan");
        assertEquals(1, doctors.size());
    }

    /**
     * Tests updating an existing doctor via the service layer.
     *
     * @throws DoctorDAOException if DAO operation fails
     * @throws DoctorNotFoundException if the doctor does not exist
     */
    @Test
    public void updateDoctor() throws DoctorDAOException, DoctorNotFoundException {
        DoctorUpdateDTO updateDTO = new DoctorUpdateDTO(1, "Alex", "Chatz");
        doctorService.updateDoctor(updateDTO);

        List<Doctor> doctors = doctorService.getDoctorsByLastname("Chatz");
        assertEquals(1, doctors.size());
    }

    /**
     * Tests deleting an existing doctor.
     *
     * @throws DoctorDAOException if DAO operation fails
     * @throws DoctorNotFoundException if the doctor does not exist
     */
    @Test
    public void deleteDoctorPositive() throws DoctorDAOException, DoctorNotFoundException {
        doctorService.deleteDoctor(1);
        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(1));
    }

    /**
     * Tests attempting to delete a non-existent doctor.
     */
    @Test
    public void deleteDoctorNegative() {
        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(10));
    }

    /**
     * Tests retrieving an existing doctor by ID.
     *
     * @throws DoctorDAOException if DAO operation fails
     * @throws DoctorNotFoundException if the doctor does not exist
     */
    @Test
    void getDoctorByIdPositive() throws DoctorDAOException, DoctorNotFoundException {
        Doctor doctor = doctorService.getDoctorById(1);
        assertEquals("Chatzis", doctor.getLastname());
    }

    /**
     * Tests retrieving a non-existent doctor by ID.
     */
    @Test
    void getDoctorByIdNegative() {
        assertThrows(DoctorNotFoundException.class, () -> doctorService.getDoctorById(15));
    }

    /**
     * Tests retrieving doctors by lastname using a partial match.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void getTeacherByLastname() throws DoctorDAOException {
        List<Doctor> doctors = doctorService.getDoctorsByLastname("Ch");
        assertEquals(1, doctors.size());
    }

    /**
     * Tests retrieving doctors by lastname when no matches exist.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void getTeacherByLastnameNegative() throws DoctorDAOException {
        List<Doctor> doctors = doctorService.getDoctorsByLastname("Labrou");
        assertEquals(0, doctors.size());
    }
}
