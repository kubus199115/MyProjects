/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 *
 * @author Kubus
 */
@Controller
@RequestMapping("/secret")
public class SecurityController {
    @RequestMapping(method=GET)
    public String secret(Model model) {
        return "secret";
    }
}
