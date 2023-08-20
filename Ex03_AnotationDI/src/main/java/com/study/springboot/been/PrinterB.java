package com.study.springboot.been;

import org.springframework.stereotype.Component;

@Component("PrinterB")
public class PrinterB implements Printer {

	@Override
	public void print(String message) {

		System.out.println("Printer B : " + message);
	}

}
