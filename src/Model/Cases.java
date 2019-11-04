package Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Cases {
    private LinkedHashMap<String,ArrayList<CaseStep>> Cases;

    public LinkedHashMap<String, ArrayList<CaseStep>> getCases() {
        return Cases;
    }

    public void setCases(LinkedHashMap<String, ArrayList<CaseStep>> cases) {
        Cases = cases;
    }
}
