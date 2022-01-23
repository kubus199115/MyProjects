/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.web;

import com.mycompany.data.UserRepository;
import com.mycompany.data.WorkOrderRepository;
import com.mycompany.entity.authority;
import com.mycompany.entity.comment;
import com.mycompany.entity.users;
import com.mycompany.entity.workOrder;
import com.sun.javafx.scene.control.skin.VirtualFlow;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author kubus
 */
@Controller
public class IndexController {
    
    private UserRepository userRepository;
    private  WorkOrderRepository workOrderRepository;
    
    public final int woCount =  4;
    
    @Autowired
    public IndexController(UserRepository userRepository, WorkOrderRepository workOrderRepository) {
        this.userRepository = userRepository;
        this.workOrderRepository = workOrderRepository;
    }
    
    @RequestMapping(value="/", method=GET)
    public String defaultIndex(Model model, Authentication auth) {
        List<workOrder> woList = new ArrayList<workOrder>();
        List<workOrder> mechanList = new ArrayList<workOrder>();
        users user = userRepository.findByUsername(auth.getName());
        //sprawdzamy uprawnienia i przypisujemy odpowiednią listę zleceń
        
                woList = workOrderRepository.findAll();
                if(woList == null)
                    woList = new ArrayList<workOrder>();
                
                mechanList = user.getWorkOrders();
                if(mechanList == null)
                    mechanList = new ArrayList<workOrder>();
            
               
        Collections.sort(woList);
        Collections.sort(mechanList);
        int pageCount = ((mechanList.size()-1) / woCount) + 1;
        
        List<comment> commList = new ArrayList<comment>();
        List<workOrder> delayedWO = new ArrayList<workOrder>();
                
        for(workOrder wo : woList) {
            for(comment comm : wo.getComments()) {
                commList.add(comm);
            }
            
            // dodajemy opóźnione zlecenia
            if(wo.getWoStatus().equals("STARTED") || wo.getWoStatus().equals("PAUSED")) {
                DateTime d1 = new DateTime(wo.getPlanningStop());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) delayedWO.add(wo);
                else if(hours < 0) delayedWO.add(wo);
                else if(days < 0) delayedWO.add(wo);
            }
            
            if(wo.getWoStatus().equals("NOT_STARTED")) {
                DateTime d1 = new DateTime(wo.getPlanningStart());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) delayedWO.add(wo);
                else if(hours < 0) delayedWO.add(wo);
                else if(days < 0) delayedWO.add(wo);
            }
        }
        
        Collections.sort(commList);
        Collections.reverse(commList);
        
        List<comment> comments = new ArrayList<comment>();
        for(int i=0; i<commList.size(); i++) {
            if(i<woCount)
                comments.add(commList.get(i));
            else
                break;
        }
        
        List<workOrder> workOrders = new ArrayList<workOrder>();
        for(int i=0; i<woCount; i++) {
            if(i < mechanList.size())
                workOrders.add(mechanList.get(i));
            else
                break;
        }
        
        
        
        model.addAttribute("woList", workOrders);
        model.addAttribute("commList", comments);
        model.addAttribute("delayedWO", delayedWO);
        model.addAttribute("count", pageCount);
        
        return "index";
    }
    
    @RequestMapping(value="/{page}", method=GET)
    public String index(@PathVariable int page, Model model, Authentication auth) {
        
        List<workOrder> woList = new ArrayList<workOrder>();
        users user = userRepository.findByUsername(auth.getName());
        //sprawdzamy uprawnienia i przypisujemy odpowiednią listę zleceń
        for(authority a : user.getAuthorities()) {
            if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ 
                woList = workOrderRepository.findAll();
                if(woList == null)
                    woList = new ArrayList<workOrder>();
            }
            else {
                woList = user.getWorkOrders();
                if(woList == null)
                    woList = new ArrayList<workOrder>();
            }
        }
        Collections.sort(woList);
        int pageCount = ((woList.size()-1) / woCount) + 1;
        
        List<comment> commList = new ArrayList<comment>();
        List<workOrder> delayedWO = new ArrayList<workOrder>();
                
        for(workOrder wo : woList) {
            for(comment comm : wo.getComments()) {
                commList.add(comm);
            }
            
            // dodajemy opóźnione zlecenia
            if(wo.getWoStatus().equals("STARTED") || wo.getWoStatus().equals("PAUSED")) {
                DateTime d1 = new DateTime(wo.getPlanningStop());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) delayedWO.add(wo);
                else if(hours < 0) delayedWO.add(wo);
                else if(days < 0) delayedWO.add(wo);
            }
            
            if(wo.getWoStatus().equals("NOT_STARTED")) {
                DateTime d1 = new DateTime(wo.getPlanningStart());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) delayedWO.add(wo);
                else if(hours < 0) delayedWO.add(wo);
                else if(days < 0) delayedWO.add(wo);
            }
        }        
        
        
        Collections.sort(commList);
        Collections.reverse(commList);
        
        List<comment> comments = new ArrayList<comment>();
        for(int i=0; i<commList.size(); i++) {
            if(i<woCount)
                comments.add(commList.get(i));
            else
                break;
        }
        
        List<workOrder> workOrders = new ArrayList<workOrder>();
        for(int i=page*woCount; i<(page*woCount)+woCount; i++) {
            if(i < woList.size())
                workOrders.add(woList.get(i));
            else
                break;
        }
        
        model.addAttribute("woList", workOrders);
        model.addAttribute("commList", comments);
        model.addAttribute("delayedWO", delayedWO);
        model.addAttribute("count", pageCount);
        
        return "index";
    }
}
