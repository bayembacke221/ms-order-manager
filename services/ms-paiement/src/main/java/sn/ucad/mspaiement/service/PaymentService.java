package sn.ucad.mspaiement.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import sn.ucad.mspaiement.dto.PaymentDTO;
import sn.ucad.mspaiement.models.Order;
import sn.ucad.mspaiement.models.Payment;
import sn.ucad.mspaiement.repository.GenericRepository;

import java.math.BigDecimal;
import java.util.Map;

@Service
@Slf4j
public class PaymentService extends GenericService<Payment,Long> {
    private final RestServiceToGetOrder restServiceToGetOrder;

    public PaymentService(GenericRepository<Payment, Long> repository,RestServiceToGetOrder restServiceToGetOrder) {
        super(repository);
        this.restServiceToGetOrder = restServiceToGetOrder;
    }


    public Payment createPayment(PaymentDTO entity) {

        //Recuperer la commande
        ResponseEntity<Order> orderResponseEntity = restServiceToGetOrder.getOrderById(entity.getOrderId());

        if (orderResponseEntity.getStatusCode()== HttpStatus.OK){
          Order order = orderResponseEntity.getBody();
          if (order!=null){
              //Recuperer le montant total de la commande
              ResponseEntity<BigDecimal> totalPriceResponseEntity = restServiceToGetOrder.calculateTotalPriceByOrderId(order.getId());
              if (totalPriceResponseEntity.getStatusCode()== HttpStatus.OK){
                  BigDecimal totalPrice = totalPriceResponseEntity.getBody();
                  Payment payment = new Payment();
                  payment.setAmount(totalPrice);
                  payment.setPaymentDate(entity.getPaymentDate());
                  payment.setPaymentMethod(entity.getPaymentMethod());
                  payment.setOrder_id(order.getId());

                  //Mettre a jour le statut de la commande
                  Map<String, String> status = Map.of("status", "PAYER");
                  restServiceToGetOrder.updateOrderStatus(order.getId(), status);

                  return repository.save(payment);
          }

        }
    }
    return null;
    }
}
