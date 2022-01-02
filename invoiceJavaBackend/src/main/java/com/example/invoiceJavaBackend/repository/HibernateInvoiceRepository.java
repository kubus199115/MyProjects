package com.example.invoiceJavaBackend.repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.transaction.Transactional;

import com.example.invoiceJavaBackend.converter.ContractorConverter;
import com.example.invoiceJavaBackend.converter.InvoiceConverter;
import com.example.invoiceJavaBackend.converter.ItemConverter;
import com.example.invoiceJavaBackend.dto.ContractorDTO;
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

        // setting invoice adding date
        invoiceEntity.setAddingDate(new java.sql.Timestamp(new Date().getTime()));

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

    // getting invoice full info
    @Override
    public InvoiceDetailsDTO findInvoiceDetailsByNumber(String invoiceNumber) {
        
        InvoiceEntity invoiceEntity = entityManager.createQuery("select i from InvoiceEntity i where i.invoiceNumber = ?1", 
                    InvoiceEntity.class)
                    .setParameter(1, invoiceNumber).getSingleResult();

        // getting realations
        ContractorEntity contractorEntity = invoiceEntity.getContractor();
        List<ItemEntity> itemEntities = invoiceEntity.getItems();

        // converting to dto
        InvoiceDTO invoiceDTO = new InvoiceDTO();
        InvoiceConverter.changeEntityToDTO(invoiceEntity, invoiceDTO);
        ContractorDTO contractorDTO = new ContractorDTO();
        ContractorConverter.changeEntityToDTO(contractorEntity, contractorDTO);
        List<ItemDTO> itemDTOs = new ArrayList<>();
        ItemConverter.changeEntityListToDTOList(itemEntities, itemDTOs);

        // making full invoice data
        InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
        invoiceDetailsDTO.setInvoice(invoiceDTO);
        invoiceDetailsDTO.setContractor(contractorDTO);
        invoiceDetailsDTO.setItems(itemDTOs);

        return invoiceDetailsDTO;

    }

    // generating next invoice number
    @Override
    public String getNextInvoiceNumber() {

        String invoiceNumber = "---";
        
        // getting only outgoing invoice sorted by adding date
        List<InvoiceEntity> invoices = entityManager.createQuery("select i from InvoiceEntity i where i.type='Outgoing' order by i.addingDate desc",
                    InvoiceEntity.class)
                    .setMaxResults(1).getResultList();

        // getting actuall date
        Date dateNow = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateNow);
        int yearNow = calendar.get(Calendar.YEAR);
        int monthNow = calendar.get(Calendar.MONTH);
        
        // if not invoices yet, set new number
        if(invoices.isEmpty()) {
            invoiceNumber = String.format("%d-%d-%d", 1, monthNow + 1, yearNow);
        }
        // if invoices exist, generate next number
        else {
            Date date = invoices.get(0).getAddingDate();
            // getting last invoice number
            String lastInvoiceNumber = invoices.get(0).getInvoiceNumber();
            calendar.setTime(date);
            int lastYear = calendar.get(Calendar.YEAR);
            int lastMonth = calendar.get(Calendar.MONTH);
            // check year
            // if we have new year, set number to 1, rest from actuall date
            if(lastYear < yearNow) {
                invoiceNumber = String.format("%d-%d-%d", 1, monthNow + 1, yearNow);
            }
            // if we have the same year
            else {
                // if we have new month set number to 1, rest from actuall date
                if(lastMonth < monthNow) {
                    invoiceNumber = String.format("%d-%d-%d", 1, monthNow + 1, yearNow);
                }
                // if we have the same month
                else {
                    // get the first number from invoice number
                    Pattern p = Pattern.compile("^(\\d+)");
                    Matcher m = p.matcher(lastInvoiceNumber);
                    int actuallNumber = 1000;
                    if(m.find()) {
                        actuallNumber = Integer.parseInt(m.group(0));
                    }
                    // increment actuall number from last invoice
                    int next = ++actuallNumber;
                    invoiceNumber = String.format("%d-%d-%d", next, monthNow + 1, yearNow);
                }
            }
        }

        return invoiceNumber;

    }

    // removing invoice from db
    @Override
    public void removeInvoice(String invoiceNumber) {
        
        InvoiceEntity invoiceEntity = entityManager.createQuery("select i from InvoiceEntity i where i.invoiceNumber = ?1", 
                InvoiceEntity.class)
                .setParameter(1, invoiceNumber).getSingleResult();

        // remove invoice items
        invoiceEntity.getItems().forEach(i -> entityManager.remove(i));

        entityManager.remove(invoiceEntity);
        
    }
    
}
