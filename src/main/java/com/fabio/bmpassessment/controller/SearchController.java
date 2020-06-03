package com.fabio.bmpassessment.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fabio.bmpassessment.entities.CsvParse;
import com.fabio.bmpassessment.util.TextUtil;
import com.fabio.bmpassessment.validators.LimitConstraint;
import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

@RestController
@Validated
@RequestMapping("/counter-api")
public class SearchController {
	@Value( "${spring.filename}" )
    private String filename;

	@PostMapping("/search")
	public ResponseEntity<String> search(@RequestBody Map<String, String[]> inputMap) {
		JSONObject response 	= new JSONObject();
		JSONArray responseArray = new JSONArray();
		
		Map<String, Integer> wordCounts  = TextUtil.searchByWord(inputMap.get("searchText"));

		// creating response
		responseArray.put(wordCounts);
		response.put("counts", responseArray);

		return new ResponseEntity<String>(response.toString(), HttpStatus.OK);
	}
	

	@RequestMapping(value = "top/{limit}", method = { RequestMethod.GET }, produces = "text/csv")
	public void searchByLimit(@PathVariable("limit") @Valid @LimitConstraint Integer limit,
			HttpServletResponse response) throws IOException, CsvDataTypeMismatchException, CsvRequiredFieldEmptyException {
		
		
		//wordCounts from Text
		Map<String, Integer> wordCounts = TextUtil.generateWordCounts();

		// Sorting word frequency reverseOrder
		Map<String, Integer> sortedByCount = wordCounts.entrySet().stream()
				.sorted(Collections.reverseOrder(Map.Entry.comparingByValue())).limit(limit)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));

		// Parsing Map to List Csv
		List<CsvParse> resultOrdered = new ArrayList<CsvParse>();
		for (Entry<String, Integer> values : sortedByCount.entrySet()) {
			CsvParse csvParse = new CsvParse();
			csvParse.setKey(values.getKey());
			csvParse.setValue(values.getValue());
			resultOrdered.add(csvParse);
		}

		
		response.setContentType("text/csv");
		response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + this.filename + "\"");

		StatefulBeanToCsv<CsvParse> writer = new StatefulBeanToCsvBuilder<CsvParse>(response.getWriter())
				.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).withSeparator('|')
				.withOrderedResults(true)
				.build();

		writer.write(resultOrdered);
	}

}
