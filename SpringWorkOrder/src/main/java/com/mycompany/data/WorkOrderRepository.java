 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.data;

import com.mycompany.dto.WorkOrder;
import com.mycompany.entity.workOrder;
import java.util.List;

/**
 *
 * @author Kubus
 */
public interface WorkOrderRepository {
    void addSingleWorkOrder(WorkOrder workOrder, String username, String areaName);
    void editWorkOrder(WorkOrder workOrder, String username, String areaName, String woNumber);
    workOrder findWorkOrderByWoNumber(String woNumber);
    List<workOrder> findAll();
    void joinToWorkOrder(String username, String woNumber);
    void removeUsersFromWorkOrder(List<String> usersList, String woNumber, String username);
    void addUsersToWorkOrder(List<String> usersList, String woNumber, String username);
    void startWorkOrder(String woNumber, String username);
    void resumeWorkOrder(String woNumber, String username);
    void stopWorkOrder(String woNumber, String username);
}
