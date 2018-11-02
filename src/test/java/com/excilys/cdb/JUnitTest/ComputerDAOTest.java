package com.excilys.cdb.JUnitTest;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.*;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.exception.DataBaseException;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerDAOTest {
private ComputerDAO computerDao;
	
	@Before
	public void setUp() {
		computerDao = ComputerDAO.getInstance();
	}
	
	@After
	public void tearDown() {
		computerDao=null;
	}
	
	@Test
	public void testNotNullFindAllComputers() {
		try {
			assertNotNull(computerDao.findAll());	
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testEqualsComputerFindAllComputers() {
		try {
			List<Computer> result = computerDao.findAll();
			Computer computer= new Computer();
			for(int i=0;i<result.size();i++) {
				assertEquals(computer.getClass(),result.get(i).getClass());
			}
		}catch(Exception e) {
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
			assertTrue(computerDao.create(computer));
		}catch(Exception e) {
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

			assertTrue(computerDao.update(computer));
			
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	@Ignore
	public void testDeleteComputer() {
		try {	
			assertTrue(computerDao.delete(650));	
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testFindByIdComputer() {
		try {	
			Optional<Computer> computer = computerDao.find(12);
			assertEquals(12,computer.get().getId());
			assertEquals("Apple III",computer.get().getName());
			assertEquals("1980-05-01",computer.get().getIntroduced().toString());
			assertEquals("1984-04-01",computer.get().getDiscontinued().toString());
			assertEquals("Apple Inc.",computer.get().getCompany().getName());
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testFindByIdOutofBoundComputer() throws IOException{
		Optional<Computer> computer = null;
		try {	
			computer = computerDao.find(0);
		}catch(DataBaseException e) {
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

			assertFalse(computerDao.update(computer));
			
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testUpdateComputerWithoutName() {
		try {
			
			Computer computer = new Computer(600,"");
			Company company = new Company(5);
			
			computer.setIntroduced(LocalDate.of(2000, 01, 01));
			computer.setDiscontinued(LocalDate.of(2010, 01, 01));
			computer.setCompany(company);

			assertFalse(computerDao.update(computer));
			
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testDeleteOutOfBoundComputer() {
		try {	
			assertFalse(computerDao.delete(0));	
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
}
