package Mapper;

import Model.CaseStep;
import Model.InputData;
import Model.Cases;
import Model.WebElementLocator;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ExcelToCasesMapper {
    public Cases map(ArrayList<String> records, ArrayList<WebElementLocator> weLocators, ArrayList<InputData> inputData) {
        Cases navSteps = new Cases();
        LinkedHashMap<String, ArrayList<CaseStep>> steps = new LinkedHashMap<String, ArrayList<CaseStep>>();
        ArrayList<CaseStep> caseSteps = new ArrayList<CaseStep>();
        if (records.size() > 0) {
            for (String string : records) {
                String[] temp = string.split("~");
                if (steps.containsKey(temp[0])) {
                    caseSteps = steps.get(temp[0]);
                    CaseStep value = new CaseStep();
                    value.setStepId(temp[1]);
                    value.setLocator((WebElementLocator) weLocators.stream().filter(x->x.getId().toLowerCase().equals(temp[2]))
                            .findAny()
                            .orElse(null));
                    value.setInput((InputData) inputData.stream().filter(x->x.getId().toLowerCase().equals(temp[3]))
                            .findAny()
                            .orElse(null));
                    value.setAction(temp[4]);
                    caseSteps.add(value);
                    steps.put(temp[0], caseSteps);
                } else {
                    ArrayList<CaseStep> newCaseSteps = new ArrayList<CaseStep>();
                    CaseStep value = new CaseStep();
                    value.setStepId(temp[1]);
                    value.setLocator((WebElementLocator) weLocators.stream().filter(x->x.getId().toLowerCase().equals(temp[2]))
                            .findAny()
                            .orElse(null));
                    value.setInput((InputData) inputData.stream().filter(x->x.getId().toLowerCase().equals(temp[3]))
                            .findAny()
                            .orElse(null));
                    value.setAction(temp[4]);
                    newCaseSteps.add(value);
                    steps.put(temp[0], newCaseSteps);
                }
            }
            navSteps.setCases(steps);
        }
        return navSteps;
    }
}
