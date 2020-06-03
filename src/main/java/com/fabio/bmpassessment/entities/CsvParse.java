package com.fabio.bmpassessment.entities;

import com.opencsv.bean.CsvBindByPosition;

public class CsvParse {
	
	@CsvBindByPosition(position = 0)
	private String key;
	
	@CsvBindByPosition(position = 1)
	private Integer value;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}


}
