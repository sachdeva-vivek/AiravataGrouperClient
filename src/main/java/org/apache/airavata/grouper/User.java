/**
 * 
 */
package org.apache.airavata.grouper;

/**
 * @author vsachdeva
 *
 */
public class User {
  
  private String airavataInternalUserId;
  
  private String userId;
  
  /**
   * @return the airavataInternalUserId
   */
  public String getAiravataInternalUserId() {
    return airavataInternalUserId;
  }

  
  /**
   * @param airavataInternalUserId the airavataInternalUserId to set
   */
  public void setAiravataInternalUserId(String airavataInternalUserId) {
    this.airavataInternalUserId = airavataInternalUserId;
  }

  
  /**
   * @return the userId
   */
  public String getUserId() {
    return userId;
  }

  
  /**
   * @param userId the userId to set
   */
  public void setUserId(String userId) {
    this.userId = userId;
  }
  
}
