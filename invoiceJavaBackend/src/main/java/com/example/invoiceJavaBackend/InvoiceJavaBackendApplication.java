package com.example.invoiceJavaBackend;

import java.util.List;

import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.repository.ContractorRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InvoiceJavaBackendApplication {

	private static final Logger log = LoggerFactory.getLogger(InvoiceJavaBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InvoiceJavaBackendApplication.class);
	}

	@Bean
  	public CommandLineRunner demo(ContractorRepository repository) {
		return (args) -> {
			ContractorDTO contractorDTO = new ContractorDTO();
			contractorDTO.setName("Firma 1");
			contractorDTO.setAddress("ul. Smile 23");
			contractorDTO.setTaxId("123-123-12-23");
			contractorDTO.setRegon("123456789");
			contractorDTO.setPhone("321234123");
			contractorDTO.setEmail("firma1@oo.pl");

			repository.addContractor(contractorDTO);

			contractorDTO.setName("Firma 2");
			contractorDTO.setAddress("ul. Polska 23");
			contractorDTO.setTaxId("123-123-11-23");
			contractorDTO.setRegon("123456722");
			contractorDTO.setPhone("121234123");
			contractorDTO.setEmail("firma2@po.pl");

			repository.addContractor(contractorDTO);

			List<ContractorDTO> contractors = repository.findAllContractor();

			contractors.forEach(con -> log.info(con.toString()));

			ContractorDTO contractorDTO2 = repository.findContractorByName("Firma 1");
			log.info(contractorDTO2.toString());
			
		};
	}

}
