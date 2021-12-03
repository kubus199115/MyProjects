package com.example.invoiceJavaBackend;

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

			log.info("Contractor added");
		};
	}

}
