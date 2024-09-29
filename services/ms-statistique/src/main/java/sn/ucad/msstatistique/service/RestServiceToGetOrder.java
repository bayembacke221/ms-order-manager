package sn.ucad.msstatistique.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sn.ucad.msstatistique.dto.ApiCollection;
import sn.ucad.msstatistique.models.Client;
import sn.ucad.msstatistique.models.Order;

import java.util.List;

@FeignClient(name = "ms-customer-order", url = "${app.commande.url}")
public interface RestServiceToGetOrder {
    @GetMapping("/orders")
    public ResponseEntity<ApiCollection<List<Order>>> getAllOrders(@RequestParam(defaultValue = "true") boolean pageable,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int size);
    @GetMapping("/clients")
    public ResponseEntity<ApiCollection<List<Client>>> getAllClients(@RequestParam(defaultValue = "true") boolean pageable,
                                                                     @RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int size);

}
