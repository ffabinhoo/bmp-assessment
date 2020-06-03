package com.fabio.bmpassessment.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Map;

import org.junit.Test;

public class TextUtilTest {
	
	@Test
	public void getWordsFromTextTest() {
		String[] wordsFromText = TextUtil.getWordsFromText();
		assertEquals(911, wordsFromText.length);
	}
	
	@Test
	public void searchByWordTest() {
		String[] searchText = {"Duis"};
		Map<String, Integer> wordCounts = TextUtil.searchByWord(searchText );
		
		assertEquals(11, wordCounts.get("Duis"));
	}
	
	@Test
	public void searchByWordThatDoesntExistTest() {
		String[] searchText = {"123"};
		Map<String, Integer> wordCounts = TextUtil.searchByWord(searchText );
		
		assertEquals(0, wordCounts.get("123"));
	}
	
	@Test
	public void generateWordCountsTest() {
		Map<String, Integer> wordCounts = TextUtil.generateWordCounts();

		assertEquals(175, wordCounts.entrySet().size());
	}
}
