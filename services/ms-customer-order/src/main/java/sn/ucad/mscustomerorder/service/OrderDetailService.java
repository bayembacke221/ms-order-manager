package sn.ucad.mscustomerorder.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ucad.mscustomerorder.dto.OrderDetailDTO;
import sn.ucad.mscustomerorder.exception.ResourceNotFoundException;
import sn.ucad.mscustomerorder.models.Order;
import sn.ucad.mscustomerorder.models.OrderDetail;
import sn.ucad.mscustomerorder.models.Product;
import sn.ucad.mscustomerorder.repository.OrderDetailRepository;
import sn.ucad.mscustomerorder.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderDetailService extends GenericService<OrderDetail, Long> {

    private final OrderRepository orderRepository;
    private final RestServiceToGetProduct productService;

    public OrderDetailService(OrderDetailRepository orderDetailRepository,RestServiceToGetProduct productService,OrderRepository orderRepository) {
        super(orderDetailRepository);
        this.orderRepository = orderRepository;
        this.productService = productService;
    }
    @Transactional(readOnly = true)
    public BigDecimal calculateOrderTotal(Long orderId) {
        List<OrderDetail> orderDetails = getOrderDetailsByOrderId(orderId);
        return orderDetails.stream()
                .map(detail -> detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional(readOnly = true)
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return ((OrderDetailRepository) repository).findByOrderId(orderId);
    }

    public void deleteOrderDetailsByOrderId(Long id) {
        if (!orderRepository.existsById(id)) {
            throw new ResourceNotFoundException("Order not found with id: " + id);
        }
        ((OrderDetailRepository) repository).deleteByOrderId(id);
    }
}
