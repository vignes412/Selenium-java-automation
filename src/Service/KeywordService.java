package Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Properties;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import Data.EnvironmentVariables;
import ExcelControl.ExcelWriter;
import Model.WebElementLocator;

public class KeywordService {
	WebDriver driver;
	WebDriverWait wait;
	Properties properties;

	public KeywordService() {
		properties = new Properties();
		try (InputStream input = new FileInputStream(EnvironmentVariables.SRCPath + "config.properties")) {
			properties.load(input);
		} catch (IOException e) {
			System.out.println("Properties not loaded");
			e.printStackTrace();
		}
	}

	public void openBrowser(String browserName) {
		if (browserName.equalsIgnoreCase("Firefox")) {
			System.setProperty("webdriver.gecko.driver", properties.getProperty("gecko.path"));
			driver = new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("chrome")) {
			System.setProperty("webdriver.chrome.driver", properties.getProperty("chrome.path"));
			driver = new ChromeDriver();
			wait = new WebDriverWait(driver, 5);
		}
//            else if (browserName.equalsIgnoreCase("IE")) {
//                driver = new InternetExplorerDriver();
//            }
	}

	public void openUrl(String url) {
		driver.navigate().to(url);
	}

	public By locatorValue(String locatorType, String value) {
		By by;
		switch (locatorType) {
		case "id":
			by = By.id(value);
			break;
		case "name":
			by = By.name(value);
			break;
		case "xpath":
			by = By.xpath(value);
			break;
		case "css":
			by = By.cssSelector(value);
			break;
		case "linkText":
			by = By.linkText(value);
			break;
		case "partialLinkText":
			by = By.partialLinkText(value);
			break;
		default:
			by = null;
			break;
		}
		return by;
	}

	public WebElement locatorValue(By element) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(element));
	}

	public void enterText(String locatorType, String value, String text) {
		By locator = locatorValue(locatorType, value);
		WebElement element = locatorValue(locator);
		element.sendKeys(text);
	}

	public void clickOnLocator(String locatorType, String value) {
		By locator = locatorValue(locatorType, value);
		WebElement element = locatorValue(locator);
		if (isElementClickable(locatorType, value))
			element.click();
	}

	public void rest(String milliTime) {
		int milliSec = Integer.parseInt(milliTime);
		try {
			Thread.sleep(milliSec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getText(String locatorType, String value) {
		By locator = locatorValue(locatorType, value);
		WebElement element = locatorValue(locator);
		return element.getText();
	}

	public Boolean isElementPresent(String locatorType, String value) {
		By locator = locatorValue(locatorType, value);
		WebElement element;
		try {
			element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Boolean isElementClickable(String locatorType, String value) {
		By locator = locatorValue(locatorType, value);
		try {
			WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	protected boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void goBack() {
		driver.navigate().back();
	}

	public void createExcelAndHeader(String input) {
		String[] arrayOfExcelDetails = input.split(",");
		String[] header = arrayOfExcelDetails[2].split("-");
		try {
			ExcelWriter.createExcelWithHeader(header, arrayOfExcelDetails[1],
					EnvironmentVariables.ResourcesPath + arrayOfExcelDetails[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void ExtractContentToExcel(ArrayList<WebElementLocator> elements, String input) {
		String[] arrayOfExcelDetails = input.split(",");
		ArrayList<String> contents = new ArrayList<String>(Arrays.asList(arrayOfExcelDetails[2].split("-")));
		ArrayList<String> contentValue = new ArrayList<String>();

		for (WebElementLocator locatorEle : elements) {
			if (contents.contains(locatorEle.getId())) {
				contentValue.add(getText(locatorEle.getType(), locatorEle.getLocator()));
			}
		}
		try {
			ExcelWriter.ExtractContentToExcel(contentValue, arrayOfExcelDetails[1],
					EnvironmentVariables.ResourcesPath + arrayOfExcelDetails[0]);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeBrowser() {
		wait = new WebDriverWait(driver, 2);
		driver.close();
	}

	public boolean verify(String locatorType, String value, String attribute, String valueToCheck) {
		By locator = locatorValue(locatorType, value);
		WebElement element = locatorValue(locator);
		String elementValue = element.getAttribute(attribute);
		if (valueToCheck != null) {
			return valueToCheck.equalsIgnoreCase(elementValue);
		}
		return element != null;
	}

}
