package ch.heigvd.amt.wp1.data.repository;

import ch.heigvd.amt.wp1.data.Database;
import ch.heigvd.amt.wp1.data.model.Application;
import ch.heigvd.amt.wp1.data.model.User;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ApplicationRepository {

    private final static String TABLE_NAME = "application";

    @Resource(lookup = "jdbc/amt")
    private DataSource database;

    //private Database database = Database.getInstance();

    public List<Application> findByUser(User user) {
        List<Application> applications = new LinkedList<>();
        Connection connection = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME +
                    " INNER JOIN user_application ON user_application.fk_application = application.id " +
                    "AND user_application.fk_user = ?";
            connection = database.getConnection();
            PreparedStatement prepare = connection.prepareStatement(sql);
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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return applications;
    }

    public List<Application> findPageByUser(User user, int pageNbr, int nbrPerPages) {
        List<Application> applications = new LinkedList<>();
        Connection connection = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME +
                    " INNER JOIN user_application ON user_application.fk_application = application.id " +
                    "AND user_application.fk_user = ?"
                    + " LIMIT ? OFFSET ?";
            connection = database.getConnection();
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setLong(1, user.getId());
            prepare.setInt(2, nbrPerPages);
            prepare.setInt(3, (pageNbr - 1) * nbrPerPages);
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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return applications;
    }

    public int getCountByUser(User user) {
        Connection connection = null;
        try {
            String sql = "SELECT count(application.id) FROM " + TABLE_NAME +
                    " INNER JOIN user_application ON user_application.fk_application = application.id " +
                    "AND user_application.fk_user = ?";
            connection = database.getConnection();
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setLong(1, user.getId());
            ResultSet result = prepare.executeQuery();
            if (result.next()) {
                return result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    public Application findById(Long id) {
        Application application = null;
        Connection connection = null;
        try {
            connection = database.getConnection();
            PreparedStatement prepare = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?");
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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return application;
    }

    public List<Application> findPage(int pageNbr, int nbrPerPages) {
        List<Application> applications = new LinkedList<>();
        Connection connection = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME + " LIMIT ? OFFSET ?";
            connection = database.getConnection();
            PreparedStatement prepare = connection.prepareStatement(sql);
            prepare.setInt(1, nbrPerPages);
            prepare.setInt(2, (pageNbr - 1) * nbrPerPages);
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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return applications;
    }

    public List<Application> findAll() {
        List<Application> applications = new LinkedList<>();
        Connection connection = null;
        try {
            String sql = "SELECT * FROM " + TABLE_NAME;
            connection = database.getConnection();
            PreparedStatement prepare = connection.prepareStatement(sql);
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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return applications;
    }

    public Application create(Application application, User user) {
        Connection connection = null;
        try {
            // Create the application
            connection = database.getConnection();
            PreparedStatement statement = connection.prepareStatement(
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

            throw new SQLException("BOOM");

            /*
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

            return application;*/
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //return null;
    }

    public boolean update(Application application, User user) {
        Connection connection = null;
        try {
            // Check if application is owned by user
            connection = database.getConnection();
            PreparedStatement prepare = connection.prepareStatement("SELECT fk_application " +
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
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean delete(Application application, User user) {
        Connection connection = null;
        if (application.getId() != null) {
            String sql = "DELETE FROM user_application WHERE fk_application = ? AND fk_user = ?";
            try {
                connection = database.getConnection();
                PreparedStatement prepare = connection.prepareStatement(sql);
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
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
}
