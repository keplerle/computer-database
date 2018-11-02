package com.excilys.cdb.model;

public class Page {

	private static int page=1;
	private static int pageSize=10;


	public static int getPage() {
		return page;
	}

	public static int getPageSize() {
		return pageSize;
	}

	public static void setPage(String page, String size) {
		if (page != null) {
			Page.page=Integer.parseInt(page);
		} else {
			Page.page=1;
		}
		if (size != null) {
			Page.pageSize=Integer.parseInt(size);
		}
	}
	
	public static void increasePage() {
		Page.page += 1;
	}
	
	public static void decreasePage() {
		Page.page -= 1;
	}

}