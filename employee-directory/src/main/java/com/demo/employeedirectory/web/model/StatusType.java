package com.demo.employeedirectory.web.model;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusType {
    ACTIVE(1),
    DISABLED(2);

    private final Integer status;

    StatusType(Integer status) {
        this.status = status;
    }

    public Integer getStatus() {
        return this.status;
    }

    @JsonCreator
    public static StatusType getStatusType(Integer value) {
        for(StatusType status : StatusType.values()) {
            if(status.status == value) {
                return status;
            }
        }
        return null;
    }

    public static StatusType valueOf(Integer status){
        return StatusType.getStatusType(status);
    }

    @Override
    public String toString() {
        return String.valueOf(this.status);
    }
}
