package com.example.invoiceJavaBackend.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.invoiceJavaBackend.converter.ContractorConverter;
import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.entity.ContractorEntity;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class HibernateContractorRepository implements ContractorRepository{

    @Autowired
    private EntityManager entityManager;
    
    // adding new contractor to database
    @Override
    public void addContractor(ContractorDTO contractorDTO) {
        
        ContractorEntity contractorEntity = new ContractorEntity();
        ContractorConverter.changeDTOToEntity(contractorDTO, contractorEntity);

        entityManager.persist(contractorEntity);
        
    }

    // finding all contractors and returning as dto list
    @Override
    public List<ContractorDTO> findAllContractor() {
        
        List<ContractorEntity> contractorEntities = 
                    entityManager.createQuery("select c from ContractorEntity c", 
                    ContractorEntity.class).getResultList();
        List<ContractorDTO> contractorDTOs = new ArrayList<>();

        ContractorConverter.changeEntityListToDTOList(contractorEntities, contractorDTOs);

        return contractorDTOs;

    }
 
}