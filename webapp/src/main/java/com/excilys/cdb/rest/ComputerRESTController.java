package com.excilys.cdb.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@RestController("computerController")
@RequestMapping("/computer")
public class ComputerRESTController {

	private final ComputerService computerService;
	private final MapperComputerDTO computerMapper;
	Logger logger = LoggerFactory.getLogger(ComputerRESTController.class);

	public ComputerRESTController(ComputerService computerService, MapperComputerDTO computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}

//	@GetMapping("/{id}")
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseEntity<Optional<Computer>> find(@PathParam("id") int id) {
		Optional<Computer> computer = computerService.find(id);
		return new ResponseEntity<>(computer, HttpStatus.OK);
	}
	   @GET
	    @Path("/count/{name}")
	    @Produces(MediaType.APPLICATION_JSON)
	//@GetMapping("/count/{name}")
	public ResponseEntity<Long> count(@PathParam("name") String name) {
		long count = computerService.count(name);
		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	   @GET
	    @Path("/all/{name}")
	    @Produces(MediaType.APPLICATION_JSON)
	//@GetMapping("/all/{name}")
	public ResponseEntity<List<Computer>> findAll(@PathParam("name") String name) {
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
