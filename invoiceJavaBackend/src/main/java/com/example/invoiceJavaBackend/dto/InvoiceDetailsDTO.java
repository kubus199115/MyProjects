package com.example.invoiceJavaBackend.dto;

import java.util.List;

public class InvoiceDetailsDTO {
    
    private InvoiceDTO invoice;
    private ContractorDTO contractor;
    private List<ItemDTO> items;

    public InvoiceDetailsDTO() {
        
    }

    public List<ItemDTO> getItems() {
        return items;
    }

    public void setItems(List<ItemDTO> items) {
        this.items = items;
    }

    public ContractorDTO getContractor() {
        return contractor;
    }

    public void setContractor(ContractorDTO contractor) {
        this.contractor = contractor;
    }

    public InvoiceDTO getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceDTO invoice) {
        this.invoice = invoice;
    }

}
