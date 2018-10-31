package com.excilys.cdb.JUnitTest;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.*;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;

public class CompanyDAOTest {
	
	private CompanyDAO companyDao;
	
	@Before
	public void setUp() {
		companyDao = CompanyDAO.getInstance();
	}
	
	@After
	public void tearDown() {
		companyDao=null;
	}
	
	@Test
	public void testNotNullFindAllCompanies() {
		try {
			assertNotNull(companyDao.findAll());	
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testEqualsCompanyFindAllCompanies() {
		try {
			List<Company> result = companyDao.findAll();
			Company company = new Company();
			for(int i=0;i<result.size();i++) {
				assertEquals(company.getClass(),result.get(i).getClass());
			}
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
}