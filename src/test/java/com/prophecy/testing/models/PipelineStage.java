package com.prophecy.testing.models;

/**
 * Model class representing a pipeline stage
 */
public class PipelineStage {
    private String name;
    private String type;
    private int expectedRecords;
    private String status;
    private int actualRecords;
    
    public PipelineStage() {
    }
    
    public PipelineStage(String name, String type, int expectedRecords) {
        this.name = name;
        this.type = type;
        this.expectedRecords = expectedRecords;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public int getExpectedRecords() {
        return expectedRecords;
    }
    
    public void setExpectedRecords(int expectedRecords) {
        this.expectedRecords = expectedRecords;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public int getActualRecords() {
        return actualRecords;
    }
    
    public void setActualRecords(int actualRecords) {
        this.actualRecords = actualRecords;
    }
    
    @Override
    public String toString() {
        return "PipelineStage{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", expectedRecords=" + expectedRecords +
                ", status='" + status + '\'' +
                ", actualRecords=" + actualRecords +
                '}';
    }
}