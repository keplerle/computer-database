package com.excilys.cdb.JUnitTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

public class ComputerServiceTest {
private ComputerService computerService;
	
	@Before
	public void setUp() {
		computerService = ComputerService.getInstance();
	}
	
	@After
	public void tearDown() {
		computerService=null;
	}
	
	@Test
	public void testNotNullFindAllComputers() {
		try {
			assertNotNull(computerService.findAll());	
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testEqualsComputerFindAllComputers() {
		try {
			List<Computer> result = computerService.findAll();
			Computer computer= new Computer();
			for(int i=0;i<result.size();i++) {
				assertEquals(computer.getClass(),result.get(i).getClass());
			}
		}catch(Exception e) {
			e.printStackTrace();
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
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception inattendue");
		}		
	}
	
	@Test
	@Ignore
	public void testUpdateComputer() {
		try {
			
			Computer computer = new Computer(600,"UnitTestUpdate");
			Company company = new Company(5);
			
			computer.setIntroduced(LocalDate.of(2000, 01, 01));
			computer.setDiscontinued(LocalDate.of(2010, 01, 01));
			computer.setCompany(company);

			computerService.update(computer);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception inattendue");
		}		
	}
	
	@Test
	@Ignore
	public void testDeleteComputer() {
		try {	
			computerService.delete(650);	
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testFindByIdComputer() {
		try {	
			Optional<Computer> computer = computerService.find(12);
			assertEquals(12,computer.get().getId());
			assertEquals("Apple III",computer.get().getName());
			assertEquals("1980-05-01",computer.get().getIntroduced().toString());
			assertEquals("1984-04-01",computer.get().getDiscontinued().toString());
			assertEquals("Apple Inc.",computer.get().getCompany().getName());
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testFindByIdOutofBoundComputer() throws IOException{
		Optional<Computer> computer = null;
		try {	
			computer = computerService.find(0);
		}catch(DataBaseException e) {
			e.printStackTrace();
			assertNull(computer);
		}
	}
	
	@Test
	public void testUpdateOutOfBoundComputer() {
		try {
			
			Computer computer = new Computer(0,"UnitTestUpdate");
			Company company = new Company(5);
			
			computer.setIntroduced(LocalDate.of(2000, 01, 01));
			computer.setDiscontinued(LocalDate.of(2010, 01, 01));
			computer.setCompany(company);

			computerService.update(computer);
			
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testUpdateComputerWithoutName() throws IOException, DataBaseException {
		
			
			Computer computer = new Computer(600,"");
			Company company = new Company(5);
			
			computer.setIntroduced(LocalDate.of(2000, 01, 01));
			computer.setDiscontinued(LocalDate.of(2010, 01, 01));
			computer.setCompany(company);

			try {
				computerService.update(computer);
			} catch (DataException e) {
				e.printStackTrace();
				assertEquals("Le nom est requis",e.getMessage());			
			}
			
				
	}
	
	@Test
	public void testDeleteOutOfBoundComputer() {
		try {	
			computerService.delete(0);	
		}catch(Exception e) {
			e.printStackTrace();
			fail("Exception inattendue");
		}		
	}
}
