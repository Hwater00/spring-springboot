package com.study.springboot.been;

public class PrinterA implements Printer {

	@Override
	public void print(String message) {
		System.out.println("Printer A:"+message);

	}

}
