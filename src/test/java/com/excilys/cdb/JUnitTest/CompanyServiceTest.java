package com.excilys.cdb.JUnitTest;

import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;


import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

public class CompanyServiceTest {
	@Autowired
	private CompanyService companyService;
	
	@Before
	public void setUp() {
	}
	
	@After
	public void tearDown() {
		companyService=null;
	}
	
	@Test
	public void testNotNullFindAllCompanies() {
		try {
			assertNotNull(companyService.findAll());	
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	public void testEqualsCompanyFindAllCompanies() {
		try {
			List<Company> result = companyService.findAll();
			Company company = new Company();
			for(int i=0;i<result.size();i++) {
				assertEquals(company.getClass(),result.get(i).getClass());
			}
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
	
	@Test
	@Ignore
	public void testDeleteCompany() {
		try {
			companyService.delete(58);
		}catch(Exception e) {
			fail("Exception inattendue");
		}		
	}
}
