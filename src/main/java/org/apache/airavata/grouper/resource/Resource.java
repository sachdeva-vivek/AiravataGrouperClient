/**
 * 
 */
package org.apache.airavata.grouper.resource;

import java.util.List;
import java.util.Map;

/**
 * @author vsachdeva
 *
 */
public class Resource {
  
  private String resourceId;
  
  private String resourceName;
  
  private ResourceType resourceType;
  
  private String ownerId;
  
  private String resourceDescription;
  
  private String creationTime;
  
  private String parentResourceId;

  private List<Resource> childResources;
  
  private Map<String, String> metadata;

  
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
   * @return the ownerId
   */
  public String getOwnerId() {
    return ownerId;
  }

  
  /**
   * @param ownerId the ownerId to set
   */
  public void setOwnerId(String ownerId) {
    this.ownerId = ownerId;
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
   * @return the creationTime
   */
  public String getCreationTime() {
    return creationTime;
  }

  
  /**
   * @param creationTime the creationTime to set
   */
  public void setCreationTime(String creationTime) {
    this.creationTime = creationTime;
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
   * @return the childResources
   */
  public List<Resource> getChildResources() {
    return childResources;
  }

  
  /**
   * @param childResources the childResources to set
   */
  public void setChildResources(List<Resource> childResources) {
    this.childResources = childResources;
  }

  
  /**
   * @return the metadata
   */
  public Map<String, String> getMetadata() {
    return metadata;
  }

  
  /**
   * @param metadata the metadata to set
   */
  public void setMetadata(Map<String, String> metadata) {
    this.metadata = metadata;
  }
  
  
 

}
