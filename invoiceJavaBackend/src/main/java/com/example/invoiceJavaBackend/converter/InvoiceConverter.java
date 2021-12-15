package com.example.invoiceJavaBackend.converter;

import java.sql.Date;
import java.util.List;

import com.example.invoiceJavaBackend.dto.InvoiceDTO;
import com.example.invoiceJavaBackend.entity.InvoiceEntity;

public class InvoiceConverter {

    // changing invoice entity to dto
    public static void changeEntityToDTO(InvoiceEntity invoiceEntity, InvoiceDTO invoiceDTO) {

        invoiceDTO.setInvoiceNumber(invoiceEntity.getInvoiceNumber());
        invoiceDTO.setPlaceOfIssue(invoiceEntity.getPlaceOfIssue());
        invoiceDTO.setDateOfIssue(invoiceEntity.getDateOfIssue());
        invoiceDTO.setDateOfSale(invoiceEntity.getDateOfSale());
        invoiceDTO.setMethodOfPayment(invoiceEntity.getMethodOfPayment());
        invoiceDTO.setDateOfPayment(invoiceEntity.getDateOfPayment());
        invoiceDTO.setTotalValue(invoiceEntity.getTotalValue());
        invoiceDTO.setRemarks(invoiceEntity.getRemarks());

    }

    // changing invoice dto to entity
    public static void changeDTOToEntity(InvoiceDTO invoiceDTO, InvoiceEntity invoiceEntity) {

        invoiceEntity.setInvoiceNumber(invoiceDTO.getInvoiceNumber());
        invoiceEntity.setPlaceOfIssue(invoiceDTO.getPlaceOfIssue());
        invoiceEntity.setDateOfIssue(new Date(invoiceDTO.getDateOfIssue().getTime()));
        invoiceEntity.setDateOfSale(new Date(invoiceDTO.getDateOfSale().getTime()));
        invoiceEntity.setMethodOfPayment(invoiceDTO.getMethodOfPayment());
        invoiceEntity.setDateOfPayment(invoiceDTO.getDateOfPayment());
        invoiceEntity.setTotalValue(invoiceDTO.getTotalValue());
        invoiceEntity.setRemarks(invoiceDTO.getRemarks());

    }

    // changing invoice entities list to dto's list
    public static void changeEntityListToDTOList(List<InvoiceEntity> invoiceEntities,
            List<InvoiceDTO> invoiceDTOs) {
        
        for (InvoiceEntity invoiceEntity : invoiceEntities) {
            InvoiceDTO invoiceDTO = new InvoiceDTO();
            changeEntityToDTO(invoiceEntity, invoiceDTO);
            invoiceDTOs.add(invoiceDTO);
        }

    }
    
}
