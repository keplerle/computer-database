package com.excilys.cdb.exception;

public class DataException extends Exception {

	private static final long serialVersionUID = 1L;

	public DataException() {
		super("Les données sont incohérentes ou incomplètes");
	}
}
