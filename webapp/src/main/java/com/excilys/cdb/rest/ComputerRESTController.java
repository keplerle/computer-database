package com.excilys.cdb.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.mapper.MapperComputerDTO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;
@CrossOrigin(origins="*")
@RestController("computerController")
@RequestMapping("/api/computer")
public class ComputerRESTController {

	private final ComputerService computerService;
	private final MapperComputerDTO computerMapper;
	Logger logger = LoggerFactory.getLogger(ComputerRESTController.class);

	public ComputerRESTController(ComputerService computerService, MapperComputerDTO computerMapper) {
		this.computerService = computerService;
		this.computerMapper = computerMapper;
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<ComputerDTO> find(@PathVariable("id") Long id) {
		ComputerDTO computerDto = computerMapper.fromOptionalComputer(computerService.find(id));
		return new ResponseEntity<>(computerDto, HttpStatus.OK);
	}

	@GetMapping({ "/count", "/count/{name}" })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<Long> count(@PathVariable("name") Optional<String> name) {
		long count;
		if (name.isPresent()) {
			count = computerService.count(name.get());
		} else {
			count = computerService.count("");
		}

		return new ResponseEntity<>(count, HttpStatus.OK);
	}

	@GetMapping({ "/all", "/all/{name}" })
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
	public ResponseEntity<List<ComputerDTO>> findAll(@PathVariable("name") Optional<String> name,
			@RequestParam(required = false, defaultValue = "1") String page,
			@RequestParam(required = false, defaultValue = "10") String size) {
		List<Computer> computerList = new ArrayList<>();
		List<ComputerDTO> subComputersDTO = new ArrayList<>();
		try {
			Page.setPage(page, size);
			if (name.isPresent()) {
				computerList = computerService.findAll(name.get());
			} else {
				computerList = computerService.findAll("");
			}

			subComputersDTO = computerList.stream().map(computerMapper::fromComputer).collect(Collectors.toList());
		} catch (NoPreviousPageException e) {
			Page.increasePage();
		} catch (NoNextPageException e) {
			Page.decreasePage();
		}
		if (computerList.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(subComputersDTO, HttpStatus.OK);

	}

	@PostMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ComputerDTO> create(@RequestBody ComputerDTO computerDto) {
		try {
			logger.info(computerDto.getCompanyId());
			logger.info(computerDto.getCompanyName());
			computerService.create(computerMapper.toComputer(computerDto));
		} catch (DataException e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(computerDto, HttpStatus.CREATED);

	}

	@PutMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<ComputerDTO> update(@RequestBody ComputerDTO computerDto) {
		try {
			computerService.update(computerMapper.toComputer(computerDto));
		} catch (DataException e) {
			logger.error(e.getMessage());
		}
		return new ResponseEntity<>(computerDto, HttpStatus.OK);
	}

	@DeleteMapping
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<Void> delete(@RequestParam String[] idTab) {
		computerService.deleteAll(idTab);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
