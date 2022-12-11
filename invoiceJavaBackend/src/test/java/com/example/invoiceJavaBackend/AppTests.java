package com.example.invoiceJavaBackend;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.NoResultException;

import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.repository.ContractorRepository;
import com.example.invoiceJavaBackend.repository.InvoiceRepository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
public class AppTests {

    @Autowired
    private MockMvc mvc;
    
    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private ContractorRepository contractorRepository;

    @Test
    public void simpleTest() {

        Assertions.assertThat(invoiceRepository).isNotNull();      
        Assertions.assertThat(contractorRepository).isNotNull();        

    }

    @Test
    public void whenFindByContractorName_thenReturnContractor() {
        // given
        ContractorDTO contractor = getContractorForTest();
        contractorRepository.addContractor(contractor);

        // when
        ContractorDTO found = contractorRepository.findContractorByName(contractor.getName());

        // then
        assertEquals(contractor.getName(), found.getName());
    }

    @Test
    public void whenNotFindByContractorName_thenThrowNoResultException() {
        // given
        String companyName = "NotExistCompany";

        // then
        assertThrows(NoResultException.class, () -> {
            contractorRepository.findContractorByName(companyName);
        });
    }

    @Test
    public void givenContractor_whenGetContractor_thenStatus200() throws Exception {
        ContractorDTO contractor = getContractorForTest();
        contractorRepository.addContractor(contractor);

        mvc.perform(get("/getContractor/" + contractor.getName())).andExpect(status().isOk());
    }

    private ContractorDTO getContractorForTest() {
        ContractorDTO contractorDTO = new ContractorDTO();
        contractorDTO.setName("Company");
        contractorDTO.setAddress("ul. Smile 23");
        contractorDTO.setTaxId("123-123-12-23");
        contractorDTO.setRegon("123456789");
        contractorDTO.setPhone("321234123");
        contractorDTO.setEmail("firma1@oo.pl");

        return contractorDTO;
    }
}
