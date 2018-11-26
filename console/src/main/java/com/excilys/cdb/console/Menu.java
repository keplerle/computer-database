package com.excilys.cdb.console;

public enum Menu {

	MENU("\n MENU :"), C1("1. Lister les ordinateurs"), C2("2. Lister les entreprises"),
	C3("3. Montrer les details d'un ordinateur par son id"), C4("4. Rechercher"),
	C5("5. Créer un ordinateur"), C6("6. Mettre à jour un ordinateur"), C7("7. Supprimer un ordinateur"),
	C8("8. Supprimer une company"),C9("9. Quitter"), COMMANDE("Veuillez entrer le numéro de la commande: ");

	private String message;

	Menu(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	private void setMessage(String message) {
		this.message = message;
	}
}
