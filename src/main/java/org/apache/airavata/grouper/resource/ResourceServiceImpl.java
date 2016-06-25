/**
 * 
 */
package org.apache.airavata.grouper.resource;

import org.apache.commons.lang.StringUtils;

import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.attr.AttributeDef;
import edu.internet2.middleware.grouper.attr.AttributeDefName;
import edu.internet2.middleware.grouper.attr.AttributeDefNameSave;
import edu.internet2.middleware.grouper.attr.finder.AttributeDefFinder;
import edu.internet2.middleware.grouper.internal.dao.QueryOptions;
import edu.internet2.middleware.grouper.misc.SaveMode;
import edu.internet2.middleware.grouper.misc.SaveResultType;
import edu.internet2.middleware.grouper.util.GrouperUtil;

/**
 * @author vsachdeva
 *
 */
public class ResourceServiceImpl {
  
  
  public void createResource(Resource resource) {
    
    //TODO: Create a new AttributeDefName with the name resource.getType()+"_"+resource.getName()
    //TODO Before you create it, make sure that the same name doesn't exist
    //TODO give inheritance
    AttributeDef attributeDef = AttributeDefFinder.findByName("airavata", false, new QueryOptions().secondLevelCache(false));
    AttributeDefNameSave attributeDefNameSave = new AttributeDefNameSave(GrouperSession.startRootSession(), attributeDef);
    //attributeDefNameSave.assignAttributeDefNameNameToEdit(null); // since we are creating a new one
    //attributeDefNameSave.assignUuid(this.getWsAttributeDefName().getUuid());
    attributeDefNameSave.assignName("PROJECT"); // change it to resource.getType
    attributeDefNameSave.assignDisplayExtension("change me");
    attributeDefNameSave.assignDescription("change me");
    attributeDefNameSave.assignSaveMode(SaveMode.INSERT_OR_UPDATE);
    attributeDefNameSave.assignCreateParentStemsIfNotExist(true);

    AttributeDefName attributeDefName = attributeDefNameSave.save();
    
    SaveResultType saveResultType = attributeDefNameSave.getSaveResultType();
  
  }

}
