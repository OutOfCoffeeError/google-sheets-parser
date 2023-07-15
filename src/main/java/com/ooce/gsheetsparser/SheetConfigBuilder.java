package com.ooce.gsheetsparser;

import java.util.Objects;

class SheetConfig {
	private String url;
	private String sheetID;
	private String outputFormat;
	private String gid;
	
	public String getUrl() {
		return url;
	}

	public String getSheetID() {
		return sheetID;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public String getGid() {
		return gid;
	}

	public static SheetConfigBuilder builder() {
		return new SheetConfigBuilder();
	}
	
	public static class SheetConfigBuilder {
		
		private String url;
		private String sheetID;
		private String outputFormat;
		private String gid;

		private final StringBuilder GOOGLE_DOMAIN = new StringBuilder("https://docs.google.com/spreadsheets/d/");
		private final String GVIZ_FORMAT = "/gviz/tq?tqx=out:";
		private final String GRID_ID = "gid=";
		private final String PARAM_SEPARATOR = "&";
		
		SheetConfigBuilder() {
			this.gid = "0";
		}
		
		public SheetConfigBuilder url(String url) {
			Objects.requireNonNull(url, "URL cannot be Null");
			this.url = url;
			return this;
		}
		
		public SheetConfigBuilder sheetID(String sheetID) {
			Objects.requireNonNull(url, "Sheets ID cannot be Null");
			this.sheetID = sheetID;
			return this;
		}
		
		public SheetConfigBuilder outputFormat(String outputFormat) {
			Objects.requireNonNull(url, "Output Format cannot be Null");
			this.outputFormat = outputFormat;
			return this;
		}
		

		public SheetConfigBuilder outputFormat(OutputFormat outputFormat) {
			Objects.requireNonNull(outputFormat, "Output Format cannot be Null");
			this.outputFormat = outputFormat.toString();
			return this;
		}
		
		public SheetConfigBuilder gid(String gid) {
			Objects.requireNonNull(url, "Grid id (gid) cannot be Null");
			this.gid = gid;
			return this;
		}
	
		
		public SheetConfig build() {
			validate(this);
			SheetConfig conf = new SheetConfig();
			conf.url = generateURL();
			conf.sheetID = sheetID;
			conf.outputFormat = outputFormat;
			conf.gid = gid;
			return conf;
		}
		
		private String generateURL() {
			String url = this.url;
			if(Objects.isNull(url)) {
				url = buildURLFromID();
			} else {
				url = buildURL();
			}
			return url;
		}
		
		private String buildURL() {
			String url = this.url;
			int idx = url.lastIndexOf("edit#");
			if(idx > -1) {
				url = url.substring(0, idx);
			}
			if(url.lastIndexOf("/") != url.length() - 1) {
				url = url.concat("/");
			}
			return url.concat(GVIZ_FORMAT).concat(this.outputFormat)
					.concat(PARAM_SEPARATOR)
					.concat(GRID_ID).concat(this.gid);
		}

		private String buildURLFromID() {
			return GOOGLE_DOMAIN.append(this.sheetID)
					.append(GVIZ_FORMAT).append(this.outputFormat)
					.append(PARAM_SEPARATOR)
					.append(GRID_ID).append(this.gid).toString();
		}

		private boolean validate(SheetConfigBuilder sheetConfigBuilder) {
			if(Objects.isNull(sheetConfigBuilder.url) && Objects.isNull(sheetConfigBuilder.sheetID)) {
				throw new IllegalArgumentException("One Of URL And SheetID Is Required!");
			}
			if(Objects.isNull(sheetConfigBuilder.outputFormat)) throw new IllegalArgumentException("Sheet Output Format Is Required!");;
			return true;
		}
	}
}