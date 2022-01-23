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
@ResponseStatus(value=HttpStatus.UNPROCESSABLE_ENTITY, reason="This work order number is not unique")
public class NotUniqueWoNumberException extends RuntimeException {
    
}
