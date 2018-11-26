package com.excilys.cdb.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.mapper.MapperCompanyDTO;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller("computerController")
@RequestMapping("/computer")
public class ComputerRESTController {

	private final ComputerService computerService;
	private final MapperComputerDTO computerMapper;
	Logger logger = LoggerFactory.getLogger(ComputerRESTController.class);

	public ComputerRESTController(ComputerService computerService, MapperComputerDTO computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}

	@GetMapping
	public ResponseEntity<Optional<Computer>> find(int id) {
		Optional<Computer> computer = computerService.find(id);
		return new ResponseEntity<>(computer, HttpStatus.OK);
	}

	@GetMapping(value = "/count")
	public ResponseEntity<Long> count(String name) {
		long count = computerService.count(name);
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<Computer>> findAll(String name) {
		List<Computer> computerList = new ArrayList<>();
		try {
			computerList = computerService.findAll(name);
		} catch (NoPreviousPageException e) {
			Page.increasePage();
		} catch (NoNextPageException e) {
			Page.decreasePage();
		}
		if (computerList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(computerList, HttpStatus.OK);

	}

	@PostMapping
	public ResponseEntity<ComputerDTO> create(@RequestBody ComputerDTO computerDto) {
		try {
			computerService.create(computerMapper.toComputer(computerDto));
		} catch (DataException e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(computerDto, HttpStatus.CREATED);

	}

	@PutMapping
	public ResponseEntity<ComputerDTO> update(@RequestBody ComputerDTO computerDto) {
		try {
			computerService.update(computerMapper.toComputer(computerDto));
		} catch (DataException e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(computerDto, HttpStatus.OK);

	}

	@DeleteMapping
	public ResponseEntity<Void> delete(String[] idTab) {
		computerService.deleteAll(idTab);
		return new ResponseEntity<>(HttpStatus.GONE);
	}
}
