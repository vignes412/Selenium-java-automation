package App;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import Data.EnvironmentVariables;
import Data.KeywordConstant;
import ExcelControl.ExcelReader;
import Mapper.ExcelToCasesMapper;
import Mapper.ExcelToInputDataMapper;
import Mapper.ExcelToWebElementLocator;
import Model.CaseStep;
import Model.Cases;
import Model.InputData;
import Model.WebElementLocator;
import Service.KeywordService;

public class WebAutomation {
	private static ExcelReader xlReader;
	private static KeywordService keywordService;
	private static ExcelToInputDataMapper xlToInputMapper;
	private static ExcelToWebElementLocator xltoWELocator;
	private static ExcelToCasesMapper xltoCases;
	private static ArrayList<InputData> inputData;
	private static Cases cases;
	private static ArrayList<WebElementLocator> webElement;

	public static void Intialize() {
		xlReader = new ExcelReader();
		keywordService = new KeywordService();
		xlToInputMapper = new ExcelToInputDataMapper();
		xltoWELocator = new ExcelToWebElementLocator();
		xltoCases = new ExcelToCasesMapper();
		webElement = xltoWELocator.map(xlReader.GetAllRowDataFromColumn(EnvironmentVariables.Sheet1,
				EnvironmentVariables.ResourcesPath + EnvironmentVariables.WebElementLocatorFileName));
		inputData = xlToInputMapper.map(xlReader.GetAllRowDataFromColumn(EnvironmentVariables.Sheet1,
				EnvironmentVariables.ResourcesPath + EnvironmentVariables.InputControlFileName));
		cases = xltoCases.map(
				xlReader.GetAllRowDataFromColumn(EnvironmentVariables.Sheet1,
						EnvironmentVariables.ResourcesPath + EnvironmentVariables.CasesFileName),
				webElement, inputData);
	}

	public static void main(String[] args) {
		Intialize();
//        System.out.print(cases);
		cases.getCases().entrySet().forEach((entry) -> {
			ExecuteCases(entry.getKey(), entry.getValue(), 0);
		});
	}

//	private static void ExecuteCases(String key, ArrayList<CaseStep> value) {
//		System.out.println(key);
//		for (CaseStep step : value) {
//			switch (step.getAction().toUpperCase()) {
//			case KeywordConstant.OPEN_BROWSER:
//				String browserName = step.getInput().getInputValue();
//				keywordService.openBrowser(browserName);
//				break;
//			case KeywordConstant.OPEN_URL:
//				keywordService.openUrl(step.getInput().getInputValue());
//				break;
//			case KeywordConstant.ENTER_TEXT:
//				keywordService.enterText(step.getLocator().getType(), step.getLocator().getLocator(),
//						step.getInput().getInputValue());
//				break;
//			case KeywordConstant.CLICK_AT:
//				keywordService.clickOnLocator(step.getLocator().getType(), step.getLocator().getLocator());
//				break;
//			case KeywordConstant.REST:
//				keywordService.rest(step.getInput().getInputValue());
//				break;
//			case KeywordConstant.CLOSE_BROWSER:
//				keywordService.closeBrowser();
//				break;
//			case KeywordConstant.REPEAT_SEQUENCE_STEPS:
//				ArrayList<String> valueOfArray = new ArrayList<>(
//						Arrays.asList(step.getInput().getInputValue().split(",")));
//				ArrayList<CaseStep> caseSteps = (ArrayList<CaseStep>) new ArrayList<>(cases.getCases().values())
//						.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
//				ArrayList<CaseStep> repeatedSteps = new ArrayList<CaseStep>();
//				for (CaseStep caseStep : caseSteps) {
//					if (valueOfArray.contains(caseStep.getStepId())) {
//						repeatedSteps.add(caseStep);
//					}
//				}
//				int iteration = 1;
//				Boolean hasValue = true;
//				while (hasValue) {
////					if (iteration > 5)
////						hasValue = false;
//					String locatorFormatValue = String.format(step.getLocator().getLocator(), iteration);
//					if (keywordService.isElementPresent(step.getLocator().getType(), locatorFormatValue)) {
//						try {
//							if (keywordService.isElementClickable(step.getLocator().getType(), locatorFormatValue)) {
//								System.out.print("Execution: " + iteration);
//								ExecuteCases("repeated:" + iteration, repeatedSteps, iteration);
//							}
//						} catch (Exception ex) {
//							break;
//						}
//
//					} else {
//						if (iteration != 1 && iteration != 2)
//							hasValue = false;
//					}
//					iteration++;
//				}
//				break;
//			case KeywordConstant.GET_TEXT:
//				keywordService.getText(step.getLocator().getType(), step.getLocator().getLocator());
//				break;
//			case KeywordConstant.GO_BACK:
//				keywordService.goBack();
//				break;
//			case KeywordConstant.EXTRACT_HEADER_TO_EXCEL:
//				keywordService.createExcelAndHeader(step.getInput().getInputValue());
//				break;
//			case KeywordConstant.EXTRACT_CONTENT_TO_EXCEL:
//				keywordService.ExtractContentToExcel(webElement, step.getInput().getInputValue());
//				break;
////                case KeywordConstant.VERIFY_VALUE:
////                    boolean success =keywordService.verify(commandLine[1],commandLine[2],commandLine[3],commandLine[4]);
////                    if(!success){
////                        throw new Exception("Verify failed for["+command+"]");
////                    }
////                    break;
//			default:
//				break;
//			}
//
//		}
//
//	}

	private static void ExecuteCases(String key, ArrayList<CaseStep> value, int iteration) {
		System.out.println(key);
		for (CaseStep step : value) {
			String formatValue = (iteration > 0 && step.getLocator() != null)
					? String.format(step.getLocator().getLocator(), iteration)
					: step.getLocator() != null ? step.getLocator().getLocator() : "";
			switch (step.getAction().toUpperCase()) {
			case KeywordConstant.OPEN_BROWSER:
				String browserName = step.getInput().getInputValue();
				keywordService.openBrowser(browserName);
				break;
			case KeywordConstant.OPEN_URL:
				keywordService.openUrl(step.getInput().getInputValue());
				break;
			case KeywordConstant.ENTER_TEXT:
				keywordService.enterText(step.getLocator().getType(), formatValue, step.getInput().getInputValue());
				break;
			case KeywordConstant.CLICK_AT:
				System.out.println(formatValue);
				keywordService.clickOnLocator(step.getLocator().getType(), formatValue);
				break;
			case KeywordConstant.REST:
				keywordService.rest(step.getInput().getInputValue());
				break;
			case KeywordConstant.CLOSE_BROWSER:
				keywordService.closeBrowser();
				break;
			case KeywordConstant.REPEAT_SEQUENCE_STEPS:
				ArrayList<String> valueOfArray = new ArrayList<>(
						Arrays.asList(step.getInput().getInputValue().split(",")));
				ArrayList<CaseStep> caseSteps = (ArrayList<CaseStep>) new ArrayList<>(cases.getCases().values())
						.stream().flatMap(x -> x.stream()).collect(Collectors.toList());
				ArrayList<CaseStep> repeatedSteps = new ArrayList<CaseStep>();
				for (CaseStep caseStep : caseSteps) {
					if (valueOfArray.contains(caseStep.getStepId())) {
						repeatedSteps.add(caseStep);
					}
				}
				int increament = 1;
				Boolean hasValue = true;
				while (hasValue) {
//					if (increament > 5)
//						hasValue = false;
					String locatorFormatValue = String.format(step.getLocator().getLocator(), increament);
					if (keywordService.isElementPresent(step.getLocator().getType(), locatorFormatValue)) {
						try {
							if (keywordService.isElementClickable(step.getLocator().getType(), locatorFormatValue)) {
								System.out.println("Execution: " + increament);
								ExecuteCases("repeated:" + increament, repeatedSteps, increament);
							}
						} catch (Exception ex) {
							continue;
						}

					} else {
						if (increament != 1 && increament != 2)
							hasValue = false;
					}
					increament++;
				}
				break;
			case KeywordConstant.GET_TEXT:
				keywordService.getText(step.getLocator().getType(), step.getLocator().getLocator());
				break;
			case KeywordConstant.GO_BACK:
				keywordService.goBack();
				break;
			case KeywordConstant.EXTRACT_HEADER_TO_EXCEL:
				keywordService.createExcelAndHeader(step.getInput().getInputValue());
				break;
			case KeywordConstant.EXTRACT_CONTENT_TO_EXCEL:
				keywordService.ExtractContentToExcel(webElement, step.getInput().getInputValue());
				break;
//                case KeywordConstant.VERIFY_VALUE:
//                    boolean success =keywordService.verify(commandLine[1],commandLine[2],commandLine[3],commandLine[4]);
//                    if(!success){
//                        throw new Exception("Verify failed for["+command+"]");
//                    }
//                    break;
			default:
				break;
			}

		}

	}

}
