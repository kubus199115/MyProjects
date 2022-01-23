/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;

import com.mycompany.dto.User;
import com.mycompany.entity.users;
import java.util.List;

/**
 *
 * @author kubus
 */
public interface UserRepository {
    void addUser(User user);
    users findByUsername(String username);
    List<users> findAll();
    List<users> findByRole(String roleName);
}
