package com.example.invoiceJavaBackend.dto;

import javax.validation.constraints.NotEmpty;

public class ContractorDTO {
    
    @NotEmpty
    private String name;

    @NotEmpty
    private String address;

    @NotEmpty
    private String taxId;

    private String regon;

    private String phone;

    private String email;

    public ContractorDTO() {
        
    }

    public String getName() {
        return name;
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

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ContractorDTO [address=" + address + ", email=" + email + ", name=" + name + ", phone=" + phone
                + ", regon=" + regon + ", taxId=" + taxId + "]";
    }

}
