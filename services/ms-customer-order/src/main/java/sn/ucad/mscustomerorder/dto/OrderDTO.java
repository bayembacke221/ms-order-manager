package sn.ucad.mscustomerorder.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long clientId;
    private Date orderDate;
    private String status;
    private List<OrderDetailDTO> orderDetails;

}
