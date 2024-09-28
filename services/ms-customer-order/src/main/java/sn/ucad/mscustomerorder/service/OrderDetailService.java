package sn.ucad.mscustomerorder.service;

import org.springframework.stereotype.Service;
import sn.ucad.mscustomerorder.models.OrderDetail;
import sn.ucad.mscustomerorder.repository.OrderDetailRepository;

@Service
public class OrderDetailService extends GenericService<OrderDetail, Long> {

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        super(orderDetailRepository);
    }
}
