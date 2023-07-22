# Google Sheets Parser

Google Sheets Parser is a Java library that provides an easy and efficient way to parse data from Google Sheets. It utilizes the Google Sheets API to fetch and process spreadsheet data, making it simple to integrate Google Sheets functionality into your Java applications.

[![Build Status](https://img.shields.io/travis/username/repo.svg)](https://travis-ci.org/username/repo)
[![License](https://img.shields.io/badge/license-Apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0)

## Table of Contents

- [Installation](#installation)
- [Usage](#usage)
- [Features](#features)
- [Contributing](#contributing)
- [License](#license)

## Installation

To use the Google Sheets Parser library in your project, you can add the following Maven dependency:

```xml
<dependency>
    <groupId>com.ooce</groupId>
    <artifactId>google-sheets-parser</artifactId>
    <version>0.0.1</version>
</dependency>
```

## Usage
To get started with the library, import the SheetConfig class and build the configuration as per your requirements. In configuration, you have to provide URL or Spreadsheet ID of your Google Sheet document. You have to provide the output format of the document. Currently, Google Sheets support output to JSON, CSV and HTML table format. Also, provide the optional GID to the config.

```Java
SheetConfig cfg = SheetConfig.builder()
				.url("https://docs.google.com/spreadsheets/d/[YOUR_SPREADSHIT_ID]/")
				.outputFormat(OutputFormat.JSON).build();
```

Now create an instance of SheetParser class and pass the config instance created above
```Java
SheetParser parser = new SheetParser(cfg);
```

Now just call parseAsString() or saveToFile() method to get the data from the spreadsheet. 
```Java
String jsonString = parser.parseAsString();
//OR
parser.saveToFile(new File("D:\somefile"));
```
- parseAsString(): Gets the data in String type
- saveToFile(): Saves the data to the file location provided


## Features
Google Sheets Parser currently supports getting the data from Google Sheets URL in a JSON, CSV and HTML format.
More Features will be added soon

## Contributing
Contributions are welcome! To contribute to the Google Sheets Parser library, please follow these steps:

- Fork the repository.
- Create a new branch for your feature or bug fix.
- Make your changes and commit them.
- Push your changes to your fork.
- Submit a pull request to the main branch of the original repository.
- Please ensure that your code adheres to the project's coding style guidelines and includes appropriate tests.

## License

In this modified version, the license badge and the license section have been changed to reflect the Apache License 2.0. Make sure to update the appropriate sections in your actual project with the correct links and information.

Remember to customize other placeholders such as `username/repo`, `com.example`, `google-sheets-parser`, and modify the content based on your library's specific functionality and usage instructions.

Feel free to further modify and tailor this template to suit your project's needs!

