/**
 * 
 */
package org.apache.airavata.grouper.resource;

import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSIONS_ATTRIBUTE_DEF;
import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSIONS_STEM_NAME;
import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSION_READ_ACTION;
import static org.apache.airavata.grouper.AiravataGrouperUtil.PERMISSION_WRITE_ACTION;

import java.util.Set;

import org.apache.airavata.grouper.ResourceNotFoundException;
import org.apache.airavata.grouper.role.RoleServiceImpl;

import edu.internet2.middleware.grouper.Group;
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
import edu.internet2.middleware.grouper.permissions.PermissionAllowed;

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
        parentAttributeDefName = AttributeDefNameFinder.findByName(PERMISSIONS_STEM_NAME+resource.getParentResourceId(), false);
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
      attributeDefNameSave.assignAttributeDefNameNameToEdit(PERMISSIONS_STEM_NAME+resource.getResourceId());
      attributeDefNameSave.assignName(PERMISSIONS_STEM_NAME+resource.getResourceId());
      attributeDefNameSave.assignDescription(resource.getResourceDescription());
      attributeDefNameSave.assignDisplayName(resource.getResourceName());
      AttributeDefName attributeDefName = attributeDefNameSave.save();
      
      // set the inheritance if parent attribute def name is not null
      if (parentAttributeDefName != null) {
        parentAttributeDefName.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(attributeDefName);
      }
      
      RoleServiceImpl roleService = new RoleServiceImpl();
      //TODO remove the session being passed
      Group readRole = roleService.createRole(resource.getResourceId()+"_"+PERMISSION_READ_ACTION, grouperSession);
      Group writeRole = roleService.createRole(resource.getResourceId()+"_"+PERMISSION_WRITE_ACTION, grouperSession);
      
      readRole.getPermissionRoleDelegate().assignRolePermission(PERMISSION_READ_ACTION, attributeDefName, PermissionAllowed.ALLOWED);
      writeRole.getPermissionRoleDelegate().assignRolePermission(PERMISSION_WRITE_ACTION, attributeDefName, PermissionAllowed.ALLOWED);
      writeRole.getRoleInheritanceDelegate().addRoleToInheritFromThis(readRole);
      
    } finally {
      GrouperSession.stopQuietly(grouperSession);
    }
  }
  
  public void deleteResource(String resourceId) throws ResourceNotFoundException {
    //TODO validate the input
    GrouperSession grouperSession = null;
    try {
      grouperSession = GrouperSession.startRootSession();
      AttributeDefName attributeDefName = AttributeDefNameFinder.findByName(PERMISSIONS_STEM_NAME+resourceId, false);
      if (attributeDefName == null) {
        throw new ResourceNotFoundException(resourceId +" was not found.");
      }
      RoleServiceImpl roleService = new RoleServiceImpl();
      // delete all the children resources and roles
      for (AttributeDefName childAttributeDefName: attributeDefName.getAttributeDefNameSetDelegate().getAttributeDefNamesImpliedByThis()) {
        childAttributeDefName.delete();
        // don't change the order since write inherits read
        roleService.deleteRole(childAttributeDefName.getExtension()+"_"+PERMISSION_WRITE_ACTION, grouperSession);
        roleService.deleteRole(childAttributeDefName.getExtension()+"_"+PERMISSION_READ_ACTION, grouperSession);
      }
      attributeDefName.delete();
      // don't change the order since write inherits read
      roleService.deleteRole(resourceId+"_"+PERMISSION_WRITE_ACTION, grouperSession);
      roleService.deleteRole(resourceId+"_"+PERMISSION_READ_ACTION, grouperSession);
    } finally {
      GrouperSession.stopQuietly(grouperSession);
    }
  }
  
  public Resource getResource(String resourceId) throws ResourceNotFoundException {
    //TODO validate the input
    GrouperSession grouperSession = null;
    Resource resource = null;
    try {
      grouperSession = GrouperSession.startRootSession();
      AttributeDefName attributeDefName = AttributeDefNameFinder.findByName(PERMISSIONS_STEM_NAME+resourceId, false);
      if (attributeDefName == null) {
        throw new ResourceNotFoundException(resourceId +" was not found.");
      }
      resource = new Resource();
      resource.setResourceDescription(attributeDefName.getDescription());
      resource.setResourceId(resourceId);
      resource.setResourceName(attributeDefName.getDisplayExtension());
      Set<AttributeDefName> parentAttributeDefNames = attributeDefName.getAttributeDefNameSetDelegate().getAttributeDefNamesThatImplyThisImmediate();
      if (parentAttributeDefNames != null && parentAttributeDefNames.size() > 0) {
        resource.setParentResourceId(parentAttributeDefNames.iterator().next().getExtension());
      }
    } finally {
      GrouperSession.stopQuietly(grouperSession);
    }
    return resource;
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
    resourceService.createResource(projectResource);
    
    // create an Experiment resource
    Resource experimentResource = new Resource();
    experimentResource.setResourceId("experiment resource id");
    experimentResource.setResourceDescription("experiment resource description");
    experimentResource.setResourceName("experiment resource name");
    experimentResource.setParentResourceId("project resource id");
    resourceService.createResource(experimentResource);
    
    //create another experiment resource within the same project resource
    Resource experimentResource1 = new Resource();
    experimentResource1.setResourceId("experiment resource id1");
    experimentResource1.setResourceDescription("experiment resource description1");
    experimentResource1.setResourceName("experiment resource name1");
    experimentResource1.setParentResourceId("project resource id");
    resourceService.createResource(experimentResource1);
    
    // create a data file resource
    Resource dataResource = new Resource();
    dataResource.setResourceId("data resource id");
    dataResource.setResourceDescription("data resource description");
    dataResource.setResourceName("data resource name");
    dataResource.setParentResourceId("experiment resource id1");
    resourceService.createResource(dataResource);
    
    // get the experiment resource and it should have parent set to project
    Resource resource = resourceService.getResource("experiment resource id1");
    System.out.println(resource);
    //delete the project resource, it will delete all the children/experiment resources as well
    resourceService.deleteResource("project resource id");
  }

}
