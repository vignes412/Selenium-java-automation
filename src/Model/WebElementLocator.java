package Model;

public class WebElementLocator {
    private String Id;
    private String Name;
    private String Type;
    private String Locator;
    private String URL;
    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }
    public String getType() {
        return Type;
    }
    public void setType(String type) {
        Type = type;
    }
    public String getLocator() {
        return Locator;
    }
    public void setLocator(String xpath) {
        Locator = xpath;
    }
    public String getURL() {
        return URL;
    }
    public void setURL(String uRL) {
        URL = uRL;
    }

}
