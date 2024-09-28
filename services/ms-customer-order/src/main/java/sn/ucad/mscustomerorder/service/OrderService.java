package sn.ucad.mscustomerorder.service;

import org.springframework.stereotype.Service;
import sn.ucad.mscustomerorder.models.Order;
import sn.ucad.mscustomerorder.repository.OrderRepository;

@Service
public class OrderService extends GenericService<Order, Long> {

    public OrderService(OrderRepository orderRepository) {
        super(orderRepository);
    }
}
