package com.excilys.cdb.model;

public class Page {

	private static int pageNumber = 1;
	private static int pageSize = 10;

	private Page() {

	}

	public static int getPageNumber() {
		return pageNumber;
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPage(String page, String size) {
		if (page != null) {
			Page.pageNumber = Integer.parseInt(page);
		} else {
			Page.pageNumber = 1;
		}
		if (size != null) {
			Page.pageSize = Integer.parseInt(size);
		}
	}

	public static void increasePage() {
		Page.pageNumber += 1;
	}

	public static void decreasePage() {
		Page.pageNumber -= 1;
	}

}