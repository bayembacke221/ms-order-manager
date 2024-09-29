package sn.ucad.mspaiement.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long order_id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Date paymentDate;

    @Column(nullable = false)
    private String paymentMethod;

}
