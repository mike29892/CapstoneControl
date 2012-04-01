package com.capstonecontrol;


import java.util.Date;

public class PowerData {
	String moduleName;
	String data;
	String user;
	Date date;

	Long id;

	public String getModuleName() {
		return moduleName;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}
	
	
	
	public String getData(){
		return data;
	}

	PowerData(String moduleName, String data, String user,Date date) {
		this.moduleName = moduleName;
		this.user = user;
		this.date = date;
		this.data = data;
	}


}