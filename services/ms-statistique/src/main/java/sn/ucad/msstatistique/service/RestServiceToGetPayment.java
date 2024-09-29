package sn.ucad.msstatistique.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sn.ucad.msstatistique.dto.ApiCollection;
import sn.ucad.msstatistique.models.Payment;

import java.util.List;

@FeignClient(name = "ms-payment", url = "${app.payment.url}")
public interface RestServiceToGetPayment {
    @GetMapping
    public ResponseEntity<ApiCollection<List<Payment>>> getAllPayments(@RequestParam(defaultValue = "true") boolean pageable,
                                                                       @RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int size);
}
