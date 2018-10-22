package com.excilys.cdb.exception;

public class OutOfCommandeScopeException extends Exception {

	private static final long serialVersionUID = 1L;

	public OutOfCommandeScopeException() {
		super("Les commandes du menu sont compris entre 1 et 8 !");
	}
}
