package com.excilys.cdb.consoleRest;

public enum MenuREST {

	MENU("\n MENU :"), C1("1.GET /computer/all/?"), C2("2.GET /company/all"),
	C3("3.GET /computer/?"), C4("4.GET /computer/count/?"),
	C5("5.POST /computer"), C6("6.PUT /computer"), C7("7.DELETE /computer"),
	C8("8.DELETE /company/?"),C9("9. Quitter"), COMMANDE("Veuillez entrer le numéro de la commande: ");

	private String message;

	MenuREST(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
