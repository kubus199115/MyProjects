/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import static javax.persistence.TemporalType.DATE;
import static javax.persistence.TemporalType.TIMESTAMP;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 *
 * @author Kubus
 */
@Entity
@Table(name = "workorder")
public class workOrder implements Serializable, Comparable<workOrder> {

    public String getWoType() {
        return woType;
    }

    public void setWoType(String woType) {
        this.woType = woType;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final workOrder other = (workOrder) obj;
        if (!Objects.equals(this.woNumber, other.woNumber)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.woNumber);
        return hash;
    }
    
    @Override
    public int compareTo(workOrder o) {
      return getPlanningStop().compareTo(o.getPlanningStop());
    }
    
    public static Comparator<workOrder> woPlanningStartComparatorASC
            = new Comparator<workOrder>() {
                
                @Override
                public int compare(workOrder wo1, workOrder wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStart();
                    Date wo2Date = wo2.getPlanningStart();
                    
                    return wo1Date.compareTo(wo2Date);
                }
            };
    
    public static Comparator<workOrder> woPlanningStartComparatorDESC
            = new Comparator<workOrder>() {
                
                @Override
                public int compare(workOrder wo1, workOrder wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStart();
                    Date wo2Date = wo2.getPlanningStart();
                    
                    return wo2Date.compareTo(wo1Date);
                }
            };
    
    public static Comparator<workOrder> woPlanningStopComparatorASC
            = new Comparator<workOrder>() {
                
                @Override
                public int compare(workOrder wo1, workOrder wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStop();
                    Date wo2Date = wo2.getPlanningStop();
                    
                    return wo1Date.compareTo(wo2Date);
                }
            };
    
    public static Comparator<workOrder> woPlanningStopComparatorDESC
            = new Comparator<workOrder>() {
                
                @Override
                public int compare(workOrder wo1, workOrder wo2) {
                    //asc
                    Date wo1Date = wo1.getPlanningStop();
                    Date wo2Date = wo2.getPlanningStop();
                    
                    return wo2Date.compareTo(wo1Date);
                }
            };

    public List<users> getUsers() {
        return users;
    }

    public void setUsers(List<users> users) {
        this.users = users;
    }

    public List<comment> getComments() {
        return comments;
    }

    public void setComments(List<comment> comments) {
        this.comments = comments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getWoNumber() {
        return woNumber;
    }

    public void setWoNumber(String woNumber) {
        this.woNumber = woNumber;
    }

    public String getWoStatus() {
        return woStatus;
    }

    public void setWoStatus(String woStatus) {
        this.woStatus = woStatus;
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

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getStop() {
        return stop;
    }

    public void setStop(Date stop) {
        this.stop = stop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public productionArea getArea() {
        return area;
    }

    public void setArea(productionArea area) {
        this.area = area;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "wo_number", unique = true, length = 10, nullable = false)
    private String woNumber;
    
    @Column(name = "wo_type", nullable = true)
    private String woType;
    
    @Column(name = "wo_status", nullable = false)
    private String woStatus;
    
    @Column(name = "planning_start", nullable = false)
    @Temporal(TIMESTAMP)
    private Date planningStart;
    
    @Column(name = "planning_stop", nullable = false)
    @Temporal(TIMESTAMP)
    private Date planningStop;
    
    @Column(name = "wo_start", nullable = true)
    @Temporal(TIMESTAMP)
    private Date start;
    
    @Column(name = "wo_stop", nullable = true)
    @Temporal(TIMESTAMP)
    private Date stop;
    
    @Column(name = "description", length = 1024, nullable = false)
    private String description;
    
    @ManyToOne
    @JoinColumn(name="area_id", nullable = false)
    private productionArea area;
    
    @OneToMany(fetch=FetchType.EAGER, mappedBy="workOrder")
    private List<comment> comments = new ArrayList<comment>();
    
    @ManyToMany(fetch=FetchType.EAGER, cascade = { 
        CascadeType.PERSIST, 
        CascadeType.MERGE
    })
    @JoinTable(name = "workorder_user",
        joinColumns = @JoinColumn(name = "wo_id"),
        inverseJoinColumns = @JoinColumn(name = "user_name")
    )
    @Fetch(value = FetchMode.SUBSELECT)
    private List<users> users = new ArrayList<users>();
}
