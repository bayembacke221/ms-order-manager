package sn.ucad.mspaiement.models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDetails {
    private Long id;
    private Long orderId;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
}
