package edu.iu.uis.eden;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import edu.iu.uis.eden.services.ServiceErrorConstants;


/**
 * <p>Title: DocElementError </p>
 * <p>Description: A simple object holding any error(s) generated by
 * an IDocElement and it's children IDocElements.  See IDocElement
 * documentation for further explanation.</p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: Indiana University</p>
 * @author Ryan Kirkendall
 * @version 1.0
 */
public class WorkflowServiceErrorImpl implements Serializable, WorkflowServiceError {

  static final long serialVersionUID = 6900090941686297017L;
  private Collection children;
  private String type;
  private String message;
  private String arg1;
  private String arg2;

  private WorkflowServiceErrorImpl() {
  }

  public WorkflowServiceErrorImpl(String message, String type) {
    children = new ArrayList();
    this.message = message;
    this.type = type;
  }

  public WorkflowServiceErrorImpl(String message, String type, String arg1) {
      children = new ArrayList();
      this.message = message;
      this.type = type;
      this.arg1 = arg1;
  }
  
  public WorkflowServiceErrorImpl(String message, String type, String arg1, String arg2) {
      children = new ArrayList();
      this.message = message;
      this.type = type;
      this.arg1 = arg1;
      this.arg2 = arg2;
  }
  
  public Collection getChildren() {
    return this.children;
  }

  public String getMessage() {
    return this.message;
  }

  public String getKey() {
    return this.type;
  }

  public String getArg1() {
    return arg1;
  }

  public String getArg2() {
    return arg2;
  }
  
  public void addChild(WorkflowServiceError busError) {
    if (busError != null) {
      children.add(busError);
    }
  }

  public void addChildren(Collection children) {
    this.children.addAll(children);
  }

  public Collection getFlatChildrenList() {
    return buildFlatChildrenList(this, null);
  }

  private static Collection buildFlatChildrenList(WorkflowServiceError error, List flatList) {
    if (flatList == null) {
      flatList = new ArrayList();
    }

    if (error.getKey() != ServiceErrorConstants.CHILDREN_IN_ERROR) {
      flatList.add(error);
    }

    Iterator iter = error.getChildren().iterator();

    while (iter.hasNext()) {
      WorkflowServiceError childError = (WorkflowServiceError) iter.next();
      buildFlatChildrenList(childError, flatList);
    }

    return flatList;
  }

  public String toString() {
    String s = "[WorkflowServiceErrorImpl: type=" + type + ", message=" + message + ", arg1=" + arg1 + ", arg2=" + arg2 + ", children=";
    if (children == null) {
        s += "null";
    } else {
        s += children;
    }
    s += "]";
    return s;
  }
}