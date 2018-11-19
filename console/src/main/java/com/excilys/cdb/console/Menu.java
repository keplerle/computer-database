package com.excilys.cdb.console;

public enum Menu {

	menu("\n MENU :"), _1("1. Lister les ordinateurs"), _2("2. Lister les entreprises"),
	_3("3. Montrer les details d'un ordinateur par son id"), _4("4. Montrer les details d'un ordinateur par son nom"),
	_5("5. Créer un ordinateur"), _6("6. Mettre à jour un ordinateur"), _7("7. Supprimer un ordinateur"),
	_8("8. Supprimer une company"),_9("9. Quitter"), commande("Veuillez entrer le numéro de la commande: ");

	private String message;

	Menu(String message) {
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
