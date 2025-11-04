package gr.aueb.cf.dpapp.dao.util;

import gr.aueb.cf.dpapp.service.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility class for database operations such as inserting test data
 * or erasing all data from the database tables.
 * <p>
 * This class provides static helper methods and cannot be instantiated.
 * </p>
 */
public class DBHelper {

    /**
     * Private constructor to prevent instantiation.
     */
    private DBHelper() {
        // No instances allowed
    }

    /**
     * Deletes all data from all tables in the 'dpdb' database and resets auto-increment counters.
     *
     * @throws SQLException if a database access error occurs.
     */
    public static void eraseData() throws SQLException {
        // Disable foreign key checks to allow truncating tables
        String sqlFkOff = "SET @@foreign_key_checks = 0";
        String sqlFkOn = "SET @@foreign_key_checks = 1";
        String sqlSelect = "SELECT TABLE_NAME FROM information_schema.tables WHERE TABLE_SCHEMA = 'dpdb'";
        ResultSet rs = null;

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps1 = connection.prepareStatement(sqlFkOff);
             PreparedStatement ps2 = connection.prepareStatement(sqlSelect)) {

            ps1.executeUpdate(); // Disable FK checks
            rs = ps2.executeQuery(); // Get all table names
            List<String> tables = mapRsToList(rs);

            for (String table : tables) {
                // Delete all rows from the table
                connection.prepareStatement("DELETE FROM " + table).executeUpdate();
                // Reset AUTO_INCREMENT counter
                connection.prepareStatement("ALTER TABLE " + table + " AUTO_INCREMENT=1").executeUpdate();
            }

            connection.prepareStatement(sqlFkOn).executeUpdate(); // Re-enable FK checks

        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * Inserts a doctor into the 'doctors' table.
     *
     * @param id        The doctor's ID.
     * @param firstname The doctor's first name.
     * @param lastname  The doctor's last name.
     * @throws SQLException if a database access error occurs.
     */
    public static void insertDoctor(int id, String firstname, String lastname) throws SQLException {
        String sql = "INSERT INTO doctors(id, firstname, lastname) VALUES (?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, firstname);
            ps.setString(3, lastname);
            ps.executeUpdate();
        }
    }

    /**
     * Inserts a patient into the 'patients' table.
     *
     * @param id        The patient's ID.
     * @param firstname The patient's first name.
     * @param lastname  The patient's last name.
     * @throws SQLException if a database access error occurs.
     */
    public static void insertPatient(int id, String firstname, String lastname) throws SQLException {
        String sql = "INSERT INTO patients(id, firstname, lastname) VALUES (?, ?, ?)";

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.setString(2, firstname);
            ps.setString(3, lastname);
            ps.executeUpdate();
        }
    }

    /**
     * Maps a ResultSet of table names to a list of strings.
     *
     * @param rs The ResultSet containing the table names.
     * @return A list of table names.
     * @throws SQLException if a database access error occurs.
     */
    private static List<String> mapRsToList(ResultSet rs) throws SQLException {
        List<String> tables = new ArrayList<>();

        while (rs.next()) {
            tables.add(rs.getString("TABLE_NAME"));
        }
        return tables;
    }
}
