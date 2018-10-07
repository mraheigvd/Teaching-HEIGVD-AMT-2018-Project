import ch.heigvd.amt.wp1.data.Database;
import com.mysql.cj.MysqlConnection;
import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
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

        try {
            Connection con = ds.getConnection();
            System.out.println(con.isReadOnly());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
