package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller("computerController")
@RequestMapping("/computer")
public class ComputerController {
	
	@Autowired
	ComputerService computerService;

	@GetMapping(value = "")
	public ResponseEntity<Optional<Computer>> find(int id) {
		Optional<Computer> computer = computerService.find(id);
		if (computer == null) {
			return new ResponseEntity<Optional<Computer>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Optional<Computer>>(computer, HttpStatus.OK);
	}

	@GetMapping(value = "/count")
	public ResponseEntity<Integer> count(String name) {
		int count = computerService.count(name);
		return new ResponseEntity<Integer>(count, HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<Computer>> findAll(String name) {
		List<Computer> computerList = new ArrayList<Computer>();
		try {
			computerList = computerService.findAll(name);
		} catch (NoPreviousPageException e) {
			Page.increasePage();
		} catch (NoNextPageException e) {
			Page.decreasePage();
		}
		if (computerList.isEmpty()) {
			return new ResponseEntity<List<Computer>>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<Computer>>(computerList, HttpStatus.OK);

	}
	@PostMapping(value = "")
	public ResponseEntity<Computer> create(@RequestBody Computer computer) {
		try {
			computerService.create(computer);
		} catch (DataException e) {
			e.printStackTrace();
		}
		ResponseEntity<Computer> responseEntity = new ResponseEntity<Computer>(computer, HttpStatus.CREATED);
		return responseEntity;
		
	}
	@PutMapping(value = "")
	public ResponseEntity<Computer> update(@RequestBody Computer computer) {
		try {
			computerService.update(computer);
		} catch (DataException e) {
			e.printStackTrace();
		}
		ResponseEntity<Computer> responseEntity = new ResponseEntity<Computer>(computer, HttpStatus.OK);
		return responseEntity;
		
	}
	@DeleteMapping(value = "")
	public ResponseEntity<Void> delete(String[] idTab) {
		computerService.deleteAll(idTab);
		return new ResponseEntity<Void>(HttpStatus.GONE);
	}
}
