package sn.ucad.msstatistique;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import sn.ucad.msstatistique.service.RestServiceToGetOrder;
import sn.ucad.msstatistique.service.RestServiceToGetPayment;
import sn.ucad.msstatistique.service.RestServiceToGetProduct;

import java.util.Objects;

@SpringBootApplication
@EnableFeignClients
@Slf4j
@RequiredArgsConstructor
public class MsStatistiqueApplication implements CommandLineRunner {

	private final RestServiceToGetOrder restServiceToGetOrder;
	private final RestServiceToGetPayment restServiceToGetPayment;
	private final RestServiceToGetProduct restServiceToGetProduct;

	public static void main(String[] args) {
		SpringApplication.run(MsStatistiqueApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Orders: {}", Objects.requireNonNull(restServiceToGetOrder.getAllOrders(false, 1, 1000).getBody()).getData());
		log.info("Payments: {}", Objects.requireNonNull(restServiceToGetPayment.getAllPayments(false, 1, 1000).getBody()).getData());
		log.info("Customers: {}", Objects.requireNonNull(restServiceToGetOrder.getAllClients(false, 1, 1000).getBody()).getData());
		log.info("Products: {}", Objects.requireNonNull(restServiceToGetProduct.getAllProducts(false, 1, 1000).getBody()).getData());
	}
}
