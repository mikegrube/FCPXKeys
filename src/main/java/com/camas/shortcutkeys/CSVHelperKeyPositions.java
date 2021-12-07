package com.camas.shortcutkeys;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVHelperKeyPositions {

	static String[] HEADERs = {"key", "code", "x", "y", "keySize"};

	public static List<KeyPosition> load(InputStream is) {

		List<KeyPosition> keyPositions = new ArrayList<>();

		try {
			BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
			final CSVFormat csvFormat = CSVFormat.Builder.create()
					.setHeader(HEADERs)
					.setSkipHeaderRecord(true)
					.setIgnoreHeaderCase(true)
					.setTrim(true)
					.build();
			CSVParser csvParser = new CSVParser(fileReader, csvFormat);

			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			long count = 0L;
			for (CSVRecord csvRecord : csvRecords) {
				KeyPosition keyPosition = new KeyPosition(
						++count,
						csvRecord.get("key"),
						Integer.parseInt(csvRecord.get("code")),
						Integer.parseInt(csvRecord.get("x")),
						Integer.parseInt(csvRecord.get("y")),
						KeySize.valueOf(csvRecord.get("keySize"))
				);
				keyPositions.add(keyPosition);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return keyPositions;
	}
}
