/**
 * 
 */
package org.apache.airavata.grouper.role;

import edu.internet2.middleware.grouper.GroupFinder;
import edu.internet2.middleware.grouper.GroupSave;
import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.group.TypeOfGroup;
import edu.internet2.middleware.grouper.internal.dao.QueryOptions;
import edu.internet2.middleware.grouper.misc.SaveMode;
import edu.internet2.middleware.grouper.misc.SaveResultType;

/**
 * @author vsachdeva
 *
 */
public class RoleServiceImpl {
  
  
  public void createRole(String role) {
    
    GrouperSession grouperSession = null;
    try {
      grouperSession = GrouperSession.startRootSession();
      GroupSave groupSave = new GroupSave(grouperSession);
      groupSave.assignTypeOfGroup(TypeOfGroup.role);
      groupSave.assignGroupNameToEdit(role);
      groupSave.assignName(role);
      groupSave.assignDescription(role);
      groupSave.assignSaveMode(SaveMode.INSERT_OR_UPDATE);
      groupSave.assignCreateParentStemsIfNotExist(true);
      groupSave.save();
    } finally {
      GrouperSession.stopQuietly(grouperSession);
    }
  }
  
  public void deleteRole(String roleName) {
    GrouperSession grouperSession = null;
    try {
      grouperSession = GrouperSession.startRootSession();
      edu.internet2.middleware.grouper.Group group = GroupFinder.findByName(grouperSession, roleName, 
          true, new QueryOptions().secondLevelCache(false));
      group.delete();
    } finally {
      GrouperSession.stopQuietly(grouperSession);
    }
  }

}
