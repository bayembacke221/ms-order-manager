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
import sn.ucad.mscustomerorder.models.Product;
import sn.ucad.mscustomerorder.service.RestServiceToGetProduct;

import java.util.List;

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
		Pageable pageable = PageRequest.of(0, 10);
		ResponseEntity<Page<Product>> products = productService.getAllProducts(
				pageable
		);
		if (products.getBody() != null) {
			List<Product> productList = products.getBody().getContent();
			productList.forEach(product -> log.info("Product: {}", product));
		}
	}
}
