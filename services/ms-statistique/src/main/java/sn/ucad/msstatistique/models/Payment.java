package sn.ucad.msstatistique.models;

import lombok.Data;

import java.util.Date;

@Data
public class Payment {
    private Long id;
    private Long orderId;
    private Date paymentDate;
    private String paymentMethod;
}
