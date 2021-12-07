package com.camas.shortcutkeys;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSVHelperCombinations {

	static String[] HEADERs = {"description", "area", "command", "combination", "key", "commandKey", "shiftKey", "optionKey", "controlKey"};

	public static List<Combination> load(InputStream is) {

		List<Combination> combinations = new ArrayList<>();

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
				Combination combination = new Combination(
						++count,
						csvRecord.get("description"),
						csvRecord.get("area"),
						csvRecord.get("command"),
						csvRecord.get("combination"),
						csvRecord.get("key"),
						0,	//Placeholder for code
						Integer.parseInt(csvRecord.get("commandKey")),
						Integer.parseInt(csvRecord.get("shiftKey")),
						Integer.parseInt(csvRecord.get("optionKey")),
						Integer.parseInt(csvRecord.get("controlKey"))
				);
				combinations.add(combination);
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return combinations;
	}
}