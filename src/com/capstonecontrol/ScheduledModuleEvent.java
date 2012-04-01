package com.capstonecontrol;

import java.util.Date;

public class ScheduledModuleEvent {
	String moduleName;
	Long value;
	String moduleType;
	String user;
	Date date;
	Date schedDate;
	String action;
	Long hours, minutes, day, month, year, timeOffset;
	boolean active, reoccurence;
	boolean Sun, Mon, Tue, Wed, Thu, Fri, Sat;

	public ScheduledModuleEvent(String moduleName, String moduleType,
			String user, String action, Date date, Long value, boolean mon,
			boolean tue, boolean wed, boolean thu, boolean fri, boolean sat,
			boolean sun, boolean active, Long hours, Long minutes,
			Date schedDate, Long year, Long day, boolean reoccurence, Long month, Long timeOffset) {
		this.moduleName = moduleName;
		this.moduleType = moduleType;
		this.user = user;
		this.action = action;
		this.date = date;
		this.value = value;
		this.Mon = mon;
		this.Tue = tue;
		this.Wed = wed;
		this.Thu = thu;
		this.Fri = fri;
		this.Sat = sat;
		this.Sun = sun;
		this.active = active;
		this.reoccurence = reoccurence;
		this.hours = hours;
		this.minutes = minutes;
		this.day = day;
		this.year = year;
		this.schedDate = schedDate;
		this.month = month;
		this.timeOffset = timeOffset;
	}

	public Long getDay(){
		return day;
	}
	
	public Long getYear(){
		return year;
	}
	
	public boolean getReoccurence(){
		return reoccurence;
	}
	
	public Date getSchedDate(){
		return schedDate;
	}
	
	public Long getMinutes() {
		return minutes;
	}

	public Long getHours() {
		return hours;
	}

	public void setMinutes(Long minutes) {
		this.minutes = minutes;
	}

	public void setHours(Long hours) {
		this.hours = hours;
	}

	public boolean getActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean getMon() {
		return Mon;
	}

	public boolean getTue() {
		return Tue;
	}

	public boolean getWed() {
		return Wed;
	}

	public boolean getThu() {
		return Thu;
	}

	public boolean getFri() {
		return Fri;
	}

	public boolean getSat() {
		return Sat;
	}

	public boolean getSun() {
		return Sun;
	}

	public void setMon(boolean Mon) {
		this.Mon = Mon;
	}

	public void setTue(boolean Tue) {
		this.Tue = Tue;
	}

	public void setWed(boolean Wed) {
		this.Wed = Wed;
	}

	public void setThu(boolean Thu) {
		this.Thu = Thu;
	}

	public void setFri(boolean Fri) {
		this.Fri = Fri;
	}

	public void setSat(boolean Sat) {
		this.Sat = Sat;
	}

	public void setSun(boolean Sun) {
		this.Sun = Sun;
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

	public Long getValue() {
		return value;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public Long getMonth(){
		return month;
	}
	
	public Long getTimeOffset(){
		return timeOffset;
	}

	// @TODO figure out why this is needed and how its used
	int getVersion() {
		return (Integer) null;
	}

	// @TODO figure out why this is needed and how its used
	Long getId() {
		return null;
	}

	public static ScheduledModuleEvent findModuleInfo(Long id) {
		return null;

	}
}
