package com.example.invoiceJavaBackend.converter;

import java.util.List;

import com.example.invoiceJavaBackend.dto.ItemDTO;
import com.example.invoiceJavaBackend.entity.ItemEntity;

public class ItemConverter {
    
    // changing item entity to dto
    public static void changeEntityToDTO(ItemEntity itemEntity, ItemDTO itemDTO) {

        itemDTO.setName(itemEntity.getName());
        itemDTO.setPkwiu(itemEntity.getPkwiu());
        itemDTO.setQuantity(itemEntity.getQuantity());
        itemDTO.setUnit(itemEntity.getUnit());
        itemDTO.setNetPrice(itemEntity.getNetPrice());
        itemDTO.setNetValue(itemEntity.getNetValue());
        itemDTO.setTaxRate(itemEntity.getTaxRate());
        itemDTO.setTaxValue(itemEntity.getTaxValue());
        itemDTO.setGrossValue(itemEntity.getGrossValue());

    }

    // changing item dto to entity
    public static void changeDTOToEntity(ItemDTO itemDTO, ItemEntity itemEntity) {

        itemEntity.setName(itemDTO.getName());
        itemEntity.setPkwiu(itemDTO.getPkwiu());
        itemEntity.setQuantity(itemDTO.getQuantity());
        itemEntity.setUnit(itemDTO.getUnit());
        itemEntity.setNetPrice(itemDTO.getNetPrice());
        itemEntity.setNetValue(itemDTO.getNetValue());
        itemEntity.setTaxRate(itemDTO.getTaxRate());
        itemEntity.setTaxValue(itemDTO.getTaxValue());
        itemEntity.setGrossValue(itemDTO.getGrossValue());

    }

    // changing item entities list to dto's list
    public static void changeEntityListToDTOList(List<ItemEntity> itemEntities,
                List<ItemDTO> itemDTOs) {
        
        for (ItemEntity itemEntity : itemEntities) {
            ItemDTO itemDTO = new ItemDTO();
            changeEntityToDTO(itemEntity, itemDTO);
            itemDTOs.add(itemDTO);
        }

    }

    // changing item entities list to dto's list
    public static void changeDTOListToEntityList(List<ItemDTO> itemDTOs,
                List<ItemEntity> itemEntities) {
        
        for (ItemDTO itemDTO : itemDTOs) {
            ItemEntity itemEntity = new ItemEntity();
            changeDTOToEntity(itemDTO, itemEntity);
            itemEntities.add(itemEntity);
        }

    }

}
