/*
 * Copyright 2005-2006 The Kuali Foundation.
 *
 *
 * Licensed under the Educational Community License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/ecl1.php
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.iu.uis.eden.documentoperation.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.Factory;
import org.apache.commons.collections.ListUtils;
import org.apache.struts.action.ActionForm;

import edu.iu.uis.eden.EdenConstants;
import edu.iu.uis.eden.engine.node.Branch;
import edu.iu.uis.eden.engine.node.RouteNodeInstance;
import edu.iu.uis.eden.routeheader.DocumentRouteHeaderValue;

/**
 * struts form bean for {@link DocumentOperationAction}.
 *
 * @author shenl
 */
public class DocumentOperationForm extends ActionForm{

	private static final long serialVersionUID = 2994179393392218743L;
	private DocumentRouteHeaderValue routeHeader;
    private String methodToCall = "";
    private String routeHeaderId;

    private List actionRequestOps = new ArrayList();
    private List actionTakenOps = new ArrayList();
    private List actionItemOps = new ArrayList();


    private String routeHeaderOp;

    private String statusModDate;
    private String createDate;
    private String approvedDate;
    private String finalizedDate;
    private String routeStatusDate;
    private String routeLevelDate;
    private String lookupableImplServiceName;
    private String lookupType;
    private Map docStatuses = EdenConstants.DOCUMENT_STATUSES;
    private Map actionRequestCds = EdenConstants.ACTION_REQUEST_CD;
    private Map actionRequestStatuses = EdenConstants.ACTION_REQUEST_STATUS;
    private Map actionRequestRecipientTypes = EdenConstants.ACTION_REQUEST_RECIPIENT_TYPE;
    private Map actionTakenCds = EdenConstants.ACTION_TAKEN_CD;
    private List routeModules;
    private String routeModuleName;

    private String lookupInvocationModule;
    private String lookupInvocationField;
    private String lookupInvocationIndex;

    //variabes for RouteNodeInstances and branches
    private List routeNodeInstances=ListUtils.lazyList(new ArrayList(),
            new Factory() {
        		public Object create() {
        			return new RouteNodeInstance();
        		}
       		});

    private List routeNodeInstanceOps=new ArrayList();
    private List branches=ListUtils.lazyList(new ArrayList(),
            new Factory() {
				public Object create() {
					return new Branch();
				}
			});
    private List branchOps=new ArrayList();
    private List nodeStateDeleteOps=new ArrayList();
    private String nodeStatesDelete;
    private String branchStatesDelete;
    private String initialNodeInstances;

    private String annotation;

    private String blanketApproveUser;
    private String blanketApproveActionTakenId;
    private String blanketApproveNodes;
    private String actionInvocationUser;
    private String actionInvocationActionItemId;
    private String actionInvocationActionCode;

    public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getInitialNodeInstances(){
    	return initialNodeInstances;
    }

    public void setInitialNodeInstances(String initialNodeInstances){
    	this.initialNodeInstances=initialNodeInstances;
    }

    public String getNodeStatesDelete(){
    	return nodeStatesDelete;
    }

    public void setNodeStatesDelete(String nodeStatesDelete){
    	this.nodeStatesDelete=nodeStatesDelete;
    }

    public String getBranchStatesDelete(){
    	return branchStatesDelete;
    }

    public void setBranchStatesDelete(String branchStatesDelete){
    	this.branchStatesDelete=branchStatesDelete;
    }

    public DocumentOperationForm(){
        routeHeader = new DocumentRouteHeaderValue();
    }

    public String getMethodToCall() {
        return methodToCall;
    }
    public void setMethodToCall(String methodToCall) {
        this.methodToCall = methodToCall;
    }
    public DocumentRouteHeaderValue getRouteHeader() {
        return routeHeader;
    }
    public void setRouteHeader(DocumentRouteHeaderValue routeHeader) {
        this.routeHeader = routeHeader;
    }

    public DocOperationIndexedParameter getActionRequestOp(int index) {
        while (actionRequestOps.size() <= index) {
            actionRequestOps.add(new DocOperationIndexedParameter(new Integer(index), EdenConstants.NOOP));
        }
        return (DocOperationIndexedParameter) getActionRequestOps().get(index);
    }

    public DocOperationIndexedParameter getActionTakenOp(int index) {
        while (actionTakenOps.size() <= index) {
            actionTakenOps.add(new DocOperationIndexedParameter(new Integer(index), EdenConstants.NOOP));
        }
        return (DocOperationIndexedParameter) getActionTakenOps().get(index);
    }

    public DocOperationIndexedParameter getRouteNodeInstanceOp(int index) {
        while (routeNodeInstanceOps.size() <= index) {
        	routeNodeInstanceOps.add(new DocOperationIndexedParameter(new Integer(index), EdenConstants.NOOP));
        }
        return (DocOperationIndexedParameter) getRouteNodeInstanceOps().get(index);
    }

    public DocOperationIndexedParameter getBranchOp(int index) {
        while (branchOps.size() <= index) {
        	branchOps.add(new DocOperationIndexedParameter(new Integer(index), EdenConstants.NOOP));
        }
        return (DocOperationIndexedParameter) getBranchOps().get(index);
    }

    public DocOperationIndexedParameter getActionItemOp(int index) {
        while (actionItemOps.size() <= index) {
            actionItemOps.add(new DocOperationIndexedParameter(new Integer(index), EdenConstants.NOOP));
        }
        return (DocOperationIndexedParameter) getActionItemOps().get(index);
    }

    public DocOperationIndexedParameter getNodeStateDeleteOp(int index){
    	while(nodeStateDeleteOps.size()<=index){
    		nodeStateDeleteOps.add(new DocOperationIndexedParameter(new Integer(index),""));
    	}
    	return(DocOperationIndexedParameter) getNodeStateDeleteOps().get(index);
    }

    public List getActionItemOps() {
        return actionItemOps;
    }
    public void setActionItemOps(List actionItemOps) {
        this.actionItemOps = actionItemOps;
    }
    public List getActionRequestOps() {
        return actionRequestOps;
    }
    public void setActionRequestOps(List actionRequestOps) {
        this.actionRequestOps = actionRequestOps;
    }
    public List getActionTakenOps() {
        return actionTakenOps;
    }
    public List getRouteNodeInstanceOps() {
        return routeNodeInstanceOps;
    }

    public List getBranchOps(){
    	return branchOps;
    }

    public List getNodeStateDeleteOps(){
    	return nodeStateDeleteOps;
    }

    public void setActionTakenOps(List actionTakenOps) {
        this.actionTakenOps = actionTakenOps;
    }

    public void setRouteNodeInstanceOps(List routeNodeInstanceOps) {
        this.routeNodeInstanceOps = routeNodeInstanceOps;
    }

    public void setBranchOps(List branchOps){
    	this.branchOps=branchOps;
    }

    public void setNodeStateDeleteOps(List nodeStateDeleteOps){
    	this.nodeStateDeleteOps=nodeStateDeleteOps;
    }

    public String getRouteHeaderOp() {
        return routeHeaderOp;
    }
    public void setRouteHeaderOp(String routeHeaderOp) {
        this.routeHeaderOp = routeHeaderOp;
    }
    public String getApprovedDate() {
        return approvedDate;
    }
    public void setApprovedDate(String approvedDate) {
        this.approvedDate = approvedDate;
    }
    public String getCreateDate() {
        return createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public String getFinalizedDate() {
        return finalizedDate;
    }
    public void setFinalizedDate(String finalizedDate) {
        this.finalizedDate = finalizedDate;
    }
    public String getRouteLevelDate() {
        return routeLevelDate;
    }
    public void setRouteLevelDate(String routeLevelDate) {
        this.routeLevelDate = routeLevelDate;
    }
    public String getRouteStatusDate() {
        return routeStatusDate;
    }
    public void setRouteStatusDate(String routeStatusDate) {
        this.routeStatusDate = routeStatusDate;
    }
    public String getStatusModDate() {
        return statusModDate;
    }
    public void setStatusModDate(String statusModDate) {
        this.statusModDate = statusModDate;
    }


    public String getLookupableImplServiceName() {
        return lookupableImplServiceName;
    }
    public void setLookupableImplServiceName(String lookupableImplServiceName) {
        this.lookupableImplServiceName = lookupableImplServiceName;
    }
    public String getLookupType() {
        return lookupType;
    }
    public void setLookupType(String lookupType) {
        this.lookupType = lookupType;
    }

    public Map getDocStatuses() {
        return docStatuses;
    }

    public Map getActionRequestCds() {
        return actionRequestCds;
    }
    public Map getActionRequestRecipientTypes() {
        return actionRequestRecipientTypes;
    }
    public Map getActionRequestStatuses() {
        return actionRequestStatuses;
    }

    public String getLookupInvocationField() {
        return lookupInvocationField;
    }
    public void setLookupInvocationField(String lookupInvocationField) {
        this.lookupInvocationField = lookupInvocationField;
    }
    public String getLookupInvocationIndex() {
        return lookupInvocationIndex;
    }
    public void setLookupInvocationIndex(String lookupInvocationIndex) {
        this.lookupInvocationIndex = lookupInvocationIndex;
    }
    public String getLookupInvocationModule() {
        return lookupInvocationModule;
    }
    public void setLookupInvocationModule(String lookupInvocationModule) {
        this.lookupInvocationModule = lookupInvocationModule;
    }
    public Map getActionTakenCds() {
        return actionTakenCds;
    }

    public String getRouteHeaderId() {
        return routeHeaderId;
    }
    public void setRouteHeaderId(String routeHeaderId) {
        this.routeHeaderId = routeHeaderId;
    }

    public List getRouteModules() {
        return routeModules;
    }

    public void setRouteModules(List routeModules) {
        this.routeModules = routeModules;
    }

    public String getRouteModuleName() {
        return routeModuleName;
    }

    public void setRouteModuleName(String routeModuleName) {
        this.routeModuleName = routeModuleName;
    }

    /*
     * methods for route node instances
     */

    public List getRouteNodeInstances(){
    	return routeNodeInstances;
    }

    public void setRouteNodeInstances(List routeNodeInstances){
    	this.routeNodeInstances=routeNodeInstances;
    }

    public RouteNodeInstance getRouteNodeInstance(int index){
    	while (getRouteNodeInstances().size() <= index) {
                getRouteNodeInstances().add(new RouteNodeInstance());
            }
            return (RouteNodeInstance) getRouteNodeInstances().get(index);
    }

    public List getBranches(){
    	return branches;
    }

    public void setBranches(List branches){
    	this.branches=branches;
    }

    public Branch getBranche(int index){
    	while(getBranches().size()<=index){
    		getBranches().add(new Branch());
    	}
    	return (Branch) getBranches().get(index);
    }

    public void resetOps(){
    	routeNodeInstanceOps=new ArrayList();
    	branchOps=new ArrayList();
    	actionRequestOps = new ArrayList();
        actionTakenOps = new ArrayList();
        actionItemOps = new ArrayList();
    }

	public String getActionInvocationActionCode() {
		return actionInvocationActionCode;
	}

	public void setActionInvocationActionCode(String actionInvocationActionCode) {
		this.actionInvocationActionCode = actionInvocationActionCode;
	}

	public String getActionInvocationActionItemId() {
		return actionInvocationActionItemId;
	}

	public void setActionInvocationActionItemId(String actionInvocationActionItemId) {
		this.actionInvocationActionItemId = actionInvocationActionItemId;
	}

	public String getActionInvocationUser() {
		return actionInvocationUser;
	}

	public void setActionInvocationUser(String actionInvocationUser) {
		this.actionInvocationUser = actionInvocationUser;
	}

	public String getBlanketApproveActionTakenId() {
		return blanketApproveActionTakenId;
	}

	public void setBlanketApproveActionTakenId(String blanketApproveActionTakenId) {
		this.blanketApproveActionTakenId = blanketApproveActionTakenId;
	}

	public String getBlanketApproveNodes() {
		return blanketApproveNodes;
	}

	public void setBlanketApproveNodes(String blanketApproveNodes) {
		this.blanketApproveNodes = blanketApproveNodes;
	}

	public String getBlanketApproveUser() {
		return blanketApproveUser;
	}

	public void setBlanketApproveUser(String blanketApproveUser) {
		this.blanketApproveUser = blanketApproveUser;
	}


}
