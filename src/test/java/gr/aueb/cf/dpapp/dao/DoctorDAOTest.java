package gr.aueb.cf.dpapp.dao;

import gr.aueb.cf.dpapp.dao.exceptions.DoctorDAOException;
import gr.aueb.cf.dpapp.dao.util.DBHelper;
import gr.aueb.cf.dpapp.model.Doctor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the {@link DoctorDAOImpl} class.
 *
 * This class tests CRUD operations (insert, update, delete, select) of the
 * Doctor DAO implementation, using a temporary database setup via DBHelper.
 */
class DoctorDAOTest {

    /** The DAO instance used for testing. */
    private static IDoctorDAO doctorDAO;

    /**
     * Initializes the DAO and clears any existing test data.
     * This method runs once before all tests.
     *
     * @throws SQLException if database access fails
     */
    @BeforeAll
    public static void setupClass() throws SQLException {
        doctorDAO = new DoctorDAOImpl();
        DBHelper.eraseData();
    }

    /**
     * Creates dummy data before each test.
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
     * Creates dummy doctor records for testing purposes.
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
     * Tests inserting a doctor and retrieving it by lastname.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void persistAndGetDoctor() throws DoctorDAOException {
        Doctor doctor = new Doctor(null, "Anna", "Kefala");

        doctorDAO.insert(doctor);
        List<Doctor> doctors = doctorDAO.getByLastname("Kef");
        assertEquals(1, doctors.size());
    }

    /**
     * Tests updating a doctor's information.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void updateDoctor() throws DoctorDAOException {
        Doctor doctor = new Doctor(2, "KostasUpdated", "GeorgiouUpdated");

        doctorDAO.update(doctor);

        List<Doctor> doctors = doctorDAO.getByLastname("GeorgiouUpdated");
        assertEquals(1, doctors.size());
    }

    /**
     * Tests deleting a doctor by ID.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void deleteDoctor() throws DoctorDAOException {
        doctorDAO.delete(1);

        Doctor doctor = doctorDAO.getById(1);
        assertNull(doctor);
    }

    /**
     * Tests retrieving a doctor by a valid ID.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void getDoctorByIdPositive() throws DoctorDAOException {
        Doctor doctor = doctorDAO.getById(1);
        assertEquals("Chatzis", doctor.getLastname());
    }

    /**
     * Tests retrieving a doctor by an invalid ID (should return null).
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void getDoctorByIdNegative() throws DoctorDAOException {
        Doctor doctor = doctorDAO.getById(5);
        assertNull(doctor);
    }

    /**
     * Tests retrieving doctors by a partial lastname match.
     *
     * @throws DoctorDAOException if DAO operation fails
     */
    @Test
    void getTeacherByLastname() throws DoctorDAOException {
        List<Doctor> doctors = doctorDAO.getByLastname("Ch");
        assertEquals(1, doctors.size());
    }
}
