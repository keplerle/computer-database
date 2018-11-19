package com.excilys.cdb.JUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.excilys.cdb.config.RootConfig;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)

public class ComputerServiceTest {
	Logger logger = LoggerFactory.getLogger(ComputerServiceTest.class);

	@Autowired
	private ComputerService computerService;

	@Before
	public void setUp() {

	}

	@After
	public void tearDown() {

	}

	@Test
	public void testNotNullFindAllComputers() {
		try {
			assertNotNull(computerService.findAll(""));
		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}

	@Test
	public void testEqualsComputerFindAllComputers() {
		try {
			List<Computer> result = computerService.findAll("");
			Computer computer = new Computer();
			for (int i = 0; i < result.size(); i++) {
				assertEquals(computer.getClass(), result.get(i).getClass());
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}

	@Test
	@Ignore
	public void testCreateComputer() {
		try {
			Computer computer = new Computer("UnitTestCreate");
			Company company = new Company();
			computer.setCompany(company);
			computerService.create(computer);
		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}

	@Test
	@Ignore
	public void testUpdateComputer() {
		try {

			Computer computer = new Computer(600, "UnitTestUpdate");
			Company company = new Company(5);

			computer.setIntroduced(LocalDate.of(2000, 01, 01));
			computer.setDiscontinued(LocalDate.of(2010, 01, 01));
			computer.setCompany(company);

			computerService.update(computer);

		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}

	@Test
	@Ignore
	public void testDeleteComputer() {
		try {
			computerService.delete(650);
		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}

	@Test
	public void testFindByIdComputer() {
		try {
			Optional<Computer> computer = computerService.find(12);
			assertEquals(new Long(12L), computer.get().getId());
			assertEquals("Apple III", computer.get().getName());
			assertEquals("1980-05-01", computer.get().getIntroduced().toString());
			assertEquals("1984-04-01", computer.get().getDiscontinued().toString());
			assertEquals("Apple Inc.", computer.get().getCompany().getName());
		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}

	@Test
	public void testUpdateOutOfBoundComputer() {
		try {

			Computer computer = new Computer(0, "UnitTestUpdate");
			Company company = new Company(5);

			computer.setIntroduced(LocalDate.of(2000, 01, 01));
			computer.setDiscontinued(LocalDate.of(2010, 01, 01));
			computer.setCompany(company);

			computerService.update(computer);

		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}

	@Test
	public void testUpdateComputerWithoutName() {

		Computer computer = new Computer(600, "");
		Company company = new Company(5);

		computer.setIntroduced(LocalDate.of(2000, 01, 01));
		computer.setDiscontinued(LocalDate.of(2010, 01, 01));
		computer.setCompany(company);

		try {
			computerService.update(computer);
		} catch (DataException e) {
			assertEquals("Le nom est requis", e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

	}

	@Test
	public void testDeleteOutOfBoundComputer() {
		try {
			computerService.delete(0L);
		} catch (Exception e) {
			logger.error(e.getMessage());
			fail("Exception inattendue");
		}
	}
}
