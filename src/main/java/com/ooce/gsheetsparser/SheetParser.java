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
	
	public String parseAsString() throws HttpException {
		String data = downloadSheet();
		if(_cfg.getOutputFormat().equalsIgnoreCase(OutputFormat.JSON.toString())) {
			return parseToJsonString(data);
		} 
		return data;
	}

	private String parseToJsonString(String data) {
		Objects.requireNonNull(data);
		return data.substring(JSON_DATA_START_IDX, data.length()-JSON_DATA_END_OFFSET);
	}
	
	public void saveToFile(File file) throws IOException, HttpException {
			String data = parseAsString();
			try {
				Files.write(file.toPath(), data.getBytes());
			} catch (IOException e) {
				throw new IOException("Error Writing to given file ", e);
			}
		
	}

	private String downloadSheet() throws HttpException {
		try {
			final HttpGet request = new HttpGet(_cfg.getUrl());
			System.out.println(_cfg.getUrl());
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
