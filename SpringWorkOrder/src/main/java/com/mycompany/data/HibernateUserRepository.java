/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;


import com.mycompany.entity.users;
import com.mycompany.dto.User;
import com.mycompany.entity.authority;
import java.io.Serializable;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
import org.springframework.stereotype.Repository;


/**
 *
 * @author kubus
 */
@Repository
@Transactional
public class HibernateUserRepository implements UserRepository{

    private SessionFactory sessionFactory;
    
    @Autowired
    public HibernateUserRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;   // inject SessionFactory
    }
    
    // retrieve current session from SessionFactory
    private Session currentSession() {
        return sessionFactory.getCurrentSession();
    }
    
    @Override
    public void addUser(User user) {
           
        users userEntity = new users();
        userEntity.setUsername(user.getUsername());
        userEntity.setPassword(new StandardPasswordEncoder("53cr3t").encode(user.getPassword()));
        userEntity.setEnabled(true);
                
        authority authorityEntity = new authority();
        authorityEntity.setAuthority("MECHAN_ROLE");
        authorityEntity.setUsername(userEntity);
        
        userEntity.getAuthorities().add(authorityEntity);
        
        currentSession().save(userEntity); // use current session
        currentSession().save(authorityEntity);
    }

    @Override
    public users findByUsername(String username) {
        users userEntity = new users();
        try{
            userEntity = (users) currentSession()
                    .createCriteria(users.class)
                    .add(Restrictions.eq("username", username))
                    .list().get(0);
            }
        catch(IndexOutOfBoundsException ex) {
            return null;
        }
        
        return userEntity;
    }
    
    @Override
    public List<users> findAll() {
        List<users> usersList = currentSession().createCriteria(users.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)            // usuwamy duplikaty
                .list();
        if(usersList.isEmpty()) return null;
        
        return usersList;
    }

    @Override
    public List<users> findByRole(String roleName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
