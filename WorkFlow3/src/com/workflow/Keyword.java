package com.workflow;

public class Keyword {
	static final private String webSiteURL = "http://localhost:8080/WorkFlow3/login.jsp";
	static final private String url = "jdbc:postgresql://localhost:5432/postgres";
	static final private String user = "postgres";
	static final private String password = "0978781";
	static final private String type01 = "1.有給休暇";
	static final private String type02 = "2.代休";
	static final private String type03 = "3.生理休暇";
	static final private String type04 = "4.慶弔休暇";
	static final private String type05 = "5.特別休暇";
	static final private String type06 = "6.罹災休暇";
	static final private String type07 = "7.半休";
	static final private String type08 = "8.結婚休暇";
	static final private String type09 = "9.出産休暇";
	static final private String type10 = "10.忌引き休暇";
	static final private String type11 = "11.隔離休暇";
	static final private String type12 = "12.一周忌";
	static final private String type13 = "13.受験休暇";
	static final private String type14 = "14.産前産後休暇";

	static String webSiteURL () {
		return webSiteURL;
	}
	static String url () {
		return url;
	}
	static String user () {
		return user;
	}
	static String password () {
		return password;
	}
	static public String type (String type) {
		String type00 = "";
		switch (type) {
		case "01":
			type00 = type01;
			break;
		case "02":
			type00 = type02;
			break;
		case "03":
			type00 = type03;
			break;
		case "04":
			type00 = type04;
			break;
		case "05":
			type00 = type05;
			break;
		case "06":
			type00 = type06;
			break;
		case "07":
			type00 = type07;
			break;
		case "08":
			type00 = type08;
			break;
		case "09":
			type00 = type09;
			break;
		case "10":
			type00 = type10;
			break;
		case "11":
			type00 = type11;
			break;
		case "12":
			type00 = type12;
			break;
		case "13":
			type00 = type13;
			break;
		case "14":
			type00 = type14;
			break;
		}

		return type00;
	}

}
