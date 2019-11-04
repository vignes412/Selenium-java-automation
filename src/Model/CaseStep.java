package Model;

public class CaseStep{
    private String StepId;
    private WebElementLocator Locator;
    private InputData Input;
    private String Action;

    public InputData getInput() {
        return Input;
    }

    public void setInput(InputData input) {
        Input = input;
    }


    public String getStepId() {
        return StepId;
    }

    public void setStepId(String stepId) {
        StepId = stepId;
    }

    public WebElementLocator getLocator() {
        return Locator;
    }

    public void setLocator(WebElementLocator locator) {
        Locator = locator;
    }


    public String getAction() {
        return Action;
    }

    public void setAction(String action) {
        Action = action;
    }
}
