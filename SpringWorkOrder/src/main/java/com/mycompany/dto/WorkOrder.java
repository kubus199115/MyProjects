/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dto;

import java.util.Date;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Kubus
 */
public class WorkOrder {

    public Date getPlanningStartTime() {
        return planningStartTime;
    }

    public void setPlanningStartTime(Date planningStartTime) {
        this.planningStartTime = planningStartTime;
    }

    public Date getPlanningStopTime() {
        return planningStopTime;
    }

    public void setPlanningStopTime(Date planningStopTime) {
        this.planningStopTime = planningStopTime;
    }

    public String getWoType() {
        return woType;
    }

    public void setWoType(String woType) {
        this.woType = woType;
    }

    public String getWoNumber() {
        return woNumber;
    }

    public void setWoNumber(String woNumber) {
        this.woNumber = woNumber;
    }

    public Date getPlanningStart() {
        return planningStart;
    }

    public void setPlanningStart(Date planningStart) {
        this.planningStart = planningStart;
    }

    public Date getPlanningStop() {
        return planningStop;
    }

    public void setPlanningStop(Date planningStop) {
        this.planningStop = planningStop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }
    
    @NotNull
    @Size(min=10, max=10, message="{woNumber.validation}")
    private String woNumber;
    @Size(max=10, message="{woType.validation}")
    private String woType;
    @NotNull(message="{planningStart.validation}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planningStart;
    @NotNull(message="{planningStop.validation}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date planningStop;
    @NotNull(message="{planningStartTime.validation}")
    @DateTimeFormat(pattern = "HH:mm")
    private Date planningStartTime;
    @NotNull(message="{planningStopTime.validation}")
    @DateTimeFormat(pattern = "HH:mm")
    private Date planningStopTime;
    @NotNull
    @Size(min=1, max=1024, message="{woDescription.validation}")
    private String description;
    @NotNull
    private String areaName;
}
