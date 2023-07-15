package com.ooce.gsheetsparser;

import java.io.File;
import java.io.IOException;

import org.apache.http.HttpException;

public class Main {
	
	public static void main(String[] args) throws HttpException, IOException {
		SheetConfig cfg = SheetConfig.builder()
							.url("https://docs.google.com/spreadsheets/d/1jp_W4z-ceHoS_lqFclkWXmTKZKq6aIpzZxPf33nwrKI/")
							.outputFormat(OutputFormat.JSON)
							.build();
		SheetParser parser = new SheetParser(cfg);
		String jsonString = parser.parseAsString();
		
		System.out.println(jsonString);
		
		parser.saveToFile(new File("D:\\work\\project\\google-sheets-parser\\google-sheets-parser\\somefile"));
	}

}
