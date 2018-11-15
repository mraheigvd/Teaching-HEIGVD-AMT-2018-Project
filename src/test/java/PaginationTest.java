
import ch.heigvd.amt.wp1.data.Database;
import ch.heigvd.amt.wp1.data.model.Application;
import ch.heigvd.amt.wp1.data.model.User;
import ch.heigvd.amt.wp1.util.PasswordAuthentication;
import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PaginationTest {
    private String email = "test@test.test";
    private String name = "name";
    private String desc = "desc";
    private int amount = 1000;

    @Test
    public void generateApplications() {
        User user = findByEmail(email);
        for(int i = 0; i < amount; i++) {
            Application application = getNewApplication(String.valueOf(i));
            create(application, user);
        }
    }

    private Application getNewApplication(String suffix) {
        return new Application(name + suffix,
                desc + suffix,
                PasswordAuthentication.generateAlphanumString(20),
                PasswordAuthentication.generateAlphanumString(50));
    }



    public Connection getConnection() {
        Properties props = new Properties();
        String fileName = "db.properties";

        try (FileInputStream fis = new FileInputStream(Database.class.getClassLoader().getResource(fileName).getFile())) {
            props.load(fis);
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }

        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL(props.getProperty("mysql.url"));
        ds.setUser(props.getProperty("mysql.username"));
        ds.setPassword(props.getProperty("mysql.password"));

        try {
            return ds.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findByEmail(String email) {
        User user = new User();
        Connection connection = null;
        try {
            connection = getConnection();
            PreparedStatement prepare = connection.prepareStatement("SELECT * FROM " + "user" + " WHERE email = ?");
            prepare.setString(1, email);
            ResultSet result = prepare.executeQuery();
            if(result.next()) {
                user.setId(result.getInt("id"));
                user.setFirstname(result.getString("firstname"));
                user.setLastname(result.getString("lastname"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setIsAdmin(result.getBoolean("is_admin"));
                user.setIsEnable(result.getBoolean("is_enable"));
                user.setTokenValidate(result.getString("token_validate"));
                user.setPasswordIsExpired(result.getBoolean("password_is_expired"));
                System.out.println(user);
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Application create(Application application, User user) {
        Connection connection = null;
        try {
            // Create the application
            connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " +
                            "application" +"(name, description, app_key, app_token) " +
                            "VALUES(?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS
            );
            statement.setString(1, application.getName());
            statement.setString(2, application.getDescription());
            statement.setString(3, application.getAppKey());
            statement.setString(4, application.getAppToken());

            // Associate the application to the user
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Error");
            }

            ResultSet keys = statement.getGeneratedKeys();

            int count = 0;

            keys.next();
            int key = keys.getInt(1);
            application.setId(key);

            // Create the application
            PreparedStatement statement2 = connection.prepareStatement(
                    "INSERT INTO user_application (fk_user, fk_application) " +
                            "VALUES(?, ?)"
            );
            statement2.setLong(1, user.getId());
            statement2.setLong(2, application.getId());

            // Associate the application to the user
            if (statement2.executeUpdate() == 0) {
                throw new SQLException("Updates failed");
            }

            return application;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
