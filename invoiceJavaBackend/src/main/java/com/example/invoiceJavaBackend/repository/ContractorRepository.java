package com.example.invoiceJavaBackend.repository;

import java.util.List;

import com.example.invoiceJavaBackend.dto.ContractorDTO;

public interface ContractorRepository {
    
    public void addContractor(ContractorDTO contractorDTO);
    public List<ContractorDTO> findAllContractor();

}
