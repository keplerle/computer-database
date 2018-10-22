package com.excilys.cdb.exception;

public class DateException extends Exception {

	private static final long serialVersionUID = 1L;

	public DateException() {
		super("Les dates sont incohérentes !");
	}
}
