package com.ooce.gsheetsparser;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.StringReader;
import java.util.Objects;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.http.HttpException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TestSheetParser {
	private String SHEET_JSON_DATA = "/*O_o*/\r\n" + 
			"google.visualization.Query.setResponse({\"version\":\"0.6\",\"reqId\":\"0\",\"status\":\"ok\",\"sig\":\"2137921698\",\"table\":{\"cols\":[{\"id\":\"A\",\"label\":\"SECURITY\",\"type\":\"string\"},{\"id\":\"B\",\"label\":\"INVESTED\",\"type\":\"number\",\"pattern\":\"General\"},{\"id\":\"C\",\"label\":\"SOLD AT LTP\",\"type\":\"number\",\"pattern\":\"General\"},{\"id\":\"D\",\"label\":\"QTY SOLD\",\"type\":\"number\",\"pattern\":\"General\"},{\"id\":\"E\",\"label\":\"PROFIT\",\"type\":\"number\",\"pattern\":\"General\"},{\"id\":\"F\",\"label\":\"\",\"type\":\"number\",\"pattern\":\"General\"}],\"rows\":[{\"c\":[{\"v\":\"BAJFINANCE\"},{\"v\":17882.0,\"f\":\"17882\"},{\"v\":7555.0,\"f\":\"7555\"},{\"v\":3.0,\"f\":\"3\"},{\"v\":4780.0,\"f\":\"4780\"},{\"v\":null}]},{\"c\":[{\"v\":\"DAAWAT\"},{\"v\":2508.0,\"f\":\"2508\"},{\"v\":129.0,\"f\":\"129\"},{\"v\":34.0,\"f\":\"34\"},{\"v\":1860.0,\"f\":\"1860\"},{\"v\":null}]},{\"c\":[{\"v\":\"PHOENIXLTD\"},{\"v\":2938.0,\"f\":\"2938\"},{\"v\":1570.0,\"f\":\"1570\"},{\"v\":3.0,\"f\":\"3\"},{\"v\":1772.0,\"f\":\"1772\"},{\"v\":null}]},{\"c\":[{\"v\":\"ESCORTS\"},{\"v\":3044.0,\"f\":\"3044\"},{\"v\":2300.0,\"f\":\"2300\"},{\"v\":2.0,\"f\":\"2\"},{\"v\":1556.0,\"f\":\"1556\"},{\"v\":null}]},{\"c\":[{\"v\":\"M&M\"},{\"v\":2794.0,\"f\":\"2794\"},{\"v\":1557.0,\"f\":\"1557\"},{\"v\":3.0,\"f\":\"3\"},{\"v\":1878.0,\"f\":\"1878\"},{\"v\":null}]},{\"c\":[{\"v\":\"VRLLOG\"},{\"v\":9180.0,\"f\":\"9180\"},{\"v\":752.0,\"f\":\"752\"},{\"v\":18.0,\"f\":\"18\"},{\"v\":4363.0,\"f\":\"4363\"},{\"v\":null}]},{\"c\":[null,{\"v\":38346.0,\"f\":\"38346\"},null,null,{\"v\":16209.0,\"f\":\"16209\"},{\"v\":54555.0,\"f\":\"54555\"}]}],\"parsedNumHeaders\":1}});";
	
	private String SHEET_CSV_DATA = "\"SECURITY\",\"INVESTED\",\"SOLD AT LTP\",\"QTY SOLD\",\"PROFIT\",\"\"\r\n" + 
			"\"BAJFINANCE\",\"17882\",\"7555\",\"3\",\"4780\",\"\"\r\n" + 
			"\"DAAWAT\",\"2508\",\"129\",\"34\",\"1860\",\"\"\r\n" + 
			"\"PHOENIXLTD\",\"2938\",\"1570\",\"3\",\"1772\",\"\"\r\n" + 
			"\"ESCORTS\",\"3044\",\"2300\",\"2\",\"1556\",\"\"\r\n" + 
			"\"M&M\",\"2794\",\"1557\",\"3\",\"1878\",\"\"\r\n" + 
			"\"VRLLOG\",\"9180\",\"752\",\"18\",\"4363\",\"\"\r\n" + 
			"\"\",\"38346\",\"\",\"\",\"16209\",\"54555\"";
	
	private String SHEET_BAD_CSV_DATA = "a,b,\"c\",d \r\n" + 
			"a,b,\"\"c\"\"\",d";
	
	@Before
	public void setup() {
		
	}

	@Test
	public void testJSONSheet() throws HttpException {
		SheetConfig cfg = SheetConfig.builder()
				.url("sample URL")
				.outputFormat(OutputFormat.JSON).build();
		SheetParser parser = new SheetParser(cfg);
		SheetParser spy = Mockito.spy(parser);
		Mockito.doReturn(SHEET_JSON_DATA).when(spy).downloadSheet();
		String jsonString = spy.parseAsString();
		assertEquals(true, Objects.nonNull(jsonString));
	}
	
	@Test
	public void testParse_ValidCSV() throws HttpException {
		SheetConfig cfg = SheetConfig.builder()
				.url("sample URL")
				.outputFormat(OutputFormat.CSV).build();
		SheetParser parser = new SheetParser(cfg);
		SheetParser spy = Mockito.spy(parser);
		Mockito.doReturn(SHEET_CSV_DATA).when(spy).downloadSheet();
		String csvString = spy.parseAsString();
		assertEquals(true, isValidCSV(csvString));
	}

	@Test
	public void testParse_InvalidCSV() throws HttpException {
		SheetConfig cfg = SheetConfig.builder()
				.url("sample URL")
				.outputFormat(OutputFormat.CSV).build();
		SheetParser parser = new SheetParser(cfg);
		SheetParser spy = Mockito.spy(parser);
		Mockito.doReturn(SHEET_BAD_CSV_DATA).when(spy).downloadSheet();
		String csvString = spy.parseAsString();
		assertEquals(false, isValidCSV(csvString));
	}
	
	@SuppressWarnings("resource")
	public static boolean isValidCSV(String csvString) {
        try (StringReader reader = new StringReader(csvString)) {
            CSVFormat csvFormat = CSVFormat.DEFAULT;
            new CSVParser(reader, csvFormat);
            return true; // If parsing and iteration pass without exceptions, it's valid CSV
        } catch (IOException e) {
            return false; // Parsing failed, not a valid CSV
        }
    }

}
