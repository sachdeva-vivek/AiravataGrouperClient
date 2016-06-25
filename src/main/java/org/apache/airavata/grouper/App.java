package org.apache.airavata.grouper;

import edu.internet2.middleware.grouper.Group;
import edu.internet2.middleware.grouper.GroupFinder;
import edu.internet2.middleware.grouper.GroupSave;
import edu.internet2.middleware.grouper.GrouperSession;
import edu.internet2.middleware.grouper.Stem;
import edu.internet2.middleware.grouper.StemFinder;
import edu.internet2.middleware.grouper.StemSave;
import edu.internet2.middleware.grouper.SubjectFinder;
import edu.internet2.middleware.grouper.attr.AttributeDef;
import edu.internet2.middleware.grouper.attr.AttributeDefName;
import edu.internet2.middleware.grouper.attr.AttributeDefNameSave;
import edu.internet2.middleware.grouper.attr.AttributeDefSave;
import edu.internet2.middleware.grouper.attr.AttributeDefType;
import edu.internet2.middleware.grouper.attr.assign.AttributeAssignAction;
import edu.internet2.middleware.grouper.attr.finder.AttributeDefFinder;
import edu.internet2.middleware.grouper.group.TypeOfGroup;
import edu.internet2.middleware.grouper.permissions.PermissionFinder;
import edu.internet2.middleware.grouper.permissions.role.Role;
import edu.internet2.middleware.subject.Subject;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GrouperSession rootSession = GrouperSession.startRootSession();
        
//        Subject subj = SubjectFinder.findById("GrouperSystem", false);
//
//        //Find the root stem
//        //you should always close your sessions, also, the are in a threadlocal if already started
//        //Stem root = StemFinder.findRootStem(GrouperSession.start(SubjectFinder.findRootSubject()));
//
//        Stem root = StemFinder.findRootStem(GrouperSession.staticGrouperSession());
//
//        //Create top level stem, I would use the Save API
//        //    Stem etc = root.addChildStem("etc", "Grouper Administration");
//
//        Stem etc = new StemSave(rootSession).assignName("etc").assignCreateParentStemsIfNotExist(true).save();
//
//        //Create child group, again, I would use the Sae api
//        //    Stem etc = root.addChildStem("etc", "Grouper Administration");
//        //    Group wheel = etc.addChildGroup("wheel", "Wheel Group");
//
//        Group wheel = new GroupSave(rootSession).assignName("etc:wheel").assignCreateParentStemsIfNotExist(true).save();
//
//        // the "All" subject is for privileges, it wont have an effect if you add it to a group, though
//        // you just might have been doing an example...
//        //  Subject all = SubjectFinder.findAllSubject();
//        //  if ( !wheel.hasMember(all) ) {
//
//        wheel.addMember(subj, false);
//        
//        System.out.println(wheel.getMembers());
//        
//        GrouperSession.stopQuietly(rootSession);
//        
//        rootSession = GrouperSession.startRootSession();
//        
//        Group group = GroupFinder.findByName(rootSession, "etc:wheel", true);
//        
//        System.out.println(group.getMembers());
        
        
        
        Stem permissionStem = new StemSave(rootSession).assignName("PermissionStem").assignCreateParentStemsIfNotExist(true).save();
        
        Role role = permissionStem.addChildRole("TestRole1", "TestRole1");
        
        Subject subject = SubjectFinder.findById("test.subject.0", false);

        role.addMember(subject, false);
        
        
        //AttributeDef attributeDef = permissionStem.addChildAttributeDef("PermissionDef1", AttributeDefType.perm);
        
        Stem top = new StemSave(rootSession).assignName("top1").assignDisplayExtension("top display name1").save();
        Role adminRole = new GroupSave(rootSession).assignName("top1:admin").assignTypeOfGroup(TypeOfGroup.role).save();
        Role seniorAdmin = new GroupSave(rootSession).assignName("top1:seniorAdmin").assignTypeOfGroup(TypeOfGroup.role).save();
        seniorAdmin.getRoleInheritanceDelegate().addRoleToInheritFromThis(adminRole);
        Group user = new GroupSave(rootSession).assignName("top1:user").assignTypeOfGroup(TypeOfGroup.role).save();
         
        AttributeDef permissionDef = new AttributeDefSave(rootSession).assignName("top1:permissionDef").assignAttributeDefType(AttributeDefType.perm).assignToEffMembership(true).assignToGroup(true).save();
        AttributeDefName english = new AttributeDefNameSave(rootSession, permissionDef).assignName("top1:english").assignDisplayExtension("English1").save();
        AttributeDefName math = new AttributeDefNameSave(rootSession, permissionDef).assignName("top1:math").assignDisplayExtension("Math1").save();
        AttributeDefName electricalEngineering = new AttributeDefNameSave(rootSession, permissionDef).assignName("top1:electricalEngineering").assignDisplayExtension("Electrical Engineering1").save();
        AttributeDefName chemicalEngineering = new AttributeDefNameSave(rootSession, permissionDef).assignName("top1:chemicalEngineering").assignDisplayExtension("Chemical Engineering1").save();
        AttributeDefName artsAndSciences = new AttributeDefNameSave(rootSession, permissionDef).assignName("top1:artsAndSciences").assignDisplayExtension("Arts and Sciences1").save();
        AttributeDefName engineering = new AttributeDefNameSave(rootSession, permissionDef).assignName("top1:engineering").assignDisplayExtension("Engineering1").save();
        AttributeDefName all = new AttributeDefNameSave(rootSession, permissionDef).assignName("top1:all").assignDisplayExtension("All1").save();
         
        all.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(engineering);
        all.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(artsAndSciences);
        artsAndSciences.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(english);
        artsAndSciences.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(math);
        engineering.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(math);
        engineering.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(electricalEngineering);
        engineering.getAttributeDefNameSetDelegate().addToAttributeDefNameSet(chemicalEngineering);
         
        permissionDef.getAttributeDefActionDelegate().configureActionList("read1, write1, readWrite1, admin1");
        AttributeAssignAction read = permissionDef.getAttributeDefActionDelegate().findAction("read1", true);
        AttributeAssignAction write = permissionDef.getAttributeDefActionDelegate().findAction("write1", true);
        AttributeAssignAction readWrite = permissionDef.getAttributeDefActionDelegate().findAction("readWrite1", true);
        AttributeAssignAction admin = permissionDef.getAttributeDefActionDelegate().findAction("admin1", true);
         
        readWrite.getAttributeAssignActionSetDelegate().addToAttributeAssignActionSet(read);
        readWrite.getAttributeAssignActionSetDelegate().addToAttributeAssignActionSet(write);
        admin.getAttributeAssignActionSetDelegate().addToAttributeAssignActionSet(readWrite);
        
        adminRole.addMember(subject, false);
        user.addMember(subject, false);
        
    }
    
    
    private void assignPermission() {
      
    }
}
