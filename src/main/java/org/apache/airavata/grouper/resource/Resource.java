/**
 * 
 */
package org.apache.airavata.grouper.resource;

/**
 * @author vsachdeva
 *
 */
public class Resource {
  
  private String resourceId;
  
  private String resourceName;
  
  private ResourceType resourceType;
  
  private String resourceDescription;
  
  private String parentResourceId;
  
  
  /**
   * @return the resourceId
   */
  public String getResourceId() {
    return resourceId;
  }

  
  /**
   * @param resourceId the resourceId to set
   */
  public void setResourceId(String resourceId) {
    this.resourceId = resourceId;
  }

  
  /**
   * @return the resourceName
   */
  public String getResourceName() {
    return resourceName;
  }

  
  /**
   * @param resourceName the resourceName to set
   */
  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  
  /**
   * @return the resourceType
   */
  public ResourceType getResourceType() {
    return resourceType;
  }

  
  /**
   * @param resourceType the resourceType to set
   */
  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }


  /**
   * @return the resourceDescription
   */
  public String getResourceDescription() {
    return resourceDescription;
  }

  
  /**
   * @param resourceDescription the resourceDescription to set
   */
  public void setResourceDescription(String resourceDescription) {
    this.resourceDescription = resourceDescription;
  }


  /**
   * @return the parentResourceId
   */
  public String getParentResourceId() {
    return parentResourceId;
  }


  /**
   * @param parentResourceId the parentResourceId to set
   */
  public void setParentResourceId(String parentResourceId) {
    this.parentResourceId = parentResourceId;
  }
  
}
