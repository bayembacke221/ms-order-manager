package sn.ucad.mscustomerorder.dto;

import lombok.Data;
import sn.ucad.mscustomerorder.models.enums.OrderStatus;

import java.util.Date;
import java.util.List;

@Data
public class OrderDTO {
    private Long id;
    private Long clientId;
    private String orderDate;
    private List<OrderDetailDTO> orderDetails;

}
