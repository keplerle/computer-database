package com.excilys.cdb.console;

import java.util.Formatter;
import java.util.Scanner;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.jaxrs.ResteasyClient;
import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

import com.excilys.cdb.config.RootConfig;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.OutOfCommandeScopeException;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Component
public class MainRest {
	static Logger logger = LoggerFactory.getLogger(MainRest.class);
	public static final String SEPARATOR = "\n---------------------------------";
	public static final String BASE_URL = "http://localhost:8080/computer-database/api/";
	public static final String LABEL_LOG = "HTTP Response Code: %d";
	ResteasyClient client = new ResteasyClientBuilder().build();
	@Autowired
	CompanyService cpaService;
	@Autowired
	ComputerService cpuService;
	Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		ApplicationContext context = new AnnotationConfigApplicationContext(RootConfig.class);
		MainRest cli = context.getBean(MainRest.class);
		cli.mainMenuRest();
	}

	private void exit() {
		sc.close();
		logger.info("A bientot !");
		System.exit(0);
	}

	private void mainMenuRest() {
		int commande = 0;
		logger.info("Bienvenue sur CDB !");
		while (commande != 9) {
			try {
				for (MenuREST menu : MenuREST.values()) {
					logger.info(menu.getMessage());
				}
				commande = sc.nextInt();
				sc.nextLine();

				switch (commande) {
				case 1:
					displayComputersRest();
					break;

				case 2:
					displayCompaniesRest();
					break;

				case 3:
					computerDetailsRest();
					break;

				case 4:
					countRest();
					break;

				case 5:
					createComputerRest();
					break;

				case 6:
					updateComputerRest();
					break;

				case 7:
					deleteComputerRest();
					break;
				case 8:
					deleteCompanyRest();
					break;
				case 9:
					createCompanyRest();
					break;	
				case 10:
					updateCompanyRest();
					break;
				case 11:
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

	private void deleteCompanyRest() {
		long id = 0;
		logger.info("Veuillez entrer la company de l'ordinateur à supprimer: ");
		id = sc.nextLong();
		ResteasyWebTarget delete = client.target(BASE_URL + "company/" + id);
		Response deleteResponse = delete.request().delete();
		if (logger.isInfoEnabled()) {
			try (Formatter fmt = new Formatter()) {
				logger.info(fmt.format(LABEL_LOG, deleteResponse.getStatus()).toString());
			}
		}
		deleteResponse.close();
	}

	private void deleteComputerRest() {
		long id = 0;
		logger.info("Veuillez entrer l'id de l'ordinateur à supprimer: ");
		id = sc.nextLong();
		ResteasyWebTarget delete = client.target(BASE_URL + "computer?idTab=" + id);
		Response deleteResponse = delete.request().delete();
		if (logger.isInfoEnabled()) {
			try (Formatter fmt = new Formatter()) {
				logger.info(fmt.format(LABEL_LOG, deleteResponse.getStatus()).toString());
			}
		}
		deleteResponse.close();
	}

	private void updateComputerRest() {
		long computerId;
		String computerName = "";
		String dateIntroduced = "";
		String dateDiscontinued = "";
		long companyId = 0;
		logger.info("Veuillez entrer l'id de l'ordinateur à mettre à jour: ");
		computerId = sc.nextLong();
		sc.nextLine();
		logger.info("Veuillez entrer le nom de l'ordinateur: ");
		computerName = sc.nextLine();
		logger.info("Veuillez entrer la date de début: ");
		dateIntroduced = sc.nextLine();
		logger.info("Veuillez entrer la date de fin: ");
		dateDiscontinued = sc.nextLine();
		logger.info("Veuillez entrer le numéro du fabricant de l'ordinateur (0 pour passer cette étape): ");
		companyId = sc.nextLong();
		ResteasyWebTarget add = client.target(BASE_URL + "computer");
		ComputerDTO computerDto = new ComputerDTO();
		computerDto.setId(computerId + "");
		computerDto.setName(computerName);
		computerDto.setIntroduced(dateIntroduced);
		computerDto.setDiscontinued(dateDiscontinued);
		computerDto.setCompanyId(companyId + "");
		Response addResponse = add.request().put(Entity.entity(computerDto, MediaType.APPLICATION_JSON));
		if (logger.isInfoEnabled()) {
			try (Formatter fmt = new Formatter()) {
				logger.info(fmt.format(LABEL_LOG, addResponse.getStatus()).toString());
			}
		}
		
		addResponse.close();
	}
	
	private void updateCompanyRest() {
		long companyId;
		String companyName = "";
		logger.info("Veuillez entrer l'id de la companie à mettre à jour: ");
		companyId = sc.nextLong();
		sc.nextLine();
		logger.info("Veuillez entrer le nom de la companie: ");
		companyName = sc.nextLine();
		ResteasyWebTarget add = client.target(BASE_URL + "company");
		CompanyDTO companyDto = new CompanyDTO();
		companyDto.setId(companyId + "");
		companyDto.setName(companyName);
		
		Response addResponse = add.request().put(Entity.entity(companyDto, MediaType.APPLICATION_JSON));
		if (logger.isInfoEnabled()) {
			try (Formatter fmt = new Formatter()) {
				logger.info(fmt.format(LABEL_LOG, addResponse.getStatus()).toString());
			}
		}
		
		addResponse.close();
	}
	

	private void createComputerRest() {
		String computerName = "";
		String dateIntroduced = "";
		String dateDiscontinued = "";
		long companyId = 0;
		logger.info("Veuillez entrer le nom de l'ordinateur: ");
		computerName = sc.nextLine();
		logger.info("Veuillez entrer la date de début: ");
		dateIntroduced = sc.nextLine();
		logger.info("Veuillez entrer la date de fin: ");
		dateDiscontinued = sc.nextLine();
		logger.info("Veuillez entrer le numéro du fabricant de l'ordinateur (0 pour passer cette étape): ");
		companyId = sc.nextLong();
		ResteasyWebTarget add = client.target(BASE_URL + "computer");
		ComputerDTO computerDto = new ComputerDTO();
		computerDto.setName(computerName);
		computerDto.setIntroduced(dateIntroduced);
		computerDto.setDiscontinued(dateDiscontinued);
		computerDto.setCompanyId(companyId + "");
		Response addResponse = add.request().post(Entity.entity(computerDto, MediaType.APPLICATION_JSON));
		if (logger.isInfoEnabled()) {
			try (Formatter fmt = new Formatter()) {
				logger.info(fmt.format(LABEL_LOG, addResponse.getStatus()).toString());
			}
		}
		
		addResponse.close();
	}
	
	private void createCompanyRest() {
		String companyName = "";
		logger.info("Veuillez entrer le nom de la company: ");
		companyName = sc.nextLine();
		ResteasyWebTarget add = client.target(BASE_URL + "company");
		CompanyDTO companyDto = new CompanyDTO();
		companyDto.setName(companyName);
		Response addResponse = add.request().post(Entity.entity(companyDto, MediaType.APPLICATION_JSON));
		if (logger.isInfoEnabled()) {
			try (Formatter fmt = new Formatter()) {
				logger.info(fmt.format(LABEL_LOG, addResponse.getStatus()).toString());
				logger.info(addResponse.getStatusInfo().toString());
			}
		}
		
		addResponse.close();
	}

	private void countRest() {
		String name = "/";
		logger.info("Veuillez entrer le mot-clé de la recherche: ");
		name += sc.nextLine();
		logger.info("\n LISTE DES COMPUTERS");
		ResteasyWebTarget getDummy = client.target(BASE_URL + "computer/count" + name);
		Response getDummyResponse = getDummy.request().get();
		String value = getDummyResponse.readEntity(String.class);
		logger.info(value);
		getDummyResponse.close();
	}

	private void computerDetailsRest() {
		long id = 0;
		logger.info("Veuillez entrer l'id de l'ordinateur: ");
		id = sc.nextLong();
		ResteasyWebTarget getDummy = client.target(BASE_URL + "computer/" + id);
		Response getDummyResponse = getDummy.request().get();
		String value = getDummyResponse.readEntity(String.class);
		logger.info(value);
		getDummyResponse.close();
	}

	private void displayCompaniesRest() {
		ResteasyWebTarget getDummy = client.target(BASE_URL + "company/all");
		Response getDummyResponse = getDummy.request().get();
		String value = getDummyResponse.readEntity(String.class);
		logger.info(value);
		getDummyResponse.close();
	}

	private void displayComputersRest() {
		Page.setPage("1", "10");
		String name = "/";
		String subCommand = "";
		logger.info("Veuillez entrer le mot-clé de la recherche: ");
		name += sc.nextLine();
		logger.info("\n LISTE DES COMPUTERS");
		do {
			ResteasyWebTarget getDummy = client.target(BASE_URL + "computer/all" + name);
			Response getDummyResponse = getDummy.request().get();
			String value = getDummyResponse.readEntity(String.class);
			logger.info(value);
			getDummyResponse.close();
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
		} while (!subCommand.equals("q"));
	}
}
