package ch.heigvd.amt.wp1.data.model;

public class User {

    private Integer id;
    private String email;
    private String firstname;
    private String lastname;
    private String password;
    private Boolean isAdmin;
    private Boolean isEnable;
    private String tokenValidate;
    private Boolean password_is_expired;

    public User() {

    }

    public User(String email, String firstname, String lastname, String password, Boolean isAdmin, Boolean isEnable, String tokenValidate, Boolean password_is_expired) {
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isEnable = isEnable;
        this.tokenValidate = tokenValidate;
        this.password_is_expired = password_is_expired;
    }

    public User(Integer id, String email, String firstname, String lastname, String password) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
    }

    public User(Integer id, String email, String firstname, String lastname, String password, Boolean isAdmin, Boolean isEnable, String tokenValidation) {
        this.id = id;
        this.email = email;
        this.firstname = firstname;
        this.lastname = lastname;
        this.password = password;
        this.isAdmin = isAdmin;
        this.isEnable = isEnable;
        this.tokenValidate = tokenValidation;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Boolean enable) {
        isEnable = enable;
    }

    public String getTokenValidate() {
        return tokenValidate;
    }

    public void setTokenValidate(String tokenValidation) {
        this.tokenValidate = tokenValidation;
    }

    public Boolean getPasswordIsExpired() {
        return password_is_expired;
    }

    public void setPasswordIsExpired(Boolean password_is_expired) {
        this.password_is_expired = password_is_expired;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                ", isEnable=" + isEnable +
                ", tokenValidate='" + tokenValidate + '\'' +
                ", passwordIsExpired='" + password_is_expired + '\'' +
                '}';
    }
}
