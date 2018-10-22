package com.excilys.cdb.ui;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.exception.DateException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.exception.OutOfCommandeScopeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

public class Main {

	public static void main(String[] args) {

		Logger logger = LoggerFactory.getLogger(Main.class);
		CompanyService cpaService;
		ComputerService cpuService;

		int commande = 0;
		int id = 0;
		int companyId = 0;
		int computerId = 0;

		boolean response = false;
		Computer computerToDisplay;
		Computer updatedComputers;
		String name = "";
		String computerName = "";
		String dateIntroduced = "";
		String dateDiscontinued = "";
		String subCommand = "";
		Date introduced = null;
		Date discontinued = null;

		Scanner sc = new Scanner(System.in);
		logger.info("Bienvenue sur CDB !");
		while (commande != 8) {
			try {
				for (Menu menu : Menu.values()) {
					logger.info(menu.getMessage());
				}
				commande = sc.nextInt();
				sc.nextLine();

				switch (commande) {
				case 1: { // Lister les PC

					cpuService = ComputerService.getInstance();

					logger.info("\n LISTE DES COMPUTERS");
					Page.setPage(1);

					int i;
					do {
						try {
							List<Computer> subListComputer = cpuService.findAll();
							i = 0;

							while (i < subListComputer.size()) {
								logger.info("\t" + subListComputer.get(i).getId() + "\t |");
								logger.info("\t" + subListComputer.get(i).getName() + "\t");
								logger.info("\n---------------------------------");
								i++;
							}
							logger.info("Previous page (p) 	Quit(q) 		Next page(n)");
							logger.info("Que voulez vous faire ? :");

							subCommand = sc.nextLine();
							if (subCommand.equals("n")) {
								Page.setPage(Page.getPage() + 1);

							} else if (subCommand.equals("p")) {
								Page.setPage(Page.getPage() - 1);

							} else if (subCommand.equals("q")) {
								break;
							} else {
								logger.warn("Je ne comprend pas la commande");
							}
						} catch (NoPreviousPageException nppe) {
							logger.error(nppe.getMessage());
							Page.setPage(Page.getPage() + 1);
						} catch (NoNextPageException nnpe) {
							logger.error(nnpe.getMessage());
							Page.setPage(Page.getPage() - 1);
						}

					} while (!subCommand.equals("q"));

					break;
				}
				case 2: { // Lister les entreprises

					cpaService = CompanyService.getInstance();

					logger.info("\n LISTE DES COMPANIES");
					Page.setPage(1);

					int j;
					do {
						try {
							List<Company> subListCompany = cpaService.findAll();
							j = 0;
							while (j < subListCompany.size()) {
								logger.info(subListCompany.get(j).toString());
								logger.info("\n---------------------------------");
								j++;
							}

							logger.info("Previous page (p) 	Quit(q) 		Next page(n)");
							logger.info("Que voulez vous faire ? :");

							subCommand = sc.nextLine();
							if (subCommand.equals("n")) {
								Page.setPage(Page.getPage() + 1);

							} else if (subCommand.equals("p")) {
								Page.setPage(Page.getPage() - 1);

							} else if (subCommand.equals("q")) {
								break;
							} else {
								logger.warn("Je ne comprend pas la commande");
							}
						} catch (NoPreviousPageException nppe) {
							logger.error(nppe.getMessage());
							Page.setPage(Page.getPage() + 1);
						} catch (NoNextPageException nnpe) {
							logger.error(nnpe.getMessage());
							Page.setPage(Page.getPage() - 1);
						}

					} while (!subCommand.equals("q"));

					break;
				}
				case 3: { // Montrer les details d'un ordinateur par son id
					cpuService = ComputerService.getInstance();

					logger.info("Veuillez entrer l'id de l'ordinateur: ");
					id = sc.nextInt();

					computerToDisplay = cpuService.find(id);
					logger.info("\n---------------------------------");
					logger.info(computerToDisplay.toString());
					logger.info("\n---------------------------------");
					break;
				}
				case 4: { // Montrer les details d'un ordinateur par son nom
					cpuService = ComputerService.getInstance();

					logger.info("Veuillez entrer le nom de l'ordinateur: ");
					name = sc.nextLine();

					computerToDisplay = cpuService.find(name);

					logger.info("\n---------------------------------");
					logger.info(computerToDisplay.toString());
					logger.info("\n---------------------------------");

					break;
				}
				case 5: { // Créer un PC
					cpuService = ComputerService.getInstance();

					logger.info("Veuillez entrer le nom de l'ordinateur: ");
					computerName = sc.nextLine();
					logger.info("Veuillez entrer la date de début: ");
					dateIntroduced = sc.nextLine();
					logger.info("Veuillez entrer la date de fin: ");
					dateDiscontinued = sc.nextLine();

					logger.info(
							"Veuillez entrer le numéro du fabricant de l'ordinateur (0 pour passer cette étape): ");
					companyId = sc.nextInt();

					Computer newComputer = new Computer(computerName);
					Company newCompany = new Company(companyId);

					if (!dateIntroduced.equals("")) {
						introduced = Date.valueOf(dateIntroduced);
						newComputer.setIntroduced(introduced.toLocalDate());
					}

					if (!dateDiscontinued.equals("")) {
						discontinued = Date.valueOf(dateDiscontinued);
						newComputer.setDiscontinued(discontinued.toLocalDate());
					}
					newComputer.setCompany(newCompany);
					try {
						response = cpuService.create(newComputer);
						if (response == true) {
							logger.info("Ordinateur créé avec succès !! ");
						} else {
							logger.info("Echec de la création !! ");
						}
						response = false;
					} catch (DateException de) {
						logger.error(de.getMessage());
					}
					introduced = null;
					discontinued = null;
					break;
				}
				case 6: { // Mettre à jour un PC
					cpuService = ComputerService.getInstance();

					logger.info("Veuillez entrer l'id de l'ordinateur à mettre à jour: ");
					computerId = sc.nextInt();
					logger.info("Veuillez entrer le nouveau nom de l'ordinateur: ");
					sc.nextLine();
					computerName = sc.nextLine();
					logger.info("Veuillez entrer la nouvelle date de début: ");
					dateIntroduced = sc.nextLine();
					logger.info("Veuillez entrer la nouvelle date de fin: ");
					dateDiscontinued = sc.nextLine();
					logger.info(
							"Veuillez entrer le nouveau numéro du fabricant de l'ordinateur (0 pour passer cette étape): ");
					companyId = sc.nextInt();

					updatedComputers = new Computer(computerId, computerName);

					if (!dateIntroduced.equals("")) {
						introduced = Date.valueOf(dateIntroduced);
						updatedComputers.setIntroduced(introduced.toLocalDate());
					}
					if (!dateDiscontinued.equals("")) {
						discontinued = Date.valueOf(dateDiscontinued);
						updatedComputers.setDiscontinued(discontinued.toLocalDate());
					}

					if (companyId > 0) {
						updatedComputers.getCompany().setId(computerId);
					}
					try {
						response = cpuService.update(updatedComputers);
						if (response == true) {
							logger.info("Ordinateur mis à jour avec succès !! ");
						} else {
							logger.warn("Echec de la mise à jour !! ");
						}
						response = false;
					} catch (DateException de) {
						logger.error(de.getMessage());
					}
					break;

				}
				case 7: { // Supprimer un PC
					cpuService = ComputerService.getInstance();

					logger.info("Veuillez entrer l'id de l'ordinateur à supprimer: ");
					id = sc.nextInt();

					response = cpuService.delete(id);

					if (response == true) {
						logger.info("Ordinateur supprimé avec succès !! ");
					} else {
						logger.warn("Echec de la suppression !! ");
					}
					response = false;
					break;
				}
				case 8: { // Quitter
					sc.close();

					logger.info("Deconnexion to DB");
					logger.info("A bientot !");
					System.exit(0);
					break;
				}
				default: {
					throw new OutOfCommandeScopeException();
				}
				}
				commande = 0;
			} catch (SQLException ex) {
				logger.error("SQLException: " + ex.getMessage());
				logger.error("SQLState: " + ex.getSQLState());
				logger.error("VendorError: " + ex.getErrorCode());
			} catch (OutOfCommandeScopeException outEx) {
				logger.error(outEx.getMessage());
			}
		}
	}
}
