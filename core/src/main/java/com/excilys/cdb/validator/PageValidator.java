package com.excilys.cdb.validator;

import java.util.List;

import org.springframework.stereotype.Component;

import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
@Component
public class PageValidator {

	public void previousPageValidator() throws NoPreviousPageException {
		if (Page.getPageSize() <= 0 || Page.getPageNumber() <= 0) {
			throw new NoPreviousPageException();
		}
	}

	public void nextPageValidator(List<Computer> sourceList) throws NoNextPageException{
		if( Page.getPageNumber()>1 && sourceList.isEmpty() )
	{
		throw new NoNextPageException();
	}
}

}
