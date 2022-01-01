package com.example.invoiceJavaBackend.entity;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Table(name="Invoice")
@Entity
public class InvoiceEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    
    // PL - numer faktury
    @Column(nullable=false)
    private String invoiceNumber;

    // PL - typ faktury (wychodzący - outgoing; przychodzący - incoming)
    @Column(nullable=false)
    private String type;
    
    // PL - miejsce wydania
    @Column(nullable=false)
    private String placeOfIssue;

    // PL - data wydania
    @Column(nullable=false)
    private Date dateOfIssue;

    // PL - data sprzedaży
    @Column(nullable=false)
    private Date dateOfSale;

    // PL - metoda płatności
    @Column(nullable=false)
    private String methodOfPayment;

    // PL - termin płatności
    private String dateOfPayment;

    // PL - numer konta bankowego
    private String accountNumber;

    // PL - wartość całkowita faktury
    @Column(nullable=false)
    private BigDecimal totalValue;

    // PL - uwagi
    private String remarks;

    // only in entity
    @Column(nullable=false)
    private Timestamp addingDate;

    @ManyToOne
    @JoinColumn
    private ContractorEntity contractor;

    @OneToMany(mappedBy = "invoice")
    private List<ItemEntity> items = new ArrayList<>();

    public InvoiceEntity() {
        
    }

    public Timestamp getAddingDate() {
        return addingDate;
    }

    public void setAddingDate(Timestamp addingDate) {
        this.addingDate = addingDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getTotalValue() {
        return totalValue;
    }

    public void setTotalValue(BigDecimal totalValue) {
        this.totalValue = totalValue;
    }

    public String getDateOfPayment() {
        return dateOfPayment;
    }

    public void setDateOfPayment(String dateOfPayment) {
        this.dateOfPayment = dateOfPayment;
    }

    public String getMethodOfPayment() {
        return methodOfPayment;
    }

    public void setMethodOfPayment(String methodOfPayment) {
        this.methodOfPayment = methodOfPayment;
    }

    public Date getDateOfSale() {
        return dateOfSale;
    }

    public void setDateOfSale(Date dateOfSale) {
        this.dateOfSale = dateOfSale;
    }

    public Date getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(Date dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getPlaceOfIssue() {
        return placeOfIssue;
    }

    public void setPlaceOfIssue(String placeOfIssue) {
        this.placeOfIssue = placeOfIssue;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public ContractorEntity getContractor() {
        return contractor;
    }

    public void setContractor(ContractorEntity contractor) {
        this.contractor = contractor;
    }

    public List<ItemEntity> getItems() {
        return items;
    }

    public void setItems(List<ItemEntity> items) {
        this.items = items;
    }

}
