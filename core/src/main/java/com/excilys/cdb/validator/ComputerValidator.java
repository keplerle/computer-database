package com.excilys.cdb.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DateException;
import com.excilys.cdb.exception.NameException;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerValidator {

	private void dateValidator(LocalDate introduced, LocalDate discontinued) throws DateException {
		if (discontinued != null && introduced != null && discontinued.isBefore(introduced)) {
			throw new DateException();
		}
	}

	private void nameValidator(String name) throws NameException {
		if (name == null || name.equals("")) {
			throw new NameException();
		}
	}

	public void computerValidator(Computer computer) throws DataException {
		nameValidator(computer.getName());
		dateValidator(computer.getIntroduced(), computer.getDiscontinued());
	}
}
