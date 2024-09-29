package sn.ucad.mscustomerorder.dto.mapper;

import sn.ucad.mscustomerorder.dto.OrderDTO;
import sn.ucad.mscustomerorder.dto.OrderDetailDTO;
import sn.ucad.mscustomerorder.models.Order;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDTOMapper {

    // Méthode pour convertir Order en OrderDTO
    public static OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setClientId(order.getClient().getId());
        dto.setOrderDate(order.getOrderDate());
        dto.setStatus(order.getStatus());

        List<OrderDetailDTO> detailDTOs = order.getOrderDetails().stream()
                .map(
                        OrderDetailDTOMapper::convertToDTO
                )
                .collect(Collectors.toList());
        dto.setOrderDetails(detailDTOs);

        return dto;
    }

    // Méthode pour convertir OrderDTO en Order
    public static Order convertToEntity(OrderDTO dto) {
        Order order = new Order();
        order.setId(dto.getId());
        order.setOrderDate(dto.getOrderDate());
        order.setStatus(dto.getStatus());
        return order;
    }
}
