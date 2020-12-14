package com.hmsapp.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hmsapp.model.LocationStats;

@Service
public class LocationService {
	private static final Logger logger = LoggerFactory.getLogger(LocationService.class);

	private Map<String, LocationStats> allStats = new HashMap<String, LocationStats>();


	public Map<String, LocationStats> getAllStats() {
		return allStats;
	}

	public static String getResourceFileAsString(String fileName) {
		InputStream is = getResourceFileAsInputStream(fileName);
		if (is != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			return (String) reader.lines().collect(Collectors.joining(System.lineSeparator()));
		} else {
			throw new RuntimeException("resource not found");
		}
	}

	public static InputStream getResourceFileAsInputStream(String fileName) {
		ClassLoader classLoader = LocationService.class.getClassLoader();
		return classLoader.getResourceAsStream(fileName);
	}

	@PostConstruct
	public void fetchLocationData() throws IOException, InterruptedException { // List<LocationStats>
		List<LocationStats> newStats = new ArrayList<>();
		try {
			String csv = getResourceFileAsString("us-zip-code-latitude-and-longitude.csv");

			StringReader csvBodyReader = new StringReader(csv);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().withDelimiter(';')
					.parse(csvBodyReader);
			for (CSVRecord record : records) {
				if (record.get("Zip") != null && !record.get("Zip").isEmpty()) {
					LocationStats location = new LocationStats();
					location.setLatitude(Double.parseDouble(record.get("Latitude")));
					location.setLongitude(Double.parseDouble(record.get("Longitude")));
					location.setZipCode(record.get("Zip"));

					this.allStats.put(record.get("Zip"), location);
				}
			}
		} catch (Exception e) {
			logger.error("error while loading location data", e);
		}

	}
}
