/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;

/**
 *
 * @author Kubus
 */
@Entity
@Table(name = "comment")
public class comment implements Serializable, Comparable<comment> {
    
    @Override
    public int compareTo(comment o) {
      return getCommentDate().compareTo(o.getCommentDate());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(Date commentDate) {
        this.commentDate = commentDate;
    }

    public workOrder getWorkOrder() {
        return workOrder;
    }

    public void setWorkOrder(workOrder workOrder) {
        this.workOrder = workOrder;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type", length = 64, nullable = false)
    private String type;
    
    @Column(name = "description", length = 1024, nullable = false)
    private String description;
    
    @Column(name = "comment_date", nullable = false)
    @Temporal(TIMESTAMP)
    private Date commentDate;
    
    @ManyToOne
    @JoinColumn(name="wo_id", nullable = false)
    private workOrder workOrder;
}

