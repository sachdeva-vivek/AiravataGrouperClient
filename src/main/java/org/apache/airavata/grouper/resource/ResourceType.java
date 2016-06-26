package org.apache.airavata.grouper.resource;


public enum ResourceType {
  
  PROJECT,
  EXPERIMENT,
  DATA,
  OTHER;
  
  public ResourceType getParentResoruceType() {
    
    switch (this) {
      case EXPERIMENT:
        return PROJECT;
      case DATA:
        return EXPERIMENT;
      default:
        return null;
    }
    
  }

}
