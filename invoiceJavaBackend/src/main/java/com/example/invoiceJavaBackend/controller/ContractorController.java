package com.example.invoiceJavaBackend.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import com.example.invoiceJavaBackend.customException.UniqueContractorNameException;
import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.repository.ContractorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContractorController {

    @Autowired
    private ContractorRepository contractorRepository;

    @PostMapping("/addContractor")
    public void addContractor(@Valid @RequestBody ContractorDTO contractorDTO) throws UniqueContractorNameException {

        ContractorDTO contractor = null;
        // check if contractor name exist in db
        try {
            contractor = contractorRepository.findContractorByName(contractorDTO.getName());
        } catch(NoResultException ex) {
            contractorRepository.addContractor(contractorDTO);
        }
        
        // if contractor not finded, throw exception
        if(contractor != null)
            throw new UniqueContractorNameException();

    }

    @GetMapping("/getContractors")
    public List<ContractorDTO> getContractors() {
        
        return contractorRepository.findAllContractor();

    }

    // javax.persistence.NoResultException if not result found
    @GetMapping("/getContractor/{name}")
    public ContractorDTO getContractor(@PathVariable String name) {

        return contractorRepository.findContractorByName(name);

    }

}
