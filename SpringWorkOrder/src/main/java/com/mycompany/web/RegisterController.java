/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.web;

import com.mycompany.data.UserRepository;
import com.mycompany.dto.User;
import com.mycompany.entity.users;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 *
 * @author kubus
 */
@Controller
@RequestMapping("/register")
public class RegisterController {
    
    private UserRepository userRepository;
    
    @Autowired
    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @RequestMapping(method=GET)
    public String register(Model model) {
        model.addAttribute(new User());
        
        return "register";
    }
    
    @RequestMapping(method=POST)
    public String addUser(
            @Valid User user, Errors errors) {
        
        if(errors.hasErrors())
            return "register";
        
        userRepository.addUser(user);
        
        return "redirect:/register/" + user.getUsername();
    }
    
    @RequestMapping(value="/{username}", method=GET) 
    public String showUser(
    @PathVariable String username, Model model) {
        users user = userRepository.findByUsername(username);
        if(user == null) throw new UserNotFoundException();
            
        model.addAttribute("user", user);
        
        return "profile";
    }
}
