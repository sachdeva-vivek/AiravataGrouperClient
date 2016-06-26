package org.apache.airavata.grouper;

import java.util.Set;

import edu.internet2.middleware.subject.SourceUnavailableException;
import edu.internet2.middleware.subject.Subject;
import edu.internet2.middleware.subject.SubjectNotFoundException;
import edu.internet2.middleware.subject.SubjectNotUniqueException;
import edu.internet2.middleware.subject.provider.BaseSourceAdapter;

public class BaseSourceAdapterExtension extends BaseSourceAdapter {

  public void checkConfig() {
    // TODO Auto-generated method stub
    
  }

  public String printConfig() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Subject getSubject(String id1)
      throws SubjectNotFoundException, SubjectNotUniqueException {
    // TODO Auto-generated method stub
    
    // make the service call to airavata java/http return the object
    return null;
  }

  @Override
  public Subject getSubjectByIdentifier(String id1)
      throws SubjectNotFoundException, SubjectNotUniqueException {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Set<Subject> search(String searchValue) {
    // TODO Auto-generated method stub
    // make call to 1 different source and fetch the results and return them
    return null;
  }

  @Override
  public void init() throws SourceUnavailableException {
    // TODO Auto-generated method stub
  }
  
}
