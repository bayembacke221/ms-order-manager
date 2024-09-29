package sn.ucad.msstatistique.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import sn.ucad.msstatistique.models.Order;
import sn.ucad.msstatistique.models.OrderDetails;
import sn.ucad.msstatistique.models.Payment;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StatistiqueService {

    private final RestServiceToGetOrder restServiceToGetOrder;
    private final RestServiceToGetPayment restServiceToGetPayment;
    private final RestServiceToGetProduct restServiceToGetProduct;

    public List<Map.Entry<Long, Long>> getBestSellingProducts() {
        List<Order> orders = Objects.requireNonNull(restServiceToGetOrder.getAllOrders(false, 1, 1000).getBody()).getData();
        Map<Long, Long> productSales = orders.stream()
                .flatMap(order -> order.getOrderDetails().stream())
                .collect(Collectors.groupingBy(
                        OrderDetails::getProductId,
                        Collectors.summingLong(OrderDetails::getQuantity)
                ));
        return productSales.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public List<Map.Entry<Long, BigDecimal>> getTopCustomers() {
        List<Order> orders = Objects.requireNonNull(restServiceToGetOrder.getAllOrders(false, 1, 1000).getBody()).getData();
        Map<Long, BigDecimal> customerSales = orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getClientId,
                        Collectors.reducing(
                                BigDecimal.ZERO,
                                order -> order.getOrderDetails().stream()
                                        .map(detail -> detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity())))
                                        .reduce(BigDecimal.ZERO, BigDecimal::add),
                                BigDecimal::add
                        )
                ));
        return customerSales.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public BigDecimal getTotalSales() {
        List<Order> orders = Objects.requireNonNull(restServiceToGetOrder.getAllOrders(false, 1, 1000).getBody()).getData();
        return orders.stream()
                .flatMap(order -> order.getOrderDetails().stream())
                .map(detail -> detail.getUnitPrice().multiply(new BigDecimal(detail.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Map<String, Long> getPaymentMethodsDistribution() {
        List<Payment> payments = Objects.requireNonNull(restServiceToGetPayment.getAllPayments(false, 1, 1000).getBody()).getData();
        return payments.stream()
                .collect(Collectors.groupingBy(
                        Payment::getPaymentMethod,
                        Collectors.counting()
                ));
    }

    public Map<Long, Long> getCustomersDistribution() {
        List<Order> orders = Objects.requireNonNull(restServiceToGetOrder.getAllOrders(false, 1, 1000).getBody()).getData();
        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getClientId,
                        Collectors.counting()
                ));
    }

    public Map<Long, Long> getProductsDistribution() {
        List<Order> orders = Objects.requireNonNull(restServiceToGetOrder.getAllOrders(false, 1, 1000).getBody()).getData();
        return orders.stream()
                .flatMap(order -> order.getOrderDetails().stream())
                .collect(Collectors.groupingBy(
                        OrderDetails::getProductId,
                        Collectors.summingLong(OrderDetails::getQuantity)
                ));
    }





}
