package com.example.invoiceJavaBackend.repository;

import java.util.List;

import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.entity.ContractorEntity;

public interface ContractorRepository {
    
    public void addContractor(ContractorDTO contractorDTO);
    public void removeContractor(String name);
    public List<ContractorDTO> findAllContractors();
    public ContractorDTO findContractorByName(String name);
    public ContractorEntity findContractorEntityByName(String name);

}
