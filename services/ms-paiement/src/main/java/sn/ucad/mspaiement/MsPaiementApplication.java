package sn.ucad.mspaiement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.ResponseEntity;
import sn.ucad.mspaiement.dto.ApiCollection;
import sn.ucad.mspaiement.models.Order;
import sn.ucad.mspaiement.service.RestServiceToGetOrder;

import java.util.List;
import java.util.Objects;

@SpringBootApplication
@EnableFeignClients
@Slf4j
public class MsPaiementApplication implements CommandLineRunner {

	@Autowired
	private RestServiceToGetOrder orderService;

	public static void main(String[] args) {
		SpringApplication.run(MsPaiementApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ResponseEntity<ApiCollection<List<Order>>> orders = orderService.getAllOrders();

		if (Objects.requireNonNull(orders.getBody()).getData().isEmpty()) {
			log.info("No order found");
		} else {
			log.info("Orders found: {}", orders.getBody().getData());
		}

	}
}
