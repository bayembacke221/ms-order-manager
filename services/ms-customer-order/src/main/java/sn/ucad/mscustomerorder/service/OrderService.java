package sn.ucad.mscustomerorder.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sn.ucad.mscustomerorder.dto.OrderDTO;
import sn.ucad.mscustomerorder.dto.OrderDetailDTO;
import sn.ucad.mscustomerorder.dto.mapper.OrderDTOMapper;
import sn.ucad.mscustomerorder.dto.mapper.OrderDetailDTOMapper;
import sn.ucad.mscustomerorder.exception.ResourceNotFoundException;
import sn.ucad.mscustomerorder.models.Client;
import sn.ucad.mscustomerorder.models.Order;
import sn.ucad.mscustomerorder.models.OrderDetail;
import sn.ucad.mscustomerorder.models.Product;
import sn.ucad.mscustomerorder.repository.ClientRepository;
import sn.ucad.mscustomerorder.repository.OrderRepository;

import java.util.ArrayList;
import java.util.Date;

@Service
@Slf4j
public class OrderService extends GenericService<Order, Long> {

    private final RestServiceToGetProduct productService;
    private final ClientRepository clientRepository;

    public OrderService(OrderRepository orderRepository,RestServiceToGetProduct productService,
                        ClientRepository clientRepository) {
        super(orderRepository);
        this.productService = productService;
        this.clientRepository = clientRepository;
    }

    @Transactional
    public Order createOrder( OrderDTO orderDTO) {
        log.info("Saving order: {}", orderDTO);

        // Check if client exists
        if (!clientRepository.existsById(orderDTO.getClientId())) {
            throw new ResourceNotFoundException("Client not found with id: " + orderDTO.getClientId());
        }

        Client client = clientRepository.findById(orderDTO.getClientId()).orElseThrow(
                () -> new ResourceNotFoundException("Client not found with id: " + orderDTO.getClientId())
        );

        Order order = new Order();
        order.setClient(client);
        order.setOrderDate(orderDTO.getOrderDate() != null ? orderDTO.getOrderDate() : new Date());
        order.setStatus(orderDTO.getStatus() != null ? orderDTO.getStatus() : "PENDING");
        order.setOrderDetails(new ArrayList<>());

        for (OrderDetailDTO detailDTO : orderDTO.getOrderDetails()) {
            OrderDetail detail = new OrderDetail();
            detail.setOrder(order);
            detail.setProduct_id(detailDTO.getProductId());
            detail.setQuantity(detailDTO.getQuantity());

            // Get product price from product microservice
            ResponseEntity<Product> productResponse = productService.getProductById(detailDTO.getProductId());
            if (productResponse.getStatusCode() == HttpStatus.OK) {
                Product product = productResponse.getBody();
                //check if product exists
                if (product == null) {
                    throw new ResourceNotFoundException("Product not found with id: " + detailDTO.getProductId());
                }
                detail.setUnitPrice(product.getPrice());
            } else {
                throw new ResourceNotFoundException("Product not found with id: " + detailDTO.getProductId());
            }

            order.getOrderDetails().add(detail);
        }

        return super.save(order);
    }
}
