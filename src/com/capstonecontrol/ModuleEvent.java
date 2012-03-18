package com.capstonecontrol;

import java.util.Date;

public class ModuleEvent {
	String moduleName;
	String value;
	String moduleType;
	String user;
	Date date;
	String action;
	int occuranceCount = 0;

	public ModuleEvent(String moduleName, String moduleType, String user,
			String action, Date date, String value) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
		this.action = action;
		this.date = date;
		this.value = value;
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

	public String getAction() {
		return action;
	}

	public String getValue() {
		return value;
	}

	public int getOccuranceCount() {
		return occuranceCount;
	}

	public void setOccuranceCount(int occuranceCount) {
		this.occuranceCount = occuranceCount;
	}

	public void incrementOccuranceCount() {
		occuranceCount = occuranceCount + 1;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean compareEventsForSuggest(ModuleEvent moduleEvent) {
		if (this.moduleName.equals(moduleEvent.getModuleName())
				&& this.moduleType.equals(moduleEvent.getModuleType())
				&& this.action.equals(moduleEvent.getAction())
				&& this.date.equals(moduleEvent.getDate())) {
			return true;
		}
		return false;
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
