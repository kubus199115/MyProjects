/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dto;

import java.io.Serializable;
import java.util.Date;
import javax.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Kubus
 */
public class WOPanelOption implements Serializable{

    public String getCriterium() {
        return criterium;
    }

    public void setCriterium(String criterium) {
        this.criterium = criterium;
    }

    public Date getBegin() {
        return begin;
    }

    public void setBegin(Date begin) {
        this.begin = begin;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public boolean isShowFinalized() {
        return showFinalized;
    }

    public void setShowFinalized(boolean showFinalized) {
        this.showFinalized = showFinalized;
    }
    @NotNull(message="{begin.validation}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date begin;
    @NotNull(message="{end.validation}")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date end;
    @NotNull(message="{criterium.validation}")
    private String criterium;
    
    private boolean showFinalized;
}
