package sn.ucad.mscustomerorder.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ucad.mscustomerorder.dto.ApiCollection;
import sn.ucad.mscustomerorder.dto.OrderDTO;
import sn.ucad.mscustomerorder.dto.OrderDetailDTO;
import sn.ucad.mscustomerorder.dto.mapper.OrderDetailDTOMapper;
import sn.ucad.mscustomerorder.helper.ResponseHandler;
import sn.ucad.mscustomerorder.models.OrderDetail;
import sn.ucad.mscustomerorder.service.OrderDetailService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Tag(name = "order-details", description = "The order details API")
@RestController
@RequestMapping("/api/v1/order-details")
@RequiredArgsConstructor
public class OrderDetailsController {

    private final OrderDetailService orderDetailsService;

    @Operation(summary = "Get all order details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all order details",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDetailDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order details not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<ApiCollection<List<OrderDetailDTO>>> getAllOrderDetails(@RequestParam(defaultValue = "true") boolean pageable,
                                                                   @RequestParam(defaultValue = "1") int page,
                                                                   @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result = orderDetailsService.findAll(pageable, page, size);
        if (result.containsKey("totalItems")) {
            return (ResponseEntity<ApiCollection<List<OrderDetailDTO>>>) ResponseHandler.generateResponse("Liste recuperée", HttpStatus.OK, result.get("data"), (long) result.get("totalItems"), (int) result.get("totalPages"));
        } else
            return (ResponseEntity<ApiCollection<List<OrderDetailDTO>>>) ResponseHandler.generateResponse("Liste recuperée", HttpStatus.OK, result.get("data"));

    }

    @Operation(summary = "Get a order detail by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order detail",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDetailDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order detail not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> getOrderDetailById(@PathVariable Long id) {
        OrderDetail orderDetail = orderDetailsService.findById(id);
        OrderDetailDTO orderDetailDTO = OrderDetailDTOMapper.convertToDTO(orderDetail);
        return ResponseEntity.ok(orderDetailDTO);
    }

    @Operation(summary = "Update a order detail")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order detail updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDetailDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order detail not found",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDetailDTO> updateOrderDetail(@PathVariable Long id, @Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = OrderDetailDTOMapper.convertToEntity(orderDetailDTO);
        OrderDetail updatedOrderDetail = orderDetailsService.update(id, orderDetail);
        OrderDetailDTO updatedOrderDetailDTO = OrderDetailDTOMapper.convertToDTO(updatedOrderDetail);
        return ResponseEntity.ok(updatedOrderDetailDTO);
    }

    @Operation(summary = "Delete a order detail by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order detail deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDetailDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order detail not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDetail(@PathVariable Long id) {
        orderDetailsService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all order details by order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all order details by order id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDetailDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order details not found",
                    content = @Content)
    })
    @GetMapping("/order/{id}")
    public ResponseEntity<List<OrderDetailDTO>> getAllOrderDetailsByOrderId(@PathVariable Long id) {
        List<OrderDetail> orderDetails = orderDetailsService.getOrderDetailsByOrderId(id);
        List<OrderDetailDTO> orderDetailDTOs = OrderDetailDTOMapper.convertToDTOs(orderDetails);
        return ResponseEntity.ok(orderDetailDTOs);
    }

    @Operation(summary = "Delete all order details by order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order details deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDetailDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order details not found",
                    content = @Content)
    })
    @DeleteMapping("/order/{id}")
    public ResponseEntity<Void> deleteAllOrderDetailsByOrderId(@PathVariable Long id) {
        orderDetailsService.deleteOrderDetailsByOrderId(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Calculate total price of order details by order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Total price of order details by order id",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDetailDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order details not found",
                    content = @Content)
    })
    @GetMapping("/order/{id}/total-price")
    public ResponseEntity<BigDecimal> calculateTotalPriceByOrderId(@PathVariable Long id) {
        BigDecimal totalPrice = orderDetailsService.calculateOrderTotal(id);
        return ResponseEntity.ok(totalPrice);
    }
}
