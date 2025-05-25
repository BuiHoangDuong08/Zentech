package helper;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import javax.sql.DataSource;

public class ConnectionHelper {
    private static DataSource dataSource;

    static {
        try (InputStream input = ConnectionHelper.class.getResourceAsStream("/config/db.properties")) {
            Properties props = new Properties();
            props.load(input);
            // Initialize DataSource
            dataSource = DataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error loading DB configuration", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}

