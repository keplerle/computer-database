package com.excilys.cdb.model;

import java.util.List;

import com.excilys.cdb.exception.NoNextPageException;
import com.excilys.cdb.exception.NoPreviousPageException;

public class Page {

	private static int page=1;
	private static int pageSize=10;

	public static <T> List<T> pagination(List<T> sourceList)
			throws NoPreviousPageException, NoNextPageException {
		if (pageSize <= 0 || page <= 0) {
			throw new NoPreviousPageException();
		}

		int fromIndex = (page - 1) * pageSize;
		if (sourceList == null || sourceList.size() < fromIndex) {
			throw new NoNextPageException();
		}
		return sourceList.subList(fromIndex, Math.min(fromIndex + pageSize, sourceList.size()));
	}

	public static int getPage() {
		return page;
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPage(int page) {
		Page.page = page;
	}

	public static void setPageSize(int pageSize) {
		Page.pageSize = pageSize;
	}

}