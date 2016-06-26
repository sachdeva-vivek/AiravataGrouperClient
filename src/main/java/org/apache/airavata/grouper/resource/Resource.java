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


  /**
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "Resource [resourceId=" + resourceId + ", resourceName=" + resourceName
        + ", resourceDescription=" + resourceDescription + ", parentResourceId="
        + parentResourceId + "]";
  }

  
}
