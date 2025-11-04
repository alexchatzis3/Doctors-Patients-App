package gr.aueb.cf.dpapp.service.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Utility class for managing database connections using Apache Commons DBCP2.
 * Provides a connection pool to improve performance and resource management.
 *
 * <p>No instances of this class should be created.</p>
 */
public class DBUtil {

    /** DataSource for connection pooling. */
    private static final BasicDataSource ds = new BasicDataSource();

    /** Current database connection. */
    private static Connection connection;

    // Static initializer block to configure the connection pool
    static {
        ds.setUrl("jdbc:mysql://localhost:3306/dpdb?serverTimeZone=UTC");
        ds.setUsername("dpuser");
        ds.setPassword(System.getenv("PASS_DP")); // password from environment variable
        ds.setInitialSize(10); // initial number of connections in pool
        ds.setMaxTotal(50);    // max total connections
        ds.setMinIdle(8);      // min idle connections
        ds.setMaxIdle(10);     // max idle connections
    }

    /**
     * Private constructor to prevent instantiation.
     */
    private DBUtil() {
    }

    /**
     * Retrieves a database connection from the connection pool.
     *
     * @return A valid database Connection object.
     * @throws SQLException if unable to obtain a connection.
     */
    public static Connection getConnection() throws SQLException {
        connection = ds.getConnection();
        return connection;
    }

    /**
     * Closes the current connection if it exists.
     */
    public static void closeConnection() {
        try {
            if (connection != null) connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
