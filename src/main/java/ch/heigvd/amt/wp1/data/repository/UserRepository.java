package ch.heigvd.amt.wp1.data.repository;

import ch.heigvd.amt.wp1.data.Database;
import ch.heigvd.amt.wp1.data.model.User;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

@Stateless
public class UserRepository {

    private final static String TABLE_NAME = "user";

    @Resource(lookup = "jdbc/amt")
    private DataSource database;

    public User findByEmail(String email) {
        User user = new User();
        try {
            PreparedStatement prepare = database.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE email = ?");
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
        }
        return null;
    }

    public User findById(Long id) {
        User user = new User();
        try {
            PreparedStatement prepare = database.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?");
            prepare.setLong(1, id);
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
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Hint: http://java.avdiel.com/Tutorials/JDBCPaging.html
    public List<User> findAll() {
        List<User> users = new LinkedList<User>();
        try {
            PreparedStatement prepare = database.getConnection().prepareStatement("SELECT * FROM " + TABLE_NAME);
            ResultSet result = prepare.executeQuery();
            while(result.next()) {
                User user = new User();
                user.setId(result.getInt("id"));
                user.setFirstname(result.getString("firstname"));
                user.setLastname(result.getString("lastname"));
                user.setEmail(result.getString("email"));
                user.setPassword(result.getString("password"));
                user.setIsAdmin(result.getBoolean("is_admin"));
                user.setIsEnable(result.getBoolean("is_enable"));
                user.setTokenValidate(result.getString("token_validate"));
                user.setPasswordIsExpired(result.getBoolean("password_is_expired"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public User create(User user) {
        try {
            PreparedStatement statement = database.getConnection().prepareStatement(
                    "INSERT INTO " +
                            TABLE_NAME +"(email, firstname, lastname, password, is_admin, is_enable, token_validate) " +
                            "VALUES(?, ?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.getIsAdmin());
            statement.setBoolean(6, user.getIsEnable());
            statement.setString(7, user.getTokenValidate());

            //verifie le resultat de l'execution de la requete SQL
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Updates failed");
            }
            user.setId(findByEmail(user.getEmail()).getId());
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean update(User user) {
        try {
            String sql = "UPDATE " +
                    TABLE_NAME +
                    " SET email = ?, " +
                    "firstname = ?, " +
                    "lastname = ?, " +
                    "password = ?, " +
                    "is_admin = ?, " +
                    "is_enable = ?, " +
                    "token_validate = ?, " +
                    "password_is_expired = ? " +
                    "WHERE id = ?";
            PreparedStatement statement = database.getConnection().prepareStatement(sql);
            statement.setString(1, user.getEmail());
            statement.setString(2, user.getFirstname());
            statement.setString(3, user.getLastname());
            statement.setString(4, user.getPassword());
            statement.setBoolean(5, user.getIsAdmin());
            statement.setBoolean(6, user.getIsEnable());
            statement.setString(7, user.getTokenValidate());
            statement.setBoolean(8, user.getPasswordIsExpired());
            statement.setLong(9, user.getId());

            System.out.println("REQUESTSQL : " + sql);
            //verifie le resultat de l'execution de la requete SQL
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Updates failed");
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean disable(User user) {
        try {
            PreparedStatement statement = database.getConnection().prepareStatement(
                    "UPDATE " +
                            TABLE_NAME +" SET is_enable = ? " +
                            "WHERE id = ?"
            );
            statement.setBoolean(1, false);
            statement.setLong(2, user.getId());

            if (statement.executeUpdate() == 0) {
                throw new SQLException("Updates failed");
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(User user) {
        if (user.getId() != null) {

            String sql = "DELETE FROM " + TABLE_NAME + " WHERE id = ?";
            try {
                PreparedStatement prepare = database.getConnection().prepareStatement(sql);
                prepare.setLong(1, user.getId());

                //verifie le resultat de l'execution de la requete SQL
                if (prepare.executeUpdate() == 0) {
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
