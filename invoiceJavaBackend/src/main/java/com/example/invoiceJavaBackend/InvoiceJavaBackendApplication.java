package com.example.invoiceJavaBackend;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.example.invoiceJavaBackend.dto.ContractorDTO;
import com.example.invoiceJavaBackend.dto.InvoiceDTO;
import com.example.invoiceJavaBackend.dto.InvoiceDetailsDTO;
import com.example.invoiceJavaBackend.dto.ItemDTO;
import com.example.invoiceJavaBackend.repository.ContractorRepository;
import com.example.invoiceJavaBackend.repository.InvoiceRepository;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InvoiceJavaBackendApplication {

	//private static final Logger log = LoggerFactory.getLogger(InvoiceJavaBackendApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(InvoiceJavaBackendApplication.class);
	}

	@Bean
  	public CommandLineRunner demo(ContractorRepository repository, InvoiceRepository invoiceRepository) {
		return (args) -> {
			ContractorDTO contractorDTO = new ContractorDTO();
			contractorDTO.setName("Firma 1");
			contractorDTO.setAddress("ul. Smile 23");
			contractorDTO.setTaxId("123-123-12-23");
			contractorDTO.setRegon("123456789");
			contractorDTO.setPhone("321234123");
			contractorDTO.setEmail("firma1@oo.pl");

			repository.addContractor(contractorDTO);

			ContractorDTO contractorDTO1 = new ContractorDTO();
			contractorDTO1.setName("Firma 2");
			contractorDTO1.setAddress("ul. Polska 23");
			contractorDTO1.setTaxId("123-123-11-23");
			contractorDTO1.setRegon("123456722");
			contractorDTO1.setPhone("121234123");

			repository.addContractor(contractorDTO1);

			ContractorDTO contractorDTO2 = repository.findContractorByName("Firma 1");
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			InvoiceDTO invoiceDTO = new InvoiceDTO();
			invoiceDTO.setInvoiceNumber("01-01-2021");
			invoiceDTO.setType("outgoing");
			invoiceDTO.setPlaceOfIssue("Kutno");
			invoiceDTO.setDateOfIssue(formatter.parse("2021-03-03"));
			invoiceDTO.setDateOfSale(formatter.parse("2021-03-03"));
			invoiceDTO.setMethodOfPayment("cash");
			invoiceDTO.setTotalValue(new BigDecimal("2.46"));

			List<ItemDTO> items = new ArrayList<>();

			ItemDTO itemDTO = new ItemDTO();
			itemDTO.setName("Bin");
			itemDTO.setPkwiu("23io0");
			itemDTO.setQuantity(2);
			itemDTO.setUnit("szt.");
			itemDTO.setNetPrice(new BigDecimal("1.00"));
			itemDTO.setNetValue(new BigDecimal("2.00"));
			itemDTO.setTaxRate(new BigDecimal("0.23"));
			itemDTO.setTaxValue(new BigDecimal("0.46"));
			itemDTO.setGrossValue(new BigDecimal("2.46"));

			items.add(itemDTO);

			InvoiceDetailsDTO invoiceDetailsDTO = new InvoiceDetailsDTO();
			invoiceDetailsDTO.setInvoice(invoiceDTO);
			invoiceDetailsDTO.setContractor(contractorDTO2);
			invoiceDetailsDTO.setItems(items);

			invoiceRepository.addInvoice(invoiceDetailsDTO);
			
		};
	}

}
