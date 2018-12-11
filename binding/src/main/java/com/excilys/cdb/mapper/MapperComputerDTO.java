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

		computer.setName(computerDto.getName());

		if (computerDto.getId() != null) {
			computer.setId(Long.parseLong(computerDto.getId()));
		}
		if (computerDto.introduced != null && !("".equals(computerDto.introduced))) {
			computer.setIntroduced(Date.valueOf(computerDto.introduced).toLocalDate());
		}
		if (computerDto.discontinued != null && !("".equals(computerDto.discontinued))) {
			computer.setDiscontinued(Date.valueOf(computerDto.discontinued).toLocalDate());
		}
		if(computerDto.getCompanyId() != null) {
			company.setId(Long.parseLong(computerDto.getCompanyId()));
		}	
		computer.setCompany(company);
		return computer;

	}

	public ComputerDTO fromOptionalComputer(Optional<Computer> optional) {

		ComputerDTO computerDto = new ComputerDTO();
		if (optional.isPresent()) {
			computerDto.setId(optional.get().getId() + "");
			computerDto.setName(optional.get().getName());

			if (optional.get().getIntroduced() != null) {
				computerDto.introduced = optional.get().getIntroduced().toString();
			}
			if (optional.get().getDiscontinued() != null) {
				computerDto.discontinued = optional.get().getDiscontinued().toString();
			}
			if (optional.get().getCompany() != null) {
				computerDto.setCompanyId(optional.get().getCompany().getId() + "");
				computerDto.setCompanyName(optional.get().getCompany().getName());
			}
		}

		return computerDto;

	}

	public ComputerDTO fromComputer(Computer computer) {

		ComputerDTO computerDto = new ComputerDTO();

		computerDto.setId(computer.getId() + "");
		computerDto.setName(computer.getName());

		if (computer.getIntroduced() != null) {
			computerDto.introduced = computer.getIntroduced().toString();
		}
		if (computer.getDiscontinued() != null) {
			computerDto.discontinued = computer.getDiscontinued().toString();
		}
		if (computer.getCompany() != null) {
			computerDto.setCompanyId(computer.getCompany().getId() + "");
			computerDto.setCompanyName(computer.getCompany().getName());
		}

		return computerDto;

	}
}
