package com.example.invoiceJavaBackend.repository;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.example.invoiceJavaBackend.converter.InvoiceConverter;
import com.example.invoiceJavaBackend.converter.ItemConverter;
import com.example.invoiceJavaBackend.dto.InvoiceDTO;
import com.example.invoiceJavaBackend.dto.InvoiceDetailsDTO;
import com.example.invoiceJavaBackend.dto.ItemDTO;
import com.example.invoiceJavaBackend.entity.ContractorEntity;
import com.example.invoiceJavaBackend.entity.InvoiceEntity;
import com.example.invoiceJavaBackend.entity.ItemEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class HibernateInvoiceRepository implements InvoiceRepository {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ContractorRepository contractorRepository;

    // adding new invoice to db
    @Override
    public void addInvoice(InvoiceDetailsDTO invoiceDetailsDTO) {

        InvoiceDTO invoiceDTO = invoiceDetailsDTO.getInvoice();
        String contractorName = invoiceDetailsDTO.getContractor().getName();
        List<ItemDTO> itemDTOs = invoiceDetailsDTO.getItems();

        // getting contractor entity
        ContractorEntity contractorEntity = contractorRepository.findContractorEntityByName(contractorName);

        // converting to entities
        InvoiceEntity invoiceEntity = new InvoiceEntity();
        InvoiceConverter.changeDTOToEntity(invoiceDTO, invoiceEntity);
        List<ItemEntity> itemEntities = new ArrayList<>();
        ItemConverter.changeDTOListToEntityList(itemDTOs, itemEntities);

        // setting realations
        invoiceEntity.setContractor(contractorEntity);
        invoiceEntity.setItems(itemEntities);
        contractorEntity.getInvoices().add(invoiceEntity);
        itemEntities.forEach(item -> item.setInvoice(invoiceEntity));

        // saving changes
        entityManager.persist(invoiceEntity);
        itemEntities.forEach(item -> entityManager.persist(item));
        entityManager.merge(contractorEntity);
        
    }

    // finding invoice by invoice number
    @Override
    public InvoiceDTO findInvoiceByNumber(String invoiceNumber) throws NoResultException {
        
        InvoiceEntity invoiceEntity = entityManager.createQuery("select i from InvoiceEntity i where i.invoiceNumber = ?1", 
                    InvoiceEntity.class)
                    .setParameter(1, invoiceNumber).getSingleResult();

        InvoiceDTO invoiceDTO = new InvoiceDTO();
        InvoiceConverter.changeEntityToDTO(invoiceEntity, invoiceDTO);

        return invoiceDTO;

    }

    // getting all invoices and returning as dto list 
    @Override
    public List<InvoiceDTO> findAllInvoices() {
        
        List<InvoiceEntity> invoiceEntities = entityManager.createQuery("select i from InvoiceEntity i", 
                    InvoiceEntity.class)
                    .getResultList();

        List<InvoiceDTO> invoiceDTOs = new ArrayList<>();
        InvoiceConverter.changeEntityListToDTOList(invoiceEntities, invoiceDTOs);

        return invoiceDTOs;

    }
    
}
