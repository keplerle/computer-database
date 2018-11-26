package com.excilys.cdb.mapper;

import java.sql.Date;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class MapperComputerDTO {

	public Computer toComputer(ComputerDTO computerDto) {
		Computer computer = new Computer();
		Company company = new Company();

		computer.setName(computerDto.name);

		if (computerDto.id != null) {
			computer.setId(Long.parseLong(computerDto.id));
		}
		if (!"".equals(computerDto.introduced)) {
			computer.setIntroduced(Date.valueOf(computerDto.introduced).toLocalDate());
		}
		if (!"".equals(computerDto.discontinued)) {
			computer.setDiscontinued(Date.valueOf(computerDto.discontinued).toLocalDate());
		}

		company.setId(Long.parseLong(computerDto.companyId));
		company.setName(computerDto.companyName);
		computer.setCompany(company);
		return computer;

	}

	public ComputerDTO fromOptionalComputer(Optional<Computer> optional) {

		ComputerDTO computerDto = new ComputerDTO();
		if (optional.isPresent()) {
			computerDto.id = optional.get().getId() + "";
			computerDto.name = optional.get().getName();

			if (optional.get().getIntroduced() != null) {
				computerDto.introduced = optional.get().getIntroduced().toString();
			}
			if (optional.get().getDiscontinued() != null) {
				computerDto.discontinued = optional.get().getDiscontinued().toString();
			}
			if (optional.get().getCompany() != null) {
				computerDto.companyId = optional.get().getCompany().getId() + "";
				computerDto.companyName = optional.get().getCompany().getName();
			}
		}

		return computerDto;

	}

	public ComputerDTO fromComputer(Computer computer) {

		ComputerDTO computerDto = new ComputerDTO();

		computerDto.id = computer.getId() + "";
		computerDto.name = computer.getName();

		if (computer.getIntroduced() != null) {
			computerDto.introduced = computer.getIntroduced().toString();
		}
		if (computer.getDiscontinued() != null) {
			computerDto.discontinued = computer.getDiscontinued().toString();
		}
		if (computer.getCompany() != null) {
			computerDto.companyId = computer.getCompany().getId() + "";
			computerDto.companyName = computer.getCompany().getName();
		}

		return computerDto;

	}
}
