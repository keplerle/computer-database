package com.excilys.cdb.mapper;

import java.sql.Date;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class MapperComputerDTO {

	private static MapperComputerDTO mapperComputerDTO = new MapperComputerDTO();

	private MapperComputerDTO() {
	}

	public static MapperComputerDTO getInstance() {
		return mapperComputerDTO;
	}

	public Computer computerDtoToComputer(ComputerDTO computerDto) {
		Computer computer = new Computer();
		Company company = new Company();

		computer.setName(computerDto.getName());

		if (computerDto.getId() != null) {
			computer.setId(Integer.parseInt(computerDto.getId()));
		}
		if (!computerDto.getIntroduced().equals("")) {
			computer.setIntroduced(Date.valueOf(computerDto.getIntroduced()).toLocalDate());
		}
		if (!computerDto.getIntroduced().equals("")) {
			computer.setDiscontinued(Date.valueOf(computerDto.getDiscontinued()).toLocalDate());
		}
		
		company.setId(Integer.parseInt(computerDto.getCompanyId()));
		company.setName(computerDto.getCompanyName());
		computer.setCompany(company);
		return computer;

	}

	public ComputerDTO computerToComputerDto(Computer computer) {

		ComputerDTO computerDto = new ComputerDTO();
		computerDto.setId(computer.getId()+"");
		computerDto.setName(computer.getName());

		if (computer.getIntroduced() != null) {
			computerDto.setIntroduced(computer.getIntroduced().toString());
		}
		if (computer.getDiscontinued() != null) {
			computerDto.setDiscontinued(computer.getDiscontinued().toString());
		}

		computerDto.setCompanyId(computer.getCompany().getId() + "");
		computerDto.setCompanyName(computer.getCompany().getName());
		return computerDto;

	}
}
