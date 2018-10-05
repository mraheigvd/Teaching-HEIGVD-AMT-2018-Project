package ch.heigvd.amt.wp1.business;

import ch.heigvd.amt.wp1.model.App;
import ch.heigvd.amt.wp1.model.User;

import java.util.ArrayList;
import java.util.List;

public class DataBase {
    private List<User> users;
    private List<App> apps;

    public DataBase() {
        users = new ArrayList<User>();
        apps = new ArrayList<App>();
    }

    public void registerNewUser(String email, String password) {

    }

    public void tryLoggin() {
        
    }

    public void getUserProfile() {

    }
}
