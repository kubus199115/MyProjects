package com.example.invoiceJavaBackend.converter;

import java.util.List;

import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.entity.ContractorEntity;

public class ContractorConverter {
    
    // changing contractor entity to dto
    public static void changeEntityToDTO(ContractorEntity contractorEntity, ContractorDTO contractorDTO) {
        
        contractorDTO.setName(contractorEntity.getName());
        contractorDTO.setAddress(contractorEntity.getAddress());
        contractorDTO.setTaxId(contractorEntity.getTaxId());
        contractorDTO.setRegon(contractorEntity.getRegon());
        contractorDTO.setPhone(contractorEntity.getPhone());
        contractorDTO.setEmail(contractorEntity.getEmail());

    }

    // changing contractor dto to entity
    public static void changeDTOToEntity(ContractorDTO contractorDTO, ContractorEntity contractorEntity) {

        contractorEntity.setName(contractorDTO.getName());
        contractorEntity.setAddress(contractorDTO.getAddress());
        contractorEntity.setTaxId(contractorDTO.getTaxId());
        contractorEntity.setRegon(contractorDTO.getRegon());
        contractorEntity.setPhone(contractorDTO.getPhone());
        contractorEntity.setEmail(contractorDTO.getEmail());

    }

    // changing contractor entities list to dto's list
    public static void changeEntityListToDTOList(List<ContractorEntity> contractorEntities,
                List<ContractorDTO> contractorDTOs) {
        
        for (ContractorEntity contractorEntity : contractorEntities) {
            ContractorDTO contractorDTO = new ContractorDTO();
            changeEntityToDTO(contractorEntity, contractorDTO);
            contractorDTOs.add(contractorDTO);
        }
    }

}
