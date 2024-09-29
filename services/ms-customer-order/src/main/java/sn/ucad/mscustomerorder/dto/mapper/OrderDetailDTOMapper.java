package sn.ucad.mscustomerorder.dto.mapper;

import sn.ucad.mscustomerorder.dto.OrderDetailDTO;
import sn.ucad.mscustomerorder.models.OrderDetail;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDetailDTOMapper {

    // Méthode pour convertir OrderDetail en OrderDetailDTO
    public static OrderDetailDTO convertToDTO(OrderDetail detail) {
        OrderDetailDTO dto = new OrderDetailDTO();
        dto.setId(detail.getId());
        dto.setProductId(detail.getProduct_id());
        dto.setQuantity(detail.getQuantity());
        return dto;
    }

    // Méthode pour convertir OrderDetailDTO en OrderDetail
    public static OrderDetail convertToEntity(OrderDetailDTO dto) {
        OrderDetail detail = new OrderDetail();
        detail.setId(dto.getId());
        detail.setProduct_id(dto.getProductId());
        detail.setQuantity(dto.getQuantity());
        return detail;
    }

    public static List<OrderDetailDTO> convertToDTOs(List<OrderDetail> orderDetails) {
        return orderDetails.stream().map(OrderDetailDTOMapper::convertToDTO).collect(Collectors.toList());
    }
}
