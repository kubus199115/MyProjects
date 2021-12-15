package com.example.invoiceJavaBackend.repository;

import java.util.List;

import com.example.invoiceJavaBackend.dto.ItemDTO;

public interface ItemRepository {
    
    public void addItem(ItemDTO itemDTO);
    public List<ItemDTO> findItemsByInvoice(String invoiceNumber);

}
