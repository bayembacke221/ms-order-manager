package sn.ucad.mspaiement.dto.mapper;

import sn.ucad.mspaiement.dto.PaymentDTO;
import sn.ucad.mspaiement.models.Payment;

public class PaymentDTOMapper {

    public static PaymentDTO toPaymentDTO(Payment payment) {
        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setId(payment.getId());
        paymentDTO.setOrderId(payment.getOrder_id());
        paymentDTO.setPaymentDate(payment.getPaymentDate());
        paymentDTO.setPaymentMethod(payment.getPaymentMethod());
        return paymentDTO;
    }

    public static Payment toPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setId(paymentDTO.getId());
        payment.setOrder_id(paymentDTO.getOrderId());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        return payment;
    }
}
