package com.example.invoiceJavaBackend.controller;

import java.util.List;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import com.example.invoiceJavaBackend.customException.UniqueContractorNameException;
import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.repository.ContractorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class ContractorController {

    @Autowired
    private ContractorRepository contractorRepository;

    @PostMapping("/addContractor")
    public void addContractor(@Valid @RequestBody ContractorDTO contractorDTO) {

        ContractorDTO contractor = null;
        // check if contractor name exist in db
        try {
            contractor = contractorRepository.findContractorByName(contractorDTO.getName());
        } catch(NoResultException ex) {
            contractorRepository.addContractor(contractorDTO);
        }
        
        // if contractor name exist, throw exception
        if(contractor != null)
            throw new UniqueContractorNameException();

    }

    @GetMapping("/getContractors")
    public List<ContractorDTO> getContractors() {
        
        return contractorRepository.findAllContractors();

    }

    @GetMapping("/getContractor/{name}")
    public ContractorDTO getContractor(@PathVariable String name) {

        return contractorRepository.findContractorByName(name);

    }
    
    @DeleteMapping("/removeContractor/{name}")
    public void removeContractor(@PathVariable String name) {

        contractorRepository.removeContractor(name);

    }

}
