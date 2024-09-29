package sn.ucad.mspaiement.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
@Data
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private Date paymentDate;
    private String paymentMethod;
}
