package com.excilys.cdb.mapper;

import java.sql.Date;
import java.util.Optional;

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

		computer.setName(computerDto.name);

		if (computerDto.id != null) {
			computer.setId(Integer.parseInt(computerDto.id));
		}
		if (!"".equals(computerDto.introduced)) {
			computer.setIntroduced(Date.valueOf(computerDto.introduced).toLocalDate());
		}
		if (!"".equals(computerDto.introduced)) {
			computer.setDiscontinued(Date.valueOf(computerDto.discontinued).toLocalDate());
		}
		
		company.setId(Integer.parseInt(computerDto.companyId));
		company.setName(computerDto.companyName);
		computer.setCompany(company);
		return computer;

	}

	public ComputerDTO computerDtoFromOptionalComputer(Optional<Computer> optional) {

		ComputerDTO computerDto = new ComputerDTO();
		

		computerDto.id=optional.get().getId()+"";
		computerDto.name=optional.get().getName();

		if (optional.get().getIntroduced() != null) {
			computerDto.introduced=optional.get().getIntroduced().toString();
		}
		if (optional.get().getDiscontinued() != null) {
			computerDto.discontinued=optional.get().getDiscontinued().toString();
		}

		computerDto.companyId=optional.get().getCompany().getId()+"";
		computerDto.companyName=optional.get().getCompany().getName();
		return computerDto;

	}
	
	public ComputerDTO computerDtoFromComputer(Computer computer) {

		ComputerDTO computerDto = new ComputerDTO();
		

		computerDto.id=computer.getId()+"";
		computerDto.name=computer.getName();

		if (computer.getIntroduced() != null) {
			computerDto.introduced=computer.getIntroduced().toString();
		}
		if (computer.getDiscontinued() != null) {
			computerDto.discontinued=computer.getDiscontinued().toString();
		}

		computerDto.companyId=computer.getCompany().getId()+"";
		computerDto.companyName=computer.getCompany().getName();
		return computerDto;

	}
}
