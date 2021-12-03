package com.example.invoiceJavaBackend.repository;

import java.util.ArrayList;
import java.util.List;

import com.example.invoiceJavaBackend.converter.ContractorConverter;
import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.entity.ContractorEntity;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class HibernateContractorRepository implements ContractorRepository{

    @Autowired
    private SessionFactory sessionFactory; 

    // adding new contractor to database
    @Override
    public void addContractor(ContractorDTO contractorDTO) {
        
        ContractorEntity contractorEntity = new ContractorEntity();
        ContractorConverter.changeDTOToEntity(contractorDTO, contractorEntity);

        sessionFactory.openSession().save(contractorEntity);
        
    }

    // finding all contractors and returning as dto list
    @Override
    public List<ContractorDTO> findAllContractor() {
        
        List<ContractorEntity> contractorEntities = sessionFactory.openSession().
                createQuery("from Contractor", ContractorEntity.class).list();

        List<ContractorDTO> contractorDTOs = new ArrayList<>();

        ContractorConverter.changeEntityListToDTOList(contractorEntities, contractorDTOs);

        return contractorDTOs;
    }
 
}