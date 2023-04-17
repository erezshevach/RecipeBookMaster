package com.erezshevach.recipebookmaster.ui.model.response;

public class OperationStatusResponseModel {
    private String EntityName;
    private String OperationName;
    private String OperationStatus;


    //-------------------getters & setters ----------------------------

    public String getEntityName() {
        return EntityName;
    }

    public void setEntityName(String entityName) {
        EntityName = entityName;
    }

    public String getOperationName() {
        return OperationName;
    }

    public void setOperationName(String operationName) {
        OperationName = operationName;
    }

    public String getOperationStatus() {
        return OperationStatus;
    }

    public void setOperationStatus(String operationStatus) {
        OperationStatus = operationStatus;
    }
}
