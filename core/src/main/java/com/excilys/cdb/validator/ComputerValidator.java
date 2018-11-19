package com.excilys.cdb.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.DataException;
import com.excilys.cdb.exception.DateException;
import com.excilys.cdb.exception.NameException;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerValidator {

	private boolean dateValidator(LocalDate introduced, LocalDate discontinued) throws DateException {
		if (discontinued != null && introduced != null && discontinued.isBefore(introduced)) {
			throw new DateException();
		}
		return true;
	}
	
	private boolean nameValidator(String name) throws NameException {
		if(name==null||name.equals("")) {
			throw new NameException();
		}
		return true;
	}
	
	public boolean computerValidator(Computer computer) throws DataException  {
		
		boolean nameFlag = false;
		boolean dateFlag = false;
					nameFlag=nameValidator(computer.getName());
					dateFlag= dateValidator(computer.getIntroduced(),computer.getDiscontinued());
		if(nameFlag&&dateFlag) {
			return true;
		}
		
		return false;
	}
}
