/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;

import com.mycompany.entity.productionArea;
import java.util.List;

/**
 *
 * @author Kubus
 */
public interface ProductionAreaRepository {
    productionArea findByName(String name);
    List<productionArea> findAll();
}
