package com.capstonecontrol;

import java.util.Date;

public class ModuleEvent {
	String moduleName;
	String value;
	String moduleType;
	String user;
	Date date;
	String action;

	public ModuleEvent(String moduleName, String moduleType, String user,
			String action, Date date) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
		this.action = action;
		this.date = date;
	}

	public String getModuleName() {
		return moduleName;
	}

	public String getModuleType() {
		return moduleType;
	}

	public String getUser() {
		return user;
	}

	public Date getDate() {
		return date;
	}
	
	public String getAction(){
		return action;
	}

	// @TODO figure out why this is needed and how its used
	int getVersion() {
		return (Integer) null;
	}

	// @TODO figure out why this is needed and how its used
	Long getId() {
		return null;
	}

	public static ModuleEvent findModuleInfo(Long id) {
		return null;

	}
}
