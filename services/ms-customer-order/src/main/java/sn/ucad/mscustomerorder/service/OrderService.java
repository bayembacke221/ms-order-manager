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
import sn.ucad.mscustomerorder.helper.utils.DateUtils;
import sn.ucad.mscustomerorder.models.Client;
import sn.ucad.mscustomerorder.models.Order;
import sn.ucad.mscustomerorder.models.OrderDetail;
import sn.ucad.mscustomerorder.models.Product;
import sn.ucad.mscustomerorder.models.enums.OrderStatus;
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
        order.setOrderDate(
                String.valueOf(new Date())
        );
        order.setOrderStatus(OrderStatus.CREER);
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

    @Transactional
    public Order updateOrderStatus(Long orderId, String status) {
        log.info("Updating order status: {}", orderId);

        Order order = super.findById(orderId);
        order.setOrderStatus(OrderStatus.valueOf(status));

        return super.save(order);
    }

    @Transactional
    public Order updateOrder(Long orderId, OrderDTO orderDTO) {
        log.info("Updating order: {}", orderId);

        Order order = super.findById(orderId);
        order.setOrderDate(String.valueOf(new Date()));
        order.setOrderStatus(OrderStatus.CREER);
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

    @Transactional
    public Order addOrderDetail(Long orderId, OrderDetailDTO detailDTO) {
        log.info("Adding order detail to order: {}", orderId);

        Order order = super.findById(orderId);

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

        return super.save(order);
    }

    @Transactional
    public Order removeOrderDetail(Long orderId, Long detailId) {
        log.info("Removing order detail from order: {}", orderId);

        Order order = super.findById(orderId);

        OrderDetail detail = order.getOrderDetails().stream()
                .filter(d -> d.getId().equals(detailId))
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Order detail not found with id: " + detailId));

        order.getOrderDetails().remove(detail);

        return super.save(order);
    }

    @Transactional
    public Order getOrderByIdWhereStatusPending(Long id) {
        log.info("Getting order by id where status is PENDING: {}", id);

        return ((OrderRepository) repository).findOrderByIdWhereStatusPending(id);
    }
}
