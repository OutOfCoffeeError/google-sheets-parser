package com.ooce.gsheetsparser;

import static org.junit.Assert.assertEquals;

import java.util.Objects;

import org.apache.http.HttpException;
import org.junit.Test;

public class TestSheetParser {

	@Test
	public void testJSONSheet() throws HttpException {
		SheetConfig cfg = SheetConfig.builder()
				.url("https://docs.google.com/spreadsheets/d/1jp_W4z-ceHoS_lqFclkWXmTKZKq6aIpzZxPf33nwrKI/")
				.outputFormat(OutputFormat.JSON).build();
		SheetParser parser = new SheetParser(cfg);
		String jsonString = parser.parseAsString();
		assertEquals(true, Objects.nonNull(jsonString));
	}

}
