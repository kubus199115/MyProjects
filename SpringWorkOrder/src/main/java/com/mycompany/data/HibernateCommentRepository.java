/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;

import com.mycompany.dto.Comment;
import com.mycompany.entity.comment;
import com.mycompany.entity.users;
import com.mycompany.entity.workOrder;
import com.mycompany.web.NotYourWorkOrderException;
import com.mycompany.web.UserNotFoundException;
import com.mycompany.web.WorkOrderNotFoundException;
import com.mycompany.web.WorkOrderStatusException;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Kubus
 */
@Repository
@Transactional
public class HibernateCommentRepository implements CommentRepository{
    
    private SessionFactory sessionFactory;
    private WorkOrderRepository workOrderRepository;
    private UserRepository userRepository;
        
    @Autowired
    public HibernateCommentRepository(SessionFactory sessionFactory, WorkOrderRepository workOrderRepository, UserRepository userRepository) {
        this.sessionFactory = sessionFactory;   // inject SessionFactory
        this.workOrderRepository = workOrderRepository;
        this.userRepository = userRepository;
    }
    
    // retrieve current session from SessionFactory
    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void addComment(Comment comment, String woNumber, String type, String username) {
        comment newComment = new comment();
        newComment.setDescription(comment.getDescription());
        newComment.setType(type);
        newComment.setCommentDate(new Date());
        
        workOrder wo = workOrderRepository.findWorkOrderByWoNumber(woNumber);
        if(wo == null) throw new WorkOrderNotFoundException();
        
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
        
        if(!user.getWorkOrders().contains(wo)) throw new NotYourWorkOrderException();
        
        if(type.equals("PAUSE_WO")) {
            if(!wo.getWoStatus().equals("STARTED")) throw new WorkOrderStatusException();
            
            newComment.setWorkOrder(wo);
            wo.setWoStatus("PAUSED");
            wo.getComments().add(newComment);

            currentSession().save(newComment);
            currentSession().update(wo);
        }
        else {
            newComment.setWorkOrder(wo);
            wo.getComments().add(newComment);

            currentSession().save(newComment);
            currentSession().update(wo);
        }
        
        
    }

    @Override
    public List<comment> findCommentByWorkOrder(String woNumber) {
        List<comment> commentList = (List<comment>) currentSession()
                .createCriteria(comment.class)
                .add(Restrictions.eq("workOrder", woNumber))
                .list();
        
        return commentList;
    }
    
}
