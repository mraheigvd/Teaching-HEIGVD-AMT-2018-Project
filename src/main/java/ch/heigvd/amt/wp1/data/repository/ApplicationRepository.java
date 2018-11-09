package ch.heigvd.amt.wp1.data.repository;

import ch.heigvd.amt.wp1.data.Database;
import ch.heigvd.amt.wp1.data.model.Application;
import ch.heigvd.amt.wp1.data.model.User;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class ApplicationRepository {

    private final static String TABLE_NAME = "application";

    @Resource(lookup = "jdbc/amt")
    private DataSource database;

    //private Database database = Database.getInstance();

    public List<Application> findByUser(User user) {
        List<Application> applications = new LinkedList<>();
        try {
            String sql = "SELECT * FROM " + TABLE_NAME +
                    " INNER JOIN user_application ON user_application.fk_application = application.id " +
                    "AND user_application.fk_user = ?";
            PreparedStatement prepare = database.getConnection().prepareStatement(sql);
            prepare.setLong(1, user.getId());
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                Application application = new Application(result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("app_key"),
                        result.getString("app_token"));
                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public List<Application> findPageByUser(User user, int pageNbr, int nbrPerPages) {
        List<Application> applications = new LinkedList<>();
        try {
            String sql = "SELECT * FROM " + TABLE_NAME +
                    " INNER JOIN user_application ON user_application.fk_application = application.id " +
                    "AND user_application.fk_user = ?"
                    + " LIMIT " + nbrPerPages + " OFFSET " + ((pageNbr - 1) * nbrPerPages);

            PreparedStatement prepare = database.getConnection().prepareStatement(sql);
            prepare.setLong(1, user.getId());
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                Application application = new Application(result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("app_key"),
                        result.getString("app_token"));
                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public int getCountByUser(User user) {
        try {
            String sql = "SELECT count(application.id) FROM " + TABLE_NAME +
                    " INNER JOIN user_application ON user_application.fk_application = application.id " +
                    "AND user_application.fk_user = ?";
            PreparedStatement prepare = database.getConnection().prepareStatement(sql);
            prepare.setLong(1, user.getId());
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Application findById(Long id) {
        Application application = null;
        try {
            PreparedStatement prepare = database.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?");
            prepare.setLong(1, id);
            ResultSet result = prepare.executeQuery();
            if(result.next()) {
                application = new Application(result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("app_key"),
                        result.getString("app_token"));
                return application;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return application;
    }

    // Hint: http://java.avdiel.com/Tutorials/JDBCPaging.html
    public List<Application> findPage(int pageNbr, int nbrPerPages) {
        List<Application> applications = new LinkedList<>();
        try {
            String sql = "SELECT * FROM " + TABLE_NAME
                    + " LIMIT " + nbrPerPages + " OFFSET " + ((pageNbr - 1) * nbrPerPages);
            PreparedStatement prepare = database.getConnection().prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                Application application = new Application(result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("app_key"),
                        result.getString("app_token"));
                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public List<Application> findAll() {
        List<Application> applications = new LinkedList<>();
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            PreparedStatement prepare = database.getConnection().prepareStatement(sql);
            ResultSet result = prepare.executeQuery();
            while (result.next()) {
                Application application = new Application(result.getInt("id"),
                        result.getString("name"),
                        result.getString("description"),
                        result.getString("app_key"),
                        result.getString("app_token"));
                applications.add(application);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return applications;
    }

    public Application create(Application application, User user) {
        try {
            // Create the application
            PreparedStatement statement = database.getConnection().prepareStatement(
                    "INSERT INTO " +
                            TABLE_NAME +"(name, description, app_key, app_token) " +
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
            PreparedStatement statement2 = database.getConnection().prepareStatement(
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
        }
        return null;
    }

    public boolean update(Application application, User user) {
        try {
            // Check if application is owned by user
            PreparedStatement prepare = database.getConnection().prepareStatement("SELECT fk_application " +
                    "FROM user_application " +
                    "WHERE fk_application = ? AND fk_user = ? ");
            prepare.setLong(1, application.getId());
            prepare.setLong(2, user.getId());
            ResultSet result = prepare.executeQuery();
            if(result.next()) {
                // Update if application is owned by user
                String sql = "UPDATE " +
                        TABLE_NAME +
                        " SET name = ?, " +
                        "description = ?, " +
                        "app_key = ?, " +
                        "app_token = ? " +
                        "WHERE id = ?";
                PreparedStatement statement = database.getConnection().prepareStatement(sql);
                statement.setString(1, application.getName());
                statement.setString(2, application.getDescription());
                statement.setString(3, application.getAppKey());
                statement.setString(4, application.getAppToken());
                statement.setLong(5, application.getId());

                //verifie le resultat de l'execution de la requete SQL
                if (statement.executeUpdate() == 0) {
                    throw new SQLException("Updates failed");
                }
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(Application application, User user) {
        if (application.getId() != null) {

            String sql = "DELETE FROM user_application WHERE fk_application = ? AND fk_user = ?";
            try {
                PreparedStatement prepare = database.getConnection().prepareStatement(sql);
                prepare.setLong(1, application.getId());
                prepare.setLong(2, user.getId());


                if (prepare.executeUpdate() == 0) {
                    return false;
                }

                sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
                PreparedStatement prepare2 = database.getConnection().prepareStatement(sql);
                prepare2.setLong(1, application.getId());

                if (prepare2.executeUpdate() == 0) {
                    return false;
                }

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
}
