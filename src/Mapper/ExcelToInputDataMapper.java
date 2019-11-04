package Mapper;

import java.util.ArrayList;

import Model.InputData;

public class ExcelToInputDataMapper {
	public ArrayList<InputData> map(ArrayList<String> records) {
		ArrayList<InputData> mappedValue = new ArrayList<InputData>();
		if (records.size() > 0) {
			for (String string : records) {
				String[] temp = string.split("~");
				InputData value = new InputData();
				value.setId(temp[0]);
				value.setLocatorId(temp[1]);
				value.setCaseId(temp[2]);
				value.setName(temp[3]);
				value.setInputValue(temp[4]);
				mappedValue.add(value);
			}
		}
		return mappedValue;
	}
}
