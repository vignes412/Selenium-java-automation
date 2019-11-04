package Model;

import java.util.ArrayList;

public class ExcelText {
	private ArrayList<String> Headers;
	private ArrayList<ArrayList<String>> Content;

	public ArrayList<String> getHeaders() {
		return Headers;
	}

	public void setHeaders(ArrayList<String> headers) {
		Headers = headers;
	}

	public ArrayList<ArrayList<String>> getContent() {
		return Content;
	}

	public void setContent(ArrayList<ArrayList<String>> content) {
		Content = content;
	}
}
