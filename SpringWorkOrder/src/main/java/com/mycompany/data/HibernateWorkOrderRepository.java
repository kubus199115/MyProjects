/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;

import com.mycompany.dto.User;
import com.mycompany.dto.WorkOrder;
import com.mycompany.entity.authority;
import com.mycompany.entity.comment;
import com.mycompany.entity.productionArea;
import com.mycompany.entity.users;
import com.mycompany.entity.workOrder;
import com.mycompany.web.NotRequiredToJoinException;
import com.mycompany.web.NotUniqueWoNumberException;
import com.mycompany.web.NotYourWorkOrderException;
import com.mycompany.web.ProductionAreaNotFoundException;
import com.mycompany.web.UserNotFoundException;
import com.mycompany.web.WorkOrderNotFoundException;
import com.mycompany.web.WorkOrderStatusException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kubus
 */
@Repository
@Transactional
public class HibernateWorkOrderRepository implements WorkOrderRepository{

    private SessionFactory sessionFactory;
    private UserRepository userRepository;
    private ProductionAreaRepository productionAreaRepository;
        
    @Autowired
    public HibernateWorkOrderRepository(SessionFactory sessionFactory, UserRepository userRepository,
            ProductionAreaRepository productionAreaRepository) {
        this.sessionFactory = sessionFactory;   // inject SessionFactory
        this.productionAreaRepository = productionAreaRepository;
        this.userRepository = userRepository;
    }
    
    // retrieve current session from SessionFactory
    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public void addSingleWorkOrder(WorkOrder workOrder, String username, String areaName) {
        workOrder wor = findWorkOrderByWoNumber(workOrder.getWoNumber());
        if(wor != null)
            throw new NotUniqueWoNumberException();
                
        workOrder wo = new workOrder();
        long time = 0;
        Date planStart = new Date();
        long date = workOrder.getPlanningStart().getTime();
        if(workOrder.getPlanningStartTime() != null)
            time = workOrder.getPlanningStartTime().getTime();
        planStart.setTime(date + time);
        
        Date planStop = new Date();
        date = workOrder.getPlanningStop().getTime();
        if(workOrder.getPlanningStopTime() != null)
            time = workOrder.getPlanningStopTime().getTime();
        planStop.setTime(date + time);
        
        wo.setDescription(workOrder.getDescription());
        wo.setPlanningStart(planStart);
        wo.setPlanningStop(planStop);
        wo.setWoNumber(workOrder.getWoNumber());
        wo.setWoType(workOrder.getWoType());
        wo.setWoStatus("NOT_STARTED");  // deafault not strated status
        
        comment comm = new comment();
        comm.setCommentDate(new Date());
        comm.setDescription("Work Order Added");
        comm.setType("ADD_WO");
        
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        productionArea pa = productionAreaRepository.findByName(areaName);
        if(pa == null) throw new ProductionAreaNotFoundException();
        
        wo.setArea(pa);
        wo.getUsers().add(user);
        comm.setWorkOrder(wo);
        wo.getComments().add(comm);
        user.getWorkOrders().add(wo);
        pa.getWorkOrders().add(wo);
        
        currentSession().save(wo);
        currentSession().save(comm);
        currentSession().update(user);
        currentSession().update(pa);
        
    }

    @Override
    public workOrder findWorkOrderByWoNumber(String woNumber) {
        workOrder wo = new workOrder();
        
        try {
            wo = (workOrder) currentSession()
                    .createCriteria(workOrder.class)
                    .add(Restrictions.eq("woNumber", woNumber))
                    .list().get(0);
        }
        catch(IndexOutOfBoundsException ex) {
            return null;
        }
        
        
        return wo;
    }

    @Override
    public void joinToWorkOrder(String username, String woNumber) {
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        //sprawdzamy uprawnienia i jeśli trzeba to sprawdzamy czy może on wykonać daną akcje
        for(authority a : user.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
                throw new NotRequiredToJoinException();
            }
            
        }
        
        workOrder wo = findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        user.getWorkOrders().add(wo);
        wo.getUsers().add(user);
        
        currentSession().update(wo);
        currentSession().update(user);
    }

    @Override
    public void removeUsersFromWorkOrder(List<String> usersList, String woNumber, String username) {
        
        // jak nie zaznaczono żadnych mechaników to nic nie rób
        if(usersList == null) return;
        
        workOrder wo = findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        users us = userRepository.findByUsername(username);
        if(us == null) throw new UserNotFoundException();
        
        //sprawdzamy uprawnienia i jeśli trzeba to sprawdzamy czy może on wykonać daną akcje
        for(authority a : us.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
            }
            else {
                if(!us.getWorkOrders().contains(wo)) throw new NotYourWorkOrderException();
            }
        }
        
        
        for(String u : usersList) {
            users user = userRepository.findByUsername(u);
            if(user == null) throw new UserNotFoundException();
            
            
            for (Iterator<workOrder> iter = user.getWorkOrders().listIterator(); iter.hasNext(); ) {
                workOrder w = iter.next();
                if(w.equals(wo)){ iter.remove(); currentSession().update(user); }
            }
            
            for (Iterator<users> iter = wo.getUsers().listIterator(); iter.hasNext(); ) {
                users usr = iter.next();
                if(usr.equals(user)) { iter.remove(); currentSession().update(wo); }
            }
        }
        
  
    }

    @Override
    public void addUsersToWorkOrder(List<String> usersList, String woNumber, String username) {
        workOrder wo = findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        //sprawdzamy uprawnienia i jeśli trzeba to sprawdzamy czy może on wykonać daną akcje
        for(authority a : user.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
            }
            else {
                if(!user.getWorkOrders().contains(wo)) throw new NotYourWorkOrderException();
            }
        }
        
        for(String u : usersList) {
            users usr = userRepository.findByUsername(u);
            if(usr == null) throw new UserNotFoundException();
            
            wo.getUsers().add(usr);
            usr.getWorkOrders().add(wo);
            currentSession().update(usr);
            currentSession().update(wo);
        }
    }

    @Override
    public void startWorkOrder(String woNumber, String username) {
        workOrder wo = findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        //sprawdzamy uprawnienia i jeśli trzeba to sprawdzamy czy może on wykonać daną akcje
        for(authority a : user.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
            }
            else {
                if(!user.getWorkOrders().contains(wo)) throw new NotYourWorkOrderException();
            }
        }
        
        if(!wo.getWoStatus().equals("NOT_STARTED")) throw new WorkOrderStatusException();
        
        comment comm = new comment();
        
        wo.setWoStatus("STARTED");
        wo.setStart(new Date());
        comm.setCommentDate(new Date());
        comm.setDescription("Work Order started");
        comm.setType("START_WO");
        wo.getComments().add(comm);
        comm.setWorkOrder(wo);
        
        currentSession().update(wo);
        currentSession().save(comm);
        
    }

    @Override
    public void resumeWorkOrder(String woNumber, String username) {
        workOrder wo = findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        //sprawdzamy uprawnienia i jeśli trzeba to sprawdzamy czy może on wykonać daną akcje
        for(authority a : user.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
            }
            else {
                if(!user.getWorkOrders().contains(wo)) throw new NotYourWorkOrderException();
            }
        }
        
        if(!wo.getWoStatus().equals("PAUSED")) throw new WorkOrderStatusException();
        
        comment comm = new comment();
        
        wo.setWoStatus("STARTED");
        wo.setStart(new Date());
        comm.setCommentDate(new Date());
        comm.setDescription("Work Order resumed");
        comm.setType("RESUME_WO");
        wo.getComments().add(comm);
        comm.setWorkOrder(wo);
        
        currentSession().update(wo);
        currentSession().save(comm);
    }

    @Override
    public void stopWorkOrder(String woNumber, String username) {
        workOrder wo = findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        //sprawdzamy uprawnienia i jeśli trzeba to sprawdzamy czy może on wykonać daną akcje
        for(authority a : user.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
            }
            else {
                if(!user.getWorkOrders().contains(wo)) throw new NotYourWorkOrderException();
            }
        }
        
        if(wo.getWoStatus().equals("NOT_STARTED") || wo.getWoStatus().equals("FINALIZED") ||
                wo.getWoStatus().equals("PAUSED")) throw new WorkOrderStatusException();
        
        comment comm = new comment();
        
        wo.setWoStatus("FINALIZED");
        wo.setStop(new Date());
        comm.setCommentDate(new Date());
        comm.setDescription("Work Order stopped");
        comm.setType("STOP_WO");
        wo.getComments().add(comm);
        comm.setWorkOrder(wo);
        
        currentSession().update(wo);
        currentSession().save(comm);
    }

    @Override
    public void editWorkOrder(WorkOrder workOrder, String username, String areaName, String woNumber) {
        workOrder wo = findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        //sprawdzamy uprawnienia i jeśli trzeba to sprawdzamy czy może on wykonać daną akcje
        for(authority a : user.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
            }
            else {
                if(!user.getWorkOrders().contains(wo)) throw new NotYourWorkOrderException();
            }
        }
        
        // usuwamy starą przestrzeń produkcyjną
        productionArea pa = productionAreaRepository.findByName(wo.getArea().getAreaName());
        if(pa == null) throw new ProductionAreaNotFoundException();
        
        for (Iterator<workOrder> iter = pa.getWorkOrders().listIterator(); iter.hasNext(); ) {
            workOrder w = iter.next();
            if(w.equals(wo)){ iter.remove(); currentSession().update(pa);}
        }
        
        // ustawiamy nowe daty
        
        DateTime dt = new DateTime(workOrder.getPlanningStart());
        
        // pobieramy godzine i minute
        long dhm = dt.getHourOfDay() + dt.getMinuteOfHour();
        // odejmujemy godziny i minuty
        long fdt = workOrder.getPlanningStart().getTime() - dhm;
        // pobieramy nowy czas
        long nhm = workOrder.getPlanningStartTime().getTime();
        Date planStart = new Date();
        planStart.setTime(fdt + nhm + 3600000);     // niestety nie wiem czemu trzeba dodać godzine :(
        
        dt = new DateTime(workOrder.getPlanningStop());
        // pobieramy godzine i minute
        dhm = dt.getHourOfDay() + dt.getMinuteOfHour();
        // odejmujemy godziny i minuty
        fdt = workOrder.getPlanningStop().getTime() - dhm;
        // pobieramy nowy czas
        nhm = workOrder.getPlanningStopTime().getTime();
        Date planStop = new Date();
        planStop.setTime(fdt + nhm + 3600000);      // niestety nie wiem czemu trzeba dodać godzine :(
        
        
        wo.setWoNumber(workOrder.getWoNumber());
        if(workOrder.getWoType() != null)
            wo.setWoType(workOrder.getWoType());
        wo.setPlanningStart(planStart);
        wo.setPlanningStop(planStop);
        wo.setDescription(workOrder.getDescription());
        
        productionArea newPa = productionAreaRepository.findByName(areaName);
        if(newPa == null) throw new ProductionAreaNotFoundException();
        
        wo.setArea(newPa);
        newPa.getWorkOrders().add(wo);
        
        currentSession().update(wo);
        currentSession().update(newPa);
        
    }

    @Override
    public List<workOrder> findAll() {
        List<workOrder> woList = currentSession().createCriteria(workOrder.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)        // usuwamy duplikaty
                .list();
        
        if(woList.isEmpty()) return null;
        
        return woList;
    }
    
}
