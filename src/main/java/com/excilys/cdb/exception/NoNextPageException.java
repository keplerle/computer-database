package com.excilys.cdb.exception;

public class NoNextPageException extends Exception {

	private static final long serialVersionUID = 1L;

	public NoNextPageException() {
		super("Il n'y a pas de pages suivantes !");
	}
}
