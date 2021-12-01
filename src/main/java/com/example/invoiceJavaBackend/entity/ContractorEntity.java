package com.example.invoiceJavaBackend.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="Contractor")
@Entity
public class ContractorEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    private String address;

    // PL - NIP
    @Column(nullable=false)
    private String taxId;

    // PL - REGON
    private String regon;

    private String phone;

    private String email;

    @OneToMany(mappedBy = "contractor")
    private List<InvoiceEntity> invoices = new ArrayList<>();

    public ContractorEntity() {
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }

    public String getTaxId() {
        return taxId;
    }

    public void setTaxId(String taxId) {
        this.taxId = taxId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<InvoiceEntity> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<InvoiceEntity> invoices) {
        this.invoices = invoices;
    }
    
}
