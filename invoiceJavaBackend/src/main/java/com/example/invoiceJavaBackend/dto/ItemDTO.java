package com.example.invoiceJavaBackend.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

public class ItemDTO {
    
    @NotEmpty
    private String name;

    private String pkwiu;

    @NotEmpty
    private Integer quantity;

    @NotEmpty
    private String unit;

    @NotEmpty
    private BigDecimal netPrice;

    @NotEmpty
    private BigDecimal netValue;

    @NotEmpty
    private BigDecimal taxRate;

    @NotEmpty
    private BigDecimal taxValue;

    @NotEmpty
    private BigDecimal grossValue;

    public ItemDTO() {
        
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

}
