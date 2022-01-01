package com.example.invoiceJavaBackend.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;

import com.example.invoiceJavaBackend.customException.UniqueInvoiceNumberException;
import com.example.invoiceJavaBackend.dto.InvoiceDTO;
import com.example.invoiceJavaBackend.dto.InvoiceDetailsDTO;
import com.example.invoiceJavaBackend.repository.InvoiceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InvoiceController {

    @Autowired
    private InvoiceRepository invoiceRepository;

    @PostMapping("/addInvoice")
    public void addInvoice(@Valid @RequestBody InvoiceDetailsDTO invoiceDetailsDTO) {

        InvoiceDTO invoiceDTO = null;
        // check if invoice number exist in db
        try {
            invoiceDTO = invoiceRepository.findInvoiceByNumber(invoiceDetailsDTO.getInvoice().getInvoiceNumber());
        } catch (NoResultException ex) {
            invoiceRepository.addInvoice(invoiceDetailsDTO);
        }

        // if invoice number exist, throw exception
        if(invoiceDTO != null)
            throw new UniqueInvoiceNumberException();

    }

    @GetMapping("/getInvoices")
    public List<InvoiceDTO> getInvoices() {
        
        return invoiceRepository.findAllInvoices();

    }

    @GetMapping("/getInvoiceDetails/{invoiceNumber}")
    public InvoiceDetailsDTO getInvoiceDetails(@PathVariable String invoiceNumber) {

        return invoiceRepository.findInvoiceDetailsByNumber(invoiceNumber);

    }

    @GetMapping("/getNextInvoiceNumber")
    public String getNextInvoiceNumber() {

        return invoiceRepository.getNextInvoiceNumber();
        
    }

    @DeleteMapping("/removeInvoice/{invoiceNumber}")
    public void removeInvoice(@PathVariable String invoiceNumber) {

        invoiceRepository.removeInvoice(invoiceNumber);

    }
    
}
