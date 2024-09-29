package sn.ucad.mscustomerorder;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import sn.ucad.mscustomerorder.dto.ApiCollection;
import sn.ucad.mscustomerorder.models.Product;
import sn.ucad.mscustomerorder.service.RestServiceToGetProduct;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class MsCustomerOrderApplication implements CommandLineRunner {
	@Autowired
	private  RestServiceToGetProduct productService;
	public static void main(String[] args) {
		SpringApplication.run(MsCustomerOrderApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ResponseEntity<ApiCollection<List<Product>>> products = productService.getAllProducts();
		if (!Objects.requireNonNull(products.getBody()).getData().isEmpty()) {
			log.info("Products found: {}", products.getBody().getData());
		} else {
			log.info("No product found");
		}
	}
}
