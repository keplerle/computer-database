package com.excilys.cdb.console;

import java.sql.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import com.excilys.cdb.config.RootConfig;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.exception.OutOfCommandeScopeException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Component
public class Main {
	static Logger logger = LoggerFactory.getLogger(Main.class);
	public static final String SEPARATOR = "\n---------------------------------";

	@Autowired
	CompanyService cpaService;
	@Autowired
	ComputerService cpuService;
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		Main cli = context.getBean(Main.class);
		cli.mainMenu();
	}

	private void updateComputer() {
		int computerId = 0;
		String computerName = "";
		String dateIntroduced = "";
		String dateDiscontinued = "";
		Date introduced = null;
		Date discontinued = null;
		long companyId = 0;
		Computer updatedComputers;
		logger.info("Veuillez entrer l'id de l'ordinateur à mettre à jour: ");
		computerId = sc.nextInt();
		logger.info("Veuillez entrer le nouveau nom de l'ordinateur: ");
		sc.nextLine();
		computerName = sc.nextLine();
		logger.info("Veuillez entrer la nouvelle date de début: ");
		dateIntroduced = sc.nextLine();
		logger.info("Veuillez entrer la nouvelle date de fin: ");
		dateDiscontinued = sc.nextLine();
		logger.info("Veuillez entrer le nouveau numéro du fabricant de l'ordinateur (0 pour passer cette étape): ");
		companyId = sc.nextLong();

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
			updatedComputers.getCompany().setId(companyId);
		}
		try {
			cpuService.update(updatedComputers);

			logger.info("Ordinateur mis à jour avec succès !! ");

		} catch (DataException de) {
			logger.error(de.getMessage());
		}
	}

	private void createComputer() {

		String computerName = "";
		String dateIntroduced = "";
		String dateDiscontinued = "";
		Date introduced = null;
		Date discontinued = null;
		long companyId = 0;

		logger.info("Veuillez entrer le nom de l'ordinateur: ");
		computerName = sc.nextLine();
		logger.info("Veuillez entrer la date de début: ");
		dateIntroduced = sc.nextLine();
		logger.info("Veuillez entrer la date de fin: ");
		dateDiscontinued = sc.nextLine();

		logger.info("Veuillez entrer le numéro du fabricant de l'ordinateur (0 pour passer cette étape): ");
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
			cpuService.create(newComputer);
			logger.info("Ordinateur créé avec succès !! ");

		} catch (DataException de) {
			logger.error(de.getMessage());
		}
	}

	private void search() {
		String name = "";
		String subCommand = "";
		logger.info("Veuillez entrer le mot-clé de la recherche: ");
		name = sc.nextLine();

		logger.info("\n LISTE DES COMPUTERS");
		int i;
		do {
			try {
				List<Computer> subListComputer = cpuService.findAll(name);
				i = 0;
				while (i < subListComputer.size()) {
					try (Formatter fmt = new Formatter()) {
						if (logger.isInfoEnabled()) {
							logger.info(fmt.format("\t %d \t |", subListComputer.get(i).getId()).toString());
							logger.info(fmt.format("\t %s \t", subListComputer.get(i).getName()).toString());
							logger.info(SEPARATOR);
						}
					}

					i++;
				}
				logger.info("Previous page (p) 	Quit(q) 		Next page(n)");
				logger.info("Que voulez vous faire ? :");
				subCommand = sc.nextLine();
				if (subCommand.equals("n")) {
					Page.increasePage();
				} else if (subCommand.equals("p")) {
					Page.decreasePage();
				} else if (subCommand.equals("q")) {
					break;
				} else {
					logger.info("Je ne comprend pas la commande");
				}
			} catch (NoPreviousPageException nppe) {
				logger.error(nppe.getMessage());
				Page.increasePage();
			} catch (NoNextPageException nnpe) {
				logger.error(nnpe.getMessage());
				Page.decreasePage();
			}

		} while (!subCommand.equals("q"));
	}

	private void computerDetails() {
		Optional<Computer> computerToDisplay;
		int id = 0;
		logger.info("Veuillez entrer l'id de l'ordinateur: ");
		id = sc.nextInt();
		computerToDisplay = cpuService.find(id);
		if (logger.isInfoEnabled()) {
			logger.info(SEPARATOR);
			logger.info(computerToDisplay.toString());
			logger.info(SEPARATOR);
		}
	}

	private void displayCompanies() {
		logger.info("\n LISTE DES COMPANIES");
		int j;
		List<Company> subListCompany = cpaService.findAll();
		j = 0;
		while (j < subListCompany.size()) {
			if (logger.isInfoEnabled()) {
				logger.info(subListCompany.get(j).toString());
				logger.info(SEPARATOR);
			}
			j++;
		}
	}

	private void displayComputers() {
		String subCommand = "";

		logger.info("\n LISTE DES COMPUTERS");
		int i;
		do {
			try {
				List<Computer> subListComputer = cpuService.findAll("");
				i = 0;
				while (i < subListComputer.size()) {
					if (logger.isInfoEnabled()) {
						try (Formatter fmt = new Formatter()) {
							logger.info(fmt.format("\t %d \t |", subListComputer.get(i).getId()).toString());
							logger.info(fmt.format("\t %s \t", subListComputer.get(i).getName()).toString());
							logger.info(SEPARATOR);
						}
					}

					i++;
				}
				logger.info("Previous page (p) 	Quit(q) 		Next page(n)");
				logger.info("Que voulez vous faire ? :");
				subCommand = sc.nextLine();
				if (subCommand.equals("n")) {
					Page.increasePage();
				} else if (subCommand.equals("p")) {
					Page.decreasePage();
				} else if (subCommand.equals("q")) {
					break;
				} else {
					logger.info("Je ne comprend pas la commande");
				}
			} catch (NoPreviousPageException nppe) {
				logger.error(nppe.getMessage());
				Page.increasePage();
			} catch (NoNextPageException nnpe) {
				logger.error(nnpe.getMessage());
				Page.decreasePage();
			}

		} while (!subCommand.equals("q"));

	}

	private void deleteComputer() {
		int id = 0;
		logger.info("Veuillez entrer l'id de l'ordinateur à supprimer: ");
		id = sc.nextInt();
		cpuService.delete(id);
		logger.info("Ordinateur supprimé avec succès !! ");
	}

	private void deleteCompany() {
		int id = 0;
		logger.info("Veuillez entrer la company de l'ordinateur à supprimer: ");
		id = sc.nextInt();
		cpaService.delete(id);
		logger.info("Company supprimé avec succès !! ");
	}

	private void exit() {
		sc.close();
		logger.info("A bientot !");
		System.exit(0);
	}

	private void mainMenu() {
		int commande = 0;
		logger.info("Bienvenue sur CDB !");
		while (commande != 9) {
			try {
				for (Menu menu : Menu.values()) {
					logger.info(menu.getMessage());
				}
				commande = sc.nextInt();
				sc.nextLine();

				switch (commande) {
				case 1: // Lister les PC
					displayComputers();
					break;

				case 2: // Lister les entreprises
					displayCompanies();
					break;

				case 3: // Montrer les details d'un ordinateur par son id
					computerDetails();
					break;

				case 4: // Recherche
					search();
					break;

				case 5: // Créer un PC
					createComputer();
					break;

				case 6: // Mettre à jour un PC
					updateComputer();
					break;

				case 7: // Supprimer un PC
					deleteComputer();
					break;
				case 8: // Supprimer une company
					deleteCompany();
					break;
				case 9: // Quitter
					exit();
					break;
				default:
					throw new OutOfCommandeScopeException();
				}
				commande = 0;

			} catch (OutOfCommandeScopeException outEx) {
				logger.error(outEx.getMessage());
			}
		}

	}

}
