package com.capstonecontrol;

public class ModuleInfo {
	String moduleName;
	String moduleMacAddr;
	String moduleType;
	String user;
	String date;
	
	public ModuleInfo(String moduleMacAddr, String moduleName, String moduleType, String user){
		this.moduleMacAddr = moduleMacAddr;
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
	}

	public String getModuleName(){
		return moduleName;
	}
	
	public String getModuleMacAddr(){
		return moduleMacAddr;
	}
	
	public String getModuleType(){
		return moduleType;
	}
	
	public String getUser(){
		return user;
	}
	
	public String getDate(){
		return date;
	}
	
	//@TODO figure out why this is needed and how its used
	int getVersion(){
		return (Integer) null;
	}
	
	//@TODO figure out why this is needed and how its used
	Long getId() {
		return null;
	}
	
	public static ModuleInfo findModuleInfo(Long id){
		return null;
		
	}
}
