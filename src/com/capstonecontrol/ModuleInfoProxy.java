package com.capstonecontrol;

import com.google.web.bindery.requestfactory.shared.EntityProxy;
import com.google.web.bindery.requestfactory.shared.ProxyFor;

@ProxyFor(ModuleInfo.class)
public interface ModuleInfoProxy extends EntityProxy {

  String getModuleName();
  
  String getModuleType();
  
  String getModuleMacAddr();
  
  String getDate();
  
  String getUser();
}