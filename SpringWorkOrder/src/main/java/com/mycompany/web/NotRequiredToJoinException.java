/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.web;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Kubus
 */
@ResponseStatus(value=HttpStatus.CONFLICT, reason="You don't need to join to this work order")
public class NotRequiredToJoinException extends RuntimeException{
    
}
