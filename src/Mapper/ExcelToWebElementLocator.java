package Mapper;

import java.util.ArrayList;

import Model.WebElementLocator;

public class ExcelToWebElementLocator {
	public ArrayList<WebElementLocator> map(ArrayList<String> records) {
		ArrayList<WebElementLocator> mappedValue = new ArrayList<WebElementLocator>();
		if (records.size() > 0) {
			for (String string : records) {
				String[] temp = string.split("~");
				WebElementLocator XpathElement = new WebElementLocator();
				XpathElement.setId(temp[0]);
				XpathElement.setName(temp[1]);
				XpathElement.setType(temp[2]);
				XpathElement.setLocator(temp[3]);
				XpathElement.setURL(temp[4]);
				mappedValue.add(XpathElement);
			}
		}
		return mappedValue;
	}
}
