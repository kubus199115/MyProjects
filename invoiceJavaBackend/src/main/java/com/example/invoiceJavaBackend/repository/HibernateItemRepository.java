package com.example.invoiceJavaBackend.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import com.example.invoiceJavaBackend.converter.ItemConverter;
import com.example.invoiceJavaBackend.dto.ItemDTO;
import com.example.invoiceJavaBackend.entity.ItemEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class HibernateItemRepository implements ItemRepository{

    @Autowired
    private EntityManager entityManager;

    // adding new item to database
    @Override
    public void addItem(ItemDTO itemDTO) {
        
        ItemEntity itemEntity = new ItemEntity();
        ItemConverter.changeDTOToEntity(itemDTO, itemEntity);
        
        entityManager.persist(itemEntity);
        
    }

    @Override
    public List<ItemDTO> findItemsByInvoice(String invoiceNumber) {
        // TODO Auto-generated method stub
        return null;
    }
    
}
