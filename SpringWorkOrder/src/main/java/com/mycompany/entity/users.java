/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Kubus
 */
@Entity
@Table(name = "users")
public class users implements Serializable {

    public List<workOrder> getWorkOrders() {
        return workOrders;
    }

    public void setWorkOrders(List<workOrder> workOrders) {
        this.workOrders = workOrders;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<authority> authorities) {
        this.authorities = authorities;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "username", nullable = false)
    private String username;
    
    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="username")
    private List<authority> authorities = new ArrayList<authority>();
    
    @ManyToMany(fetch=FetchType.EAGER, mappedBy = "users")
    @Fetch(value = FetchMode.SUBSELECT)
    private List<workOrder> workOrders = new ArrayList<workOrder>();
    
}
