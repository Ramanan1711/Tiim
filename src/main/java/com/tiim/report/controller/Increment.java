package com.tiim.report.controller;

import org.springframework.stereotype.Component;

@Component
public class Increment {
 
	private int counter;

	public int getCounter() {
		counter++;
		counter++;
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
	
}
