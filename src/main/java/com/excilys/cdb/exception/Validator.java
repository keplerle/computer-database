package com.excilys.cdb.exception;

import java.time.LocalDate;

import com.excilys.cdb.model.Computer;

public class Validator {

	public static boolean dateValidator(LocalDate introduced, LocalDate discontinued) {
		if (discontinued != null && introduced != null && discontinued.isBefore(introduced)) {
			return false;
		}
		return true;
	}
	
	public static boolean nameValidator(String name) {
		if(name==null||name.equals("")) {
			return false;
		}
		return true;
	}
	
	public static boolean computerValidator(Computer computer) {
		
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
