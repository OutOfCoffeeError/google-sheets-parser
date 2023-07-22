package com.ooce.gsheetsparser;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

public class SheetParser {
	private SheetConfig _cfg;
	private static final int JSON_DATA_START_IDX = 47;
	private static final int JSON_DATA_END_OFFSET = 2;
	public SheetParser(SheetConfig cfg) {
		Objects.requireNonNull(cfg);
		this._cfg = cfg;
	}
	
	/**
	 * Calls the provided Google sheets URL from the {@code SheetConfig} and fetches the result as a String
	 * @return String format of the Spreadsheet data
	 * @throws HttpException
	 */
	public String parseAsString() throws HttpException {
		String data = downloadSheet();
		if(_cfg.getOutputFormat().equalsIgnoreCase(OutputFormat.JSON.toString())) {
			return parseToJsonString(data);
		} 
		return data;
	}

	/**
	 * Removes the additional data from the returned String of a spreadsheet data and keeps the JSON part
	 * @param data
	 * @return the JSON String from the spreadsheet data
	 */
	private String parseToJsonString(String data) {
		Objects.requireNonNull(data);
		return data.substring(JSON_DATA_START_IDX, data.length()-JSON_DATA_END_OFFSET);
	}
	
	/**
	 * Saves the spreadsheet data to the provided file location
	 * @param file
	 * @throws IOException
	 * @throws HttpException
	 */
	public void saveToFile(File file) throws IOException, HttpException {
			Objects.requireNonNull(file);
			String data = parseAsString();
			try {
				Files.write(file.toPath(), data.getBytes());
			} catch (IOException e) {
				throw new IOException("Error Writing to given file ", e);
			}
		
	}
	
	/**
	 * Invokes the HTTP URL of the Google Sheets to download the spreadsheet data provided in the {@code SheetConfig}
	 * @return Raw data from the Google Sheets
	 * @throws HttpException
	 */
	public String downloadSheet() throws HttpException {
		try {
			final HttpGet request = new HttpGet(_cfg.getUrl());
		    try (CloseableHttpClient client = HttpClientBuilder.create().build();

		        CloseableHttpResponse response = (CloseableHttpResponse) client
		            .execute(request)) {
		    	return EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
		    } 
		} catch (Exception e) {
			throw new HttpException("Error Connecting Google Sheet URL ", e);
		}
	}


}
