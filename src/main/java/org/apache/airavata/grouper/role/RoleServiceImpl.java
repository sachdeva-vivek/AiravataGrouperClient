/**
 * 
 */
package org.apache.airavata.grouper.role;

import static org.apache.airavata.grouper.AiravataGrouperUtil.ROLES_STEM_NAME;

import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.GroupFinder;
import edu.internet2.middleware.grouper.GroupSave;
import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.exception.GroupNotFoundException;
import edu.internet2.middleware.grouper.group.TypeOfGroup;
import edu.internet2.middleware.grouper.misc.SaveMode;

/**
 * @author vsachdeva
 *
 */
public class RoleServiceImpl {
  
  
  public Group createRole(String roleId, GrouperSession session) {
    
    GrouperSession grouperSession = null;
    Group role = null;
    try {
      grouperSession = session != null? session : GrouperSession.startRootSession();
      GroupSave groupSave = new GroupSave(grouperSession);
      groupSave.assignTypeOfGroup(TypeOfGroup.role);
      groupSave.assignGroupNameToEdit(ROLES_STEM_NAME+roleId);
      groupSave.assignName(ROLES_STEM_NAME+roleId);
      groupSave.assignDisplayExtension(roleId);
      groupSave.assignDescription(roleId);
      groupSave.assignSaveMode(SaveMode.INSERT_OR_UPDATE);
      groupSave.assignCreateParentStemsIfNotExist(true);
      role = groupSave.save();
    } finally {
      if (session == null) {
        GrouperSession.stopQuietly(grouperSession);
      }
    }
    return role;
  }
  
  public void deleteRole(String roleId) throws GroupNotFoundException {
    GrouperSession grouperSession = null;
    try {
      grouperSession = GrouperSession.startRootSession();
      edu.internet2.middleware.grouper.Group role = GroupFinder.findByName(grouperSession, ROLES_STEM_NAME+roleId, true);
      role.delete();
    } finally {
      GrouperSession.stopQuietly(grouperSession);
    }
  }
  
  public static void main(String[] args) {
    RoleServiceImpl roleServiceImpl = new RoleServiceImpl();
    roleServiceImpl.createRole("test_role", null);
    roleServiceImpl.deleteRole("test_role");
  }

}
