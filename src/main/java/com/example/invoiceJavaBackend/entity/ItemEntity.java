package com.example.invoiceJavaBackend.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Table(name="Item")
@Entity
public class ItemEntity {
    
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    @Column(nullable=false)
    private String name;

    // PL - symbol PKWiU
    private String pkwiu;

    // PL - ilość
    @Column(nullable=false)
    private Integer quantity;

    // PL - jednostka miary
    @Column(nullable=false)
    private String unit;

    // PL - cena netto
    @Column(nullable=false)
    private BigDecimal netPrice;

    // PL - wartość netto
    @Column(nullable=false)
    private BigDecimal netValue;

    // PL - stawka VAT
    @Column(nullable=false)
    private BigDecimal taxRate;

    // PL - kwota VAT
    @Column(nullable=false)
    private BigDecimal taxValue;

    // PL - wartość z VAT
    @Column(nullable=false)
    private BigDecimal grossValue;

    @ManyToOne
    @JoinColumn
    private InvoiceEntity invoice;

    public ItemEntity() {
        
    }

    public BigDecimal getGrossValue() {
        return grossValue;
    }

    public void setGrossValue(BigDecimal grossValue) {
        this.grossValue = grossValue;
    }

    public BigDecimal getTaxValue() {
        return taxValue;
    }

    public void setTaxValue(BigDecimal taxValue) {
        this.taxValue = taxValue;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getNetValue() {
        return netValue;
    }

    public void setNetValue(BigDecimal netValue) {
        this.netValue = netValue;
    }

    public BigDecimal getNetPrice() {
        return netPrice;
    }

    public void setNetPrice(BigDecimal netPrice) {
        this.netPrice = netPrice;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getPkwiu() {
        return pkwiu;
    }

    public void setPkwiu(String pkwiu) {
        this.pkwiu = pkwiu;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvoiceEntity getInvoice() {
        return invoice;
    }

    public void setInvoice(InvoiceEntity invoice) {
        this.invoice = invoice;
    }


}