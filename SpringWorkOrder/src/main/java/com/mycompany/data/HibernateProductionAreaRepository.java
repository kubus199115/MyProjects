/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;

import com.mycompany.entity.productionArea;
import com.mycompany.entity.users;
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
public class HibernateProductionAreaRepository implements ProductionAreaRepository{
    
    private SessionFactory sessionFactory;
    
    @Autowired
    public HibernateProductionAreaRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;   // inject SessionFactory
    }
    
    // retrieve current session from SessionFactory
    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public productionArea findByName(String name) {
        productionArea production = new productionArea();
        
        try {
            production = (productionArea) currentSession()
                    .createCriteria(productionArea.class)
                    .add(Restrictions.eq("areaName", name))
                    .list().get(0);
        }
        catch(IndexOutOfBoundsException ex) {
            return null;
        }
        
        return production;
    }

    @Override
    public List<productionArea> findAll() {
        List<productionArea> paList = currentSession().createCriteria(productionArea.class).list();
        
        return paList;
    }

    
}
