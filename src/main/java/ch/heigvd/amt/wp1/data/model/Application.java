package ch.heigvd.amt.wp1.data.model;

public class Application {

    private Integer id;
    private String name;
    private String description;
    private String appKey;
    private String appToken;

    public Application() {
    }

    public Application(String name, String description, String appKey, String appToken) {
        this.name = name;
        this.description = description;
        this.appKey = appKey;
        this.appToken = appToken;
    }

    public Application(Integer id, String name, String description, String appKey, String appToken) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.appKey = appKey;
        this.appToken = appToken;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppToken() {
        return appToken;
    }

    public void setAppToken(String appToken) {
        this.appToken = appToken;
    }

}
