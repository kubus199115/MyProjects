/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kubus
 */
public class checkBoxValues {

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }
    private List<String> values = new ArrayList<String>();
}
