package com.erezshevach.recipebookmaster.presentation.model.response;

public class OperationStatusResponseModel {
    private String entityClassName;
    private String entityIdentifier;
    private OperationName operationName;
    private OperationStatus operationStatus;
    private String statusMsg;


    //-------------------getters & setters ----------------------------


    public String getEntityClass() { return entityClassName; }

    public void setEntityClass(String entityClass) { this.entityClassName = entityClass; }

    public String getEntityIdentifier() {
        return entityIdentifier;
    }

    public void setEntityIdentifier(String entityIdentifier) {
        this.entityIdentifier = entityIdentifier;
    }

    public OperationName getOperationName() {
        return operationName;
    }

    public void setOperationName(OperationName operationName) {
        this.operationName = operationName;
    }

    public OperationStatus getOperationStatus() {
        return operationStatus;
    }

    public void setOperationStatus(OperationStatus status) {
        this.operationStatus = status;
    }

    public String getStatusMsg() { return statusMsg; }

    public void setStatusMsg(String statusMsg) { this.statusMsg = statusMsg; }
}
