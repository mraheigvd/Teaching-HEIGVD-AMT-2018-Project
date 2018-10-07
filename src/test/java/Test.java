import ch.heigvd.amt.wp1.data.Database;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Test {

    public static void main(String[] args) {
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

/*
        try (FileInputStream fis = new BufferedInputStream(new  new FileInputStream(Database.class.getClassLoader().getResource("db_scheme.sql").getFile()))) {
            Connection con = ds.getConnection();
            System.out.println(con.isReadOnly());
            Statement stmt = con.createStatement();
            System.out.println(fis);
            stmt.executeQuery(fis.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger lgr = Logger.getLogger(Database.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }
    */
    }
}
