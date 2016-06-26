/**
 * 
 */
package org.apache.airavata.grouper.resource;

import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSIONS_ATTRIBUTE_DEF;
import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSIONS_STEM_NAME;
import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSION_READ_ACTION;
import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSION_WRITE_ACTION;
import static org.apache.airavata.grouper.resource.ResourceType.EXPERIMENT;
import static org.apache.airavata.grouper.resource.ResourceType.PROJECT;

import org.apache.airavata.grouper.ResourceNotFoundException;

import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.attr.AttributeDef;
import edu.internet2.middleware.grouper.attr.AttributeDefName;
import edu.internet2.middleware.grouper.attr.AttributeDefNameSave;
import edu.internet2.middleware.grouper.attr.AttributeDefSave;
import edu.internet2.middleware.grouper.attr.AttributeDefType;
import edu.internet2.middleware.grouper.attr.assign.AttributeAssignAction;
import edu.internet2.middleware.grouper.attr.finder.AttributeDefFinder;
import edu.internet2.middleware.grouper.attr.finder.AttributeDefNameFinder;
import edu.internet2.middleware.grouper.misc.SaveMode;

/**
 * @author vsachdeva
 *
 */
public class ResourceServiceImpl {
  
  
  //TODO: break this method into smaller methods
  public void createResource(Resource resource) throws ResourceNotFoundException {
    
    validateResource(resource);
    
    GrouperSession grouperSession = null;
    try {
      grouperSession = GrouperSession.startRootSession();
      AttributeDefName parentAttributeDefName = null;
      
      // make sure that the parent resource exists in grouper if it is in the request
      if (resource.getParentResourceId() != null) {
        parentAttributeDefName = AttributeDefNameFinder.findByName(PERMISSIONS_STEM_NAME+resource.getResourceType().getParentResoruceType()+"_"+resource.getParentResourceId(), false);
        if (parentAttributeDefName == null) {
          throw new ResourceNotFoundException(resource.getParentResourceId() +" was not found.");
        }
      }
      
      // create an attribute def if doesn't exist
      AttributeDef attributeDef = AttributeDefFinder.findByName(PERMISSIONS_ATTRIBUTE_DEF, false);
      if (attributeDef == null) {
        AttributeDefSave attributeDefSave = new AttributeDefSave(grouperSession);
        attributeDef = attributeDefSave.assignAttributeDefType(AttributeDefType.perm).assignToGroup(true)
          .assignToEffMembership(true).assignName(PERMISSIONS_ATTRIBUTE_DEF).assignCreateParentStemsIfNotExist(true)
          .assignSaveMode(SaveMode.INSERT_OR_UPDATE).save();
        AttributeAssignAction read = attributeDef.getAttributeDefActionDelegate().addAction(PERMISSION_READ_ACTION);
        AttributeAssignAction write = attributeDef.getAttributeDefActionDelegate().addAction(PERMISSION_WRITE_ACTION);
        write.getAttributeAssignActionSetDelegate().addToAttributeAssignActionSet(read);
      }
      
      // create attribute def name
      AttributeDefNameSave attributeDefNameSave = new AttributeDefNameSave(grouperSession, attributeDef);
      attributeDefNameSave.assignCreateParentStemsIfNotExist(true);
      attributeDefNameSave.assignSaveMode(SaveMode.INSERT_OR_UPDATE);
      attributeDefNameSave.assignAttributeDefNameNameToEdit(PERMISSIONS_STEM_NAME+resource.getResourceType().toString()+"_"+resource.getResourceId());
      attributeDefNameSave.assignName(PERMISSIONS_STEM_NAME+resource.getResourceType().toString()+"_"+resource.getResourceId());
      attributeDefNameSave.assignDescription(resource.getResourceDescription());
      attributeDefNameSave.assignDisplayName(resource.getResourceName());
      AttributeDefName attributeDefName = attributeDefNameSave.save();
      
      // set the inheritance if parent attribute def name is not null
      if (parentAttributeDefName != null) {
        parentAttributeDefName.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(attributeDefName);
        parentAttributeDefName.store();
      }
      
    } finally {
      GrouperSession.stopQuietly(grouperSession);
    }
  }
  
  private void validateResource(Resource resource) {
    //TODO: throw some IllegalArgumentException
  }
  
  public static void main(String[] args) {
    ResourceServiceImpl resourceService = new ResourceServiceImpl();
    
    // create a Project resource
    Resource projectResource = new Resource();
    projectResource.setResourceId("project resource id");
    projectResource.setResourceDescription("project resource description");
    projectResource.setResourceName("project resource name");
    projectResource.setResourceType(PROJECT);
    resourceService.createResource(projectResource);
    
    // create an Experiment resource
    Resource experimentResource = new Resource();
    experimentResource.setResourceId("experiment resource id");
    experimentResource.setResourceDescription("experiment resource description");
    experimentResource.setResourceName("experiment resource name");
    experimentResource.setResourceType(EXPERIMENT);
    experimentResource.setParentResourceId("project resource id");
    resourceService.createResource(experimentResource);
    
    Resource experimentResource1 = new Resource();
    experimentResource1.setResourceId("experiment resource id1");
    experimentResource1.setResourceDescription("experiment resource description1");
    experimentResource1.setResourceName("experiment resource name1");
    experimentResource1.setResourceType(EXPERIMENT);
    experimentResource1.setParentResourceId("project resource id");
    resourceService.createResource(experimentResource1);
  }

}
