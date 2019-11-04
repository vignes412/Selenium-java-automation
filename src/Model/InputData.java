package Model;

public class InputData {
    private String Id;
    private String LocatorId;
    private String CaseId;
    private String Name;


    public String getCaseId() {
        return CaseId;
    }

    public void setCaseId(String caseId) {
        CaseId = caseId;
    }

    public String getLocatorId() {
        return LocatorId;
    }

    public void setLocatorId(String locatorId) {
        LocatorId = locatorId;
    }

    private String InputValue;

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

    public String getInputValue() {
        return InputValue;
    }

    public void setInputValue(String inputValue) {
        InputValue = inputValue;
    }
}
