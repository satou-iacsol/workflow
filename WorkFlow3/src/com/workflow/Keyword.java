package com.workflow;

public class Keyword {
	static final private String url = "jdbc:postgresql://localhost:5432/postgres";
	static final private String user = "postgres";
	static final private String password = "0978781";

	static String url () {
		return url;
	}
	static String user () {
		return user;
	}
	static String password () {
		return password;
	}
}
