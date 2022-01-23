/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.web;

import com.mycompany.data.CommentRepository;
import com.mycompany.data.ProductionAreaRepository;
import com.mycompany.data.UserRepository;
import com.mycompany.data.WorkOrderRepository;
import com.mycompany.dto.Comment;
import com.mycompany.dto.User;
import com.mycompany.dto.WOPanelOption;
import com.mycompany.dto.WorkOrder;
import com.mycompany.dto.checkBoxValues;
import com.mycompany.dto.woPanel;
import com.mycompany.entity.authority;
import com.mycompany.entity.comment;
import com.mycompany.entity.productionArea;
import com.mycompany.entity.users;
import com.mycompany.entity.workOrder;
import static com.mycompany.entity.workOrder.woPlanningStartComparatorASC;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Period;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sun.security.pkcs11.wrapper.Functions;

/**
 *
 * @author Kubus
 */
@Controller
public class WorkOrderController {
    
    private WorkOrderRepository workOrderRepository;
    private ProductionAreaRepository productionAreaRepository;
    private UserRepository userRepository;
    private CommentRepository commentRepository;
    
    public final int commCount =  4;
    
    @Autowired
    public WorkOrderController(WorkOrderRepository workOrderRepository, ProductionAreaRepository productionAreaRepository,
            UserRepository userRepository, CommentRepository commentRepository) {
        this.workOrderRepository = workOrderRepository;
        this.productionAreaRepository = productionAreaRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }
    
    @RequestMapping(value="/addWorkOrder", method=GET)
    public String workOrder(Model model) {
        List<productionArea> pal = productionAreaRepository.findAll();
        
        model.addAttribute("paList", pal);
        model.addAttribute(new WorkOrder());
        
        return "addWorkOrder";
    }
    
    @RequestMapping(value="/addWorkOrder", method=POST)
    public String addWorkOrder(
            @Valid WorkOrder workOrder, Errors errors, Model model, Authentication auth) {
        
        if(errors.hasErrors()) {
            List<productionArea> pal = productionAreaRepository.findAll();
            model.addAttribute("paList", pal);
            return "addWorkOrder";
        }
        
        try {
            workOrderRepository.addSingleWorkOrder(workOrder, auth.getName(), workOrder.getAreaName());
        }
        catch(UserNotFoundException | ProductionAreaNotFoundException ex) {
            
        }
        catch(NotUniqueWoNumberException ex) {
            List<productionArea> pal = productionAreaRepository.findAll();
            model.addAttribute("paList", pal);
            model.addAttribute("unique", "Ten numer nie jest dostepny");
            return "addWorkOrder";
        }
        
        
        return "redirect:/workOrder/" + workOrder.getWoNumber();
    }
    
    @RequestMapping(value="/workOrder/{woNumber}", method=GET) 
    public String defaultShowWorkOrder(
    @PathVariable String woNumber, Model model, Authentication auth) {
        
        workOrder wo = workOrderRepository.findWorkOrderByWoNumber(woNumber);
        if(wo == null) {
            model.addAttribute("error", "Nie znaleziono zlecenia o podanym numerze");
            return "searchWorkOrder";
        }
                
        int days = 0;
        int hours = 0;
        int minutes = 0;
        boolean possibilityToJoin = false;
        int pageCount = ((wo.getComments().size()-1) / commCount) + 1;
        
        Collections.sort(wo.getComments());
        Collections.reverse(wo.getComments());
        
        List<comment> comments = new ArrayList<comment>();
        for(int i=0; i<commCount; i++) {
            if(i<wo.getComments().size())
                comments.add(wo.getComments().get(i));
            else
                break;
        }
        
        List<users> mechanics = new ArrayList<users>();
        
        int i = 0;
        for(users u : wo.getUsers()) {
            mechanics.add(u);
            for(authority a : u.getAuthorities()) {
                if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ mechanics.remove(i); i--; break; }
            }
            i++;
        }
        
        // obliczamy ile czasu pozostało do planowanego końca zlecenia
        if(wo.getWoStatus().equals("STARTED") || wo.getWoStatus().equals("PAUSED")) {
            DateTime d1 = new DateTime(wo.getPlanningStop());
            DateTime d2 = new DateTime();
            Period per = new Period(d2, d1);
            days = per.getDays();
            hours = per.getHours();
            minutes = per.getMinutes();
        }
        
        users user = userRepository.findByUsername(auth.getName());
        // czy mechanik może dołączyć do zlecenia?
        if(!user.getWorkOrders().contains(wo)) possibilityToJoin = true;
        
        // czy użytkownik jest planistą albo inżynierem (wtedy nie musi dołączać do zlecenia w celu działań na nim)
        for(authority a : user.getAuthorities()) {
                if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ possibilityToJoin = false; break; }
            }
        
        model.addAttribute("possible", possibilityToJoin);
        model.addAttribute("mechanics", mechanics);
        model.addAttribute("day", days);  
        model.addAttribute("hour", hours);
        model.addAttribute("minute", minutes);  
        model.addAttribute("wo", wo);
        model.addAttribute("comments", comments);
        model.addAttribute("count", pageCount);
                
        model.addAttribute("cbv", new checkBoxValues());
        
        return "workOrderPage";
    }
    
    @RequestMapping(value="/workOrder/{woNumber}/{page}", method=GET) 
    public String showWorkOrder(
    @PathVariable String woNumber, @PathVariable int page, Model model, Authentication auth) {
        
        workOrder wo = workOrderRepository.findWorkOrderByWoNumber(woNumber);
        if(wo == null) {
            model.addAttribute("error", "Nie znaleziono zlecenia o podanym numerze");
            return "searchWorkOrder";
        }
                
        int days = 0;
        int hours = 0;
        int minutes = 0;
        boolean possibilityToJoin = false;
        int pageCount = ((wo.getComments().size()-1) / commCount) + 1;
        
        Collections.sort(wo.getComments());
        Collections.reverse(wo.getComments());
        
        List<comment> comments = new ArrayList<comment>();
        for(int i=page*commCount; i<(page*commCount)+commCount; i++) {
            if(i<wo.getComments().size())
                comments.add(wo.getComments().get(i));
            else
                break;
        }
        
        List<users> mechanics = new ArrayList<users>();
        
        int i = 0;
        for(users u : wo.getUsers()) {
            mechanics.add(u);
            for(authority a : u.getAuthorities()) {
                if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ mechanics.remove(i); i--; break; }
            }
            i++;
        }
        
        // obliczamy ile dni pozostało do planowanego końca zlecenia
        if(wo.getWoStatus().equals("STARTED") || wo.getWoStatus().equals("PAUSED")) {
            DateTime d1 = new DateTime(wo.getPlanningStop());
            DateTime d2 = new DateTime();
            Period per = new Period(d2, d1);
            days = per.getDays();
            hours = per.getHours();
            minutes = per.getMinutes();
        }
        
        users user = userRepository.findByUsername(auth.getName());
        // czy mechanik może dołączyć do zlecenia?
        if(!user.getWorkOrders().contains(wo)) possibilityToJoin = true;
        
        // czy użytkownik jest planistą albo inżynierem (wtedy nie musi dołączać do zlecenia w celu działań na nim)
        for(authority a : user.getAuthorities()) {
                if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ possibilityToJoin = false; break; }
            }
        
        model.addAttribute("possible", possibilityToJoin);
        model.addAttribute("mechanics", mechanics);
        model.addAttribute("day", days);  
        model.addAttribute("hour", hours);
        model.addAttribute("minute", minutes);       
        model.addAttribute("wo", wo);
        model.addAttribute("comments", comments);
        model.addAttribute("count", pageCount);
        
        model.addAttribute("cbv", new checkBoxValues());
        
        return "workOrderPage";
    }
    
    @RequestMapping(value="/workOrder/joinToWO/{woNumber}", method=GET)
    public String joinToWorkOrder(
            @PathVariable String woNumber, Model model, Authentication auth) {
        
        try {
            workOrderRepository.joinToWorkOrder(auth.getName(), woNumber);
        }
        catch(UserNotFoundException | WorkOrderNotFoundException | NotRequiredToJoinException ex) {
            
        }
                        
        return "redirect:/";
    }
    
    @RequestMapping(value="/workOrder/{woNumber}", method=POST) 
    public String defaultRemoveUsersWorkOrder(@PathVariable String woNumber,
            checkBoxValues cbv, Authentication auth) {
        
        try {
            workOrderRepository.removeUsersFromWorkOrder(cbv.getValues(), woNumber, auth.getName());
        }
        catch(UserNotFoundException | WorkOrderNotFoundException | NotYourWorkOrderException ex) {
            
        }
        
        return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="/workOrder/{woNumber}/{page}", method=POST) 
    public String removeUsersWorkOrder(@PathVariable String woNumber,
            checkBoxValues cbv, Authentication auth) {
        
        try {
            workOrderRepository.removeUsersFromWorkOrder(cbv.getValues(), woNumber, auth.getName());
        }
        catch(UserNotFoundException | WorkOrderNotFoundException | NotYourWorkOrderException ex) {
            
        }
        
        return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="/workOrder/addMechanic/{woNumber}", method=GET)
    public String addUsersWorkOrder(Model model, @PathVariable String woNumber) {
        
        model.addAttribute("cbv", new checkBoxValues());
        
        List<users> mechanics = new ArrayList<users>();
        List<users> usrList = userRepository.findAll();
        workOrder wo = workOrderRepository.findWorkOrderByWoNumber(woNumber);
        if(wo == null) {
            model.addAttribute("error", "Nie znaleziono zlecenia o podanym numerze");
            return "searchWorkOrder";
        }
        
        int i = 0;
        for(users usr : usrList) {
            
            if(usr.getWorkOrders().contains(wo)) continue;
            else mechanics.add(usr);
            
            for(authority a : usr.getAuthorities()) {
                if(a.getAuthority().equals("PLAN_ROLE") || a.getAuthority().equals("ENGIN_ROLE")){ mechanics.remove(i); i--; break; }
            }
            i++;
        }
        
        model.addAttribute("mechanics", mechanics);
        
        return "addUsersToWorkOrder";
    }
    
    @RequestMapping(value="/workOrder/addMechanic/{woNumber}", method=POST)
    public String addUsersToWorkOrder(checkBoxValues cbv, @PathVariable String woNumber, Authentication auth) {
        try {
            workOrderRepository.addUsersToWorkOrder(cbv.getValues(), woNumber, auth.getName());
        }
        catch(UserNotFoundException | WorkOrderNotFoundException | NotYourWorkOrderException  ex) {
            
        }
        
        return "redirect:/workOrder/" + woNumber;
        
    }
    
    @RequestMapping(value="/workOrder/start/{woNumber}", method=GET) 
    public String startWorkOrder(@PathVariable String woNumber, Authentication auth) {
        
        try {
            workOrderRepository.startWorkOrder(woNumber, auth.getName());
        }
        catch(WorkOrderNotFoundException | WorkOrderStatusException | UserNotFoundException | NotYourWorkOrderException ex) {
            
        }
        
        return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="/workOrder/stop/{woNumber}", method=GET) 
    public String stopWorkOrder(@PathVariable String woNumber, Authentication auth) {
        
        try {
            workOrderRepository.stopWorkOrder(woNumber, auth.getName());
        }
        catch(WorkOrderNotFoundException | WorkOrderStatusException | UserNotFoundException | NotYourWorkOrderException ex) {
            
        }
        
        return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="/workOrder/resume/{woNumber}", method=GET) 
    public String resumeWorkOrder(@PathVariable String woNumber, Authentication auth) {
        
        try {
            workOrderRepository.resumeWorkOrder(woNumber, auth.getName());
        }
        catch(WorkOrderNotFoundException | WorkOrderStatusException | UserNotFoundException | NotYourWorkOrderException ex ) {
            
        }
        
        return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="/workOrder/pause/{woNumber}/{type}", method=GET) 
    public String pauseWorkOrder(@PathVariable String woNumber, @PathVariable String type, Model model) {
        
        model.addAttribute("comment", new Comment());
                
        return "addComment";
    }
    
    @RequestMapping(value="/workOrder/addComment/{woNumber}/{type}", method=GET) 
    public String commentWorkOrder(@PathVariable String woNumber, @PathVariable String type, Model model) {
        
        model.addAttribute("comment", new Comment());
                
        return "addComment";
    }
    
    @RequestMapping(value="/workOrder/addComment/{woNumber}/{type}", method=POST) 
    public String addCommentToWorkOrder(@PathVariable String woNumber,
            @PathVariable String type, @Valid Comment comment, Errors errors, Authentication auth) {
        
            try {
                commentRepository.addComment(comment, woNumber, type, auth.getName());
            }
            catch(WorkOrderNotFoundException | WorkOrderStatusException | UserNotFoundException | NotYourWorkOrderException ex) {
                
            } 
        
            return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="/workOrder/pause/{woNumber}/{type}", method=POST) 
    public String addCommentToPauseWorkOrder(@PathVariable String woNumber,
            @PathVariable String type, @Valid Comment comment, Errors errors, Authentication auth) {
        
            try {
                commentRepository.addComment(comment, woNumber, type, auth.getName());
            }
            catch(WorkOrderNotFoundException | WorkOrderStatusException | UserNotFoundException | NotYourWorkOrderException ex) {
                
            } 
        
            return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="workOrder/searchWorkOrder", method=GET)
    public String searchWorkOrder(Model model) {
           
        return "searchWorkOrder";
    }
    
    @RequestMapping(value="workOrder/searchWorkOrder", method=POST)
    public String searchWorkOrder(
            @RequestParam String woNumber, Model model) {
        
        workOrder wo = workOrderRepository.findWorkOrderByWoNumber(woNumber);
        if(wo == null) {
            model.addAttribute("error", "Nie znaleziono zlecenia o podanym numerze");
            return "searchWorkOrder";
        }
        
        return "redirect:/workOrder/" + woNumber;
    }
    
    @RequestMapping(value="/woPanelOption", method=GET)
    public String woPanelOption(Model model) {
        
        model.addAttribute("WOPanelOption", new WOPanelOption());
        return "WOPanelOption";
    }
    
    @RequestMapping(value="/woPanelOption", method=POST)
    public String woPanelOption(@Valid WOPanelOption woPanelOption, Errors errors, Model model, 
            HttpSession session) {
        
        if(errors.hasErrors())
            return "WOPanelOption";
        
        //zapisujemy w sesji opcje wyświetlania
        session.setAttribute("wopSession", woPanelOption);
                        
        return "redirect:/workOrderPanel";
    }
    
    @RequestMapping(value="/workOrderPanel", method=GET)
    public String defaultWorkOrderPanel(Model model, ModelMap map, Authentication auth, HttpSession session) {
        
        // pobieramy z sesji opcje wyświetlania
        WOPanelOption wopSession = (WOPanelOption) session.getAttribute("wopSession");
        if(wopSession == null) return "WOPanelOption";
        
        // odpowiedni link do widoku do generacji excela
        String excelLinkName = "/workOrderPanel.xls";
        
        List<workOrder> workOrderList = workOrderRepository.findAll();
        List<woPanel> woList = new ArrayList<woPanel>();
        
        for(workOrder wo : workOrderList) {
            if(wopSession.isShowFinalized() == false) {
                if(wo.getWoStatus().equals("FINALIZED")) continue;
            }
            
            if(wopSession.getCriterium().equals("planningStart")) {
                if(!(wopSession.getBegin().before(wo.getPlanningStart()) && wopSession.getEnd().after(wo.getPlanningStart())))
                continue;
            }
            else {
                if(!(wopSession.getBegin().before(wo.getPlanningStop()) && wopSession.getEnd().after(wo.getPlanningStop())))
                continue;
            }
            
            woPanel wop = new woPanel();
            wop.setWoNumber(wo.getWoNumber());
            wop.setWoType(wo.getWoType());
            wop.setPlanningStart(wo.getPlanningStart());
            wop.setPlanningStop(wo.getPlanningStop());
            wop.setStart(wo.getStart());
            wop.setStop(wo.getStop());
            wop.setWoStatus(wo.getWoStatus());
            wop.setAreaName(wo.getArea().getAreaName());
            wop.setDescription(wo.getDescription());
            wop.setSize(wo.getUsers().size()-1);
            wop.setDelayedStart(false);
            wop.setDelayedStop(false);
            
            // sprawdzamy czy jest opóźnienie
            if(wo.getWoStatus().equals("STARTED") || wo.getWoStatus().equals("PAUSED")) {
                DateTime d1 = new DateTime(wo.getPlanningStop());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) wop.setDelayedStop(true);
                else if(hours < 0) wop.setDelayedStop(true);
                else if(days < 0) wop.setDelayedStop(true);
                else wop.setDelayedStop(false);
            }
            
            if(wo.getWoStatus().equals("NOT_STARTED")) {
                DateTime d1 = new DateTime(wo.getPlanningStart());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) wop.setDelayedStart(true);
                else if(hours < 0) wop.setDelayedStart(true);
                else if(days < 0) wop.setDelayedStart(true);
                else wop.setDelayedStart(false);
            }
            
            woList.add(wop);
        }
        if(!woList.isEmpty())
            Collections.sort(woList, woPanel.woPlanningStartComparatorASC);
        
        model.addAttribute("woList", woList);
        model.addAttribute("cbv", new checkBoxValues());
        model.addAttribute("excelLink", excelLinkName);
        map.addAttribute("woL", woList);        // tutaj dane do excela
                
        return "workOrderPanel";
    }
    
    @RequestMapping(value="/workOrderPanel/{field}/{order}", method=GET)
    public String workOrderPanel(@PathVariable String field, @PathVariable String order, Model model, ModelMap map, Authentication auth,
            HttpSession session) {
        
        // pobieramy z sesji opcje wyświetlania
        WOPanelOption wopSession = (WOPanelOption) session.getAttribute("wopSession");
        if(wopSession == null) return "WOPanelOption";
        
        // odpowiedni link do widoku do generacji excela
        String excelLinkName = "/workOrderPanel.xls";
        
        List<workOrder> workOrderList = workOrderRepository.findAll();
        List<woPanel> woList = new ArrayList<woPanel>();
        
        for(workOrder wo : workOrderList) {
            if(wopSession.isShowFinalized() == false) {
                if(wo.getWoStatus().equals("FINALIZED")) continue;
            }
            
            if(wopSession.getCriterium().equals("planningStart")) {
                if(!(wopSession.getBegin().before(wo.getPlanningStart()) && wopSession.getEnd().after(wo.getPlanningStart())))
                continue;
            }
            else {
                if(!(wopSession.getBegin().before(wo.getPlanningStop()) && wopSession.getEnd().after(wo.getPlanningStop())))
                continue;
            }
            
            woPanel wop = new woPanel();
            wop.setWoNumber(wo.getWoNumber());
            wop.setWoType(wo.getWoType());
            wop.setPlanningStart(wo.getPlanningStart());
            wop.setPlanningStop(wo.getPlanningStop());
            wop.setStart(wo.getStart());
            wop.setStop(wo.getStop());
            wop.setWoStatus(wo.getWoStatus());
            wop.setAreaName(wo.getArea().getAreaName());
            wop.setDescription(wo.getDescription());
            wop.setSize(wo.getUsers().size()-1);
            wop.setDelayedStart(false);
            wop.setDelayedStop(false);
            
            // sprawdzamy czy jest opóźnienie
            if(wo.getWoStatus().equals("STARTED") || wo.getWoStatus().equals("PAUSED")) {
                DateTime d1 = new DateTime(wo.getPlanningStop());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) wop.setDelayedStop(true);
                else if(hours < 0) wop.setDelayedStop(true);
                else if(days < 0) wop.setDelayedStop(true);
                else wop.setDelayedStop(false);
            }
            
            if(wo.getWoStatus().equals("NOT_STARTED")) {
                DateTime d1 = new DateTime(wo.getPlanningStart());
                DateTime d2 = new DateTime();
                Period per = new Period(d2, d1);
                
                int days = per.getDays();
                int hours = per.getHours();
                int minutes = per.getMinutes();
                
                if(minutes < 0) wop.setDelayedStart(true);
                else if(hours < 0) wop.setDelayedStart(true);
                else if(days < 0) wop.setDelayedStart(true);
                else wop.setDelayedStart(false);
            }
            
            woList.add(wop);
        }
        
        // ustalamy odpowiedni link do pobrania excela
        if(!woList.isEmpty()) {
            switch(field) {
                case "woNumber": {
                    if(order.equals("asc")) {
                        Collections.sort(woList, woPanel.woNumberComparatorASC);
                        excelLinkName = "/workOrderPanel/woNumber/asc.xls";
                    }
                    else {
                        Collections.sort(woList, woPanel.woNumberComparatorDESC);
                        excelLinkName = "/workOrderPanel/woNumber/desc.xls";
                    }
                    break;
                }
                case "planStart": {
                    if(order.equals("asc")) {
                        Collections.sort(woList, woPanel.woPlanningStartComparatorASC);
                        excelLinkName = "/workOrderPanel/planStart/asc.xls";
                    }
                    else {
                        Collections.sort(woList, woPanel.woPlanningStartComparatorDESC);
                        excelLinkName = "/workOrderPanel/planStart/desc.xls";
                    }
                    break;
                }
                case "planStop": {
                    if(order.equals("asc")) {
                        Collections.sort(woList, woPanel.woPlanningStopComparatorASC);
                        excelLinkName = "/workOrderPanel/planStop/asc.xls";
                    }
                    else {
                        Collections.sort(woList, woPanel.woPlanningStopComparatorDESC);
                        excelLinkName = "/workOrderPanel/planStop/desc.xls";
                    }
                    break;
                }
                case "woStatus": {
                    if(order.equals("asc")) {
                        Collections.sort(woList, woPanel.woStatusComparatorASC);
                        excelLinkName = "/workOrderPanel/woStatus/asc.xls";
                    }
                    else {
                        Collections.sort(woList, woPanel.woStatusComparatorDESC);
                        excelLinkName = "/workOrderPanel/woStatus/desc.xls";
                    }
                    break;
                }
                case "size": {
                    if(order.equals("asc")) {
                        Collections.sort(woList, woPanel.woSizeComparatorASC);
                        excelLinkName = "/workOrderPanel/size/asc.xls";
                    }
                    else {
                        Collections.sort(woList, woPanel.woSizeComparatorDESC);
                        excelLinkName = "/workOrderPanel/size/desc.xls";
                    }
                    break;
                }
                case "opStart": {
                    if(order.equals("asc")) {
                        Collections.sort(woList, woPanel.woDelayedStartComparatorASC);
                        excelLinkName = "/workOrderPanel/opStart/asc.xls";
                    }
                    else {
                        Collections.sort(woList, woPanel.woDelayedStartComparatorDESC);
                        excelLinkName = "/workOrderPanel/opStart/desc.xls";
                    }
                    break;
                }
                case "opStop": {
                    if(order.equals("asc")) {
                        Collections.sort(woList, woPanel.woDelayedStopComparatorASC);
                        excelLinkName = "/workOrderPanel/opStop/asc.xls";
                    }
                    else {
                        Collections.sort(woList, woPanel.woDelayedStopComparatorDESC);
                        excelLinkName = "/workOrderPanel/opStop/desc.xls";
                    }
                    break;
                }
            }
        }
        
        model.addAttribute("woList", woList);
        model.addAttribute("cbv", new checkBoxValues());
        model.addAttribute("excelLink", excelLinkName);
        map.addAttribute("woL", woList);
        
        return "workOrderPanel";
    }
    
    // zatrzymywanie zleceń zaznaczonych w panelu zleceń
    @RequestMapping(value="/workOrderPanel", method=POST)
    public String defaultWorkOrderPanel(
            checkBoxValues cbv, Model model, Authentication auth) {
        
        for(String cboxv : cbv.getValues()) {
            try {
                workOrderRepository.stopWorkOrder(cboxv, auth.getName());
            }
            catch(WorkOrderNotFoundException | WorkOrderStatusException | UserNotFoundException | NotYourWorkOrderException ex) {

            }
        }
        
        return "redirect:/workOrderPanel";
    }
    
    @RequestMapping(value="/workOrderPanel/{field}/{order}", method=POST)
    public String workOrderPanel(
            checkBoxValues cbv, Model model, Authentication auth) {
        
        for(String cboxv : cbv.getValues()) {
            try {
                workOrderRepository.stopWorkOrder(cboxv, auth.getName());
            }
            catch(WorkOrderNotFoundException | WorkOrderStatusException | UserNotFoundException | NotYourWorkOrderException ex) {

            }
        }
        
        return "redirect:/workOrderPanel";
    }
    
    @RequestMapping(value="/workOrder/edit/{woNumber}", method=GET)
    public String editWorkOrder(@PathVariable String woNumber, Model model) {
        workOrder wo = workOrderRepository.findWorkOrderByWoNumber(woNumber);
        if(wo == null) {
            model.addAttribute("error", "Nie znaleziono zlecenia o podanym numerze");
            return "searchWorkOrder";
        }
        
        //wypełniamy od razu formularz, żeby nie trzeba było wszystkiego wpisywać od nowa
        WorkOrder workO = new WorkOrder();
        workO.setWoNumber(wo.getWoNumber());
        if(wo.getWoType() != null)
            workO.setWoType(wo.getWoType());
        workO.setDescription(wo.getDescription());
        workO.setPlanningStart(wo.getPlanningStart());
        workO.setPlanningStartTime(wo.getPlanningStart());
        workO.setPlanningStop(wo.getPlanningStop());
        workO.setPlanningStopTime(wo.getPlanningStop());
        workO.setAreaName(wo.getArea().getAreaName());
        
        List<productionArea> pal = productionAreaRepository.findAll();
        
        model.addAttribute("paList", pal);
        model.addAttribute("workOrder", workO);
        
        return "editWorkOrder";
    }
    
    @RequestMapping(value="/workOrder/edit/{woNumber}", method=POST)
    public String editWorkOrder(@Valid WorkOrder workOrder, @PathVariable String woNumber, Model model, Authentication auth) {
        try {
            workOrderRepository.editWorkOrder(workOrder, auth.getName(), workOrder.getAreaName(), woNumber);
        }
        catch (UserNotFoundException | WorkOrderNotFoundException | NotYourWorkOrderException | ProductionAreaNotFoundException ex) {
            
        }
        
        return "redirect:/workOrderPanel";
    }
    
    @RequestMapping(value="/workOrder/addFromFile", method=GET)
    public String addFromFile() {
        return "addFromFile";
    }
    
    // dodawanie zleceń z excela
    @RequestMapping(value="/workOrder/addFromFile", method=POST)
    public String addFromFile(@RequestPart("woFile") Part woFile, Model model, Authentication auth) {
        int i = 1;
        int woCount = 0;
        int iteratorCount = 1;
        int failCount = 0;
        boolean error = false;
        List<String> errorMessage = new ArrayList<String>();
        
        // sprawdzamy, czy wybrano jakiś plik
        if(woFile.getSize() == 0) {
            model.addAttribute("fileError", "Wybierz plik!");
            return "addFromFile";
        }
        
        try {
            //woFile.write("\\woFiles\\" + woFile.getSubmittedFileName());
            //FileInputStream excelInput = new FileInputStream();
            Workbook workBook = new XSSFWorkbook(woFile.getInputStream());
            Sheet workSheet = workBook.getSheetAt(0);
            Iterator<Row> iterator = workSheet.iterator();
            iterator.next(); // omijamy opis kolumn
            
            while(iterator.hasNext()) {
                error = false;
                String areaName = new String();
                WorkOrder wo = new WorkOrder();
                //uwaga! brak walidacji
                Row nextRow = iterator.next();
                if(nextRow.getCell(0) != null) {
                    // sprawdzamy czy jest unikalny numer zlecenia
                    List<workOrder> wol = workOrderRepository.findAll();
                    nextRow.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    if(!wol.isEmpty()) {
                        workOrder w = new workOrder();                        
                        w.setWoNumber(nextRow.getCell(0).getStringCellValue());
                        if(wol.contains(w)){
                            errorMessage.add("Błąd: Taki numer zlecenia już istnieje! Wiersz: " + (iteratorCount+1));
                            error = true;
                        }
                    }
                    // sprawdzamy czy ma 10 cyfr
                    if(nextRow.getCell(0).getStringCellValue().length() != 10) {
                        errorMessage.add("Błąd: Numer powinien mieć 10 cyfr! Wiersz: " + (iteratorCount+1));
                        error = true;
                    }
                    //nextRow.getCell(0).setCellType(Cell.CELL_TYPE_STRING);
                    wo.setWoNumber(nextRow.getCell(0).getStringCellValue());
                }
                else {
                    errorMessage.add("Błąd: Numer zlecenia pusty! Wiersz: " + (iteratorCount+1));
                    error = true;
                }
                
                if(nextRow.getCell(1) != null) {
                    wo.setWoType(nextRow.getCell(1).getStringCellValue());
                }
                if(nextRow.getCell(2) != null) {
                    // "łapiemy" zły format daty
                    try {
                        Date date = nextRow.getCell(2).getDateCellValue();
                    }
                    catch(IllegalStateException ex) {
                        errorMessage.add("Błąd: Zły format daty (planowany start)! Wiersz " + (iteratorCount+1));
                        error = true;
                    }
                    if(error == false)
                        wo.setPlanningStart(nextRow.getCell(2).getDateCellValue());
                }
                else {
                    errorMessage.add("Błąd: Planowany start pusty! Wiersz: " + (iteratorCount+1));
                    error = true;
                }
                if(nextRow.getCell(3) != null) {
                    try {
                        Date date = nextRow.getCell(3).getDateCellValue();
                    }
                    catch(IllegalStateException ex) {
                        errorMessage.add("Błąd: Zły format daty (planowany stop)! Wiersz " + (iteratorCount+1));
                        error = true;
                    }
                    if(error == false)
                        wo.setPlanningStop(nextRow.getCell(3).getDateCellValue());
                }
                else {
                    errorMessage.add("Błąd: Planowany stop pusty! Wiersz: " + (iteratorCount+1));
                    error = true;
                }
                if(nextRow.getCell(4) != null) {
                    if(nextRow.getCell(4).getStringCellValue().length() < 1 || nextRow.getCell(4).getStringCellValue().length() > 1024) {
                        errorMessage.add("Błąd: Za krótki lub za długi opis zlecenia (min 1, max 1024 znaków)! Wiersz " + (iteratorCount+1));
                        error = true;
                    }
                    wo.setDescription(nextRow.getCell(4).getStringCellValue());
                }
                else {
                    errorMessage.add("Błąd: Opis zlecenia pusty! Wiersz: " + (iteratorCount+1));
                    error = true;
                }
                if(nextRow.getCell(5) != null) {
                    // sprawdzamy czy taki obszar istnieje
                    List<productionArea> pal = productionAreaRepository.findAll();
                    if(!pal.isEmpty()) {
                        productionArea pa = new productionArea();
                        pa.setAreaName(nextRow.getCell(5).getStringCellValue());
                        if(!pal.contains(pa)) {
                            errorMessage.add("Błąd: Nie ma takiego obszaru produkcji w bazie danych! Wiersz " + (iteratorCount+1));
                            error = true;
                        }
                    }
                    else {
                        // jak jest puste no to też nie da rady
                        errorMessage.add("Błąd: Nie ma takiego obszaru produkcji w bazie danych! Wiersz " + (iteratorCount+1));
                        error = true;
                    }
                    areaName = nextRow.getCell(5).getStringCellValue();
                }
                else {
                    errorMessage.add("Błąd: Obszar produkcyjny pusty! Wiersz: " + (iteratorCount+1));
                    error = true;
                }
                
                if(error == false) {
                    try {
                        workOrderRepository.addSingleWorkOrder(wo, auth.getName(), areaName);
                        woCount++;
                    }
                    catch(UserNotFoundException | ProductionAreaNotFoundException ex) {
                        failCount++;
                    }
                }
                else
                    failCount++;
                
                iteratorCount++;
            }
            
            
            workBook.close();
        }
        catch(IOException ex) {
            
        }
        
        model.addAttribute("woCount", woCount);
        model.addAttribute("fail", failCount);
        model.addAttribute("errors", errorMessage);
        
        return "ExcelImportOutput";
    }
    
    @RequestMapping(value="/workOrder/excelImportOutput", method=GET)
    public String excelImportOutput() {
        return "ExcelImportOutput";
    }
}
