package sn.ucad.mspaiement.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import sn.ucad.mspaiement.dto.ApiCollection;
import sn.ucad.mspaiement.models.Order;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@FeignClient(url = "${app.order.url}", name = "ms-customer-order")
public interface RestServiceToGetOrder {

    @GetMapping("/orders/status/pending/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id);

    @GetMapping("/orders")
    public ResponseEntity<ApiCollection<List<Order>>> getAllOrders();

    @GetMapping("/order-details/order/{id}/total-price")
    public ResponseEntity<BigDecimal> calculateTotalPriceByOrderId(@PathVariable Long id);

    @PutMapping("/orders/status/{id}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestBody Map<String, String> status);
}
