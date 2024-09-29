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
import sn.ucad.mscustomerorder.dto.ClientDTO;
import sn.ucad.mscustomerorder.dto.OrderDTO;
import sn.ucad.mscustomerorder.dto.mapper.OrderDTOMapper;
import sn.ucad.mscustomerorder.helper.ResponseHandler;
import sn.ucad.mscustomerorder.models.Order;
import sn.ucad.mscustomerorder.service.OrderService;

import java.util.List;
import java.util.Map;

@Tag(name = "orders", description = "The orders API")
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Get all orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all orders",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Orders not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<ApiCollection<List<OrderDTO>>> getAllOrders(@RequestParam(defaultValue = "true") boolean pageable,
                                                       @RequestParam(defaultValue = "1") int page,
                                                       @RequestParam(defaultValue = "10") int size) {
        Map<String, Object> result  = orderService.findAll(pageable, page, size);
        if (result.containsKey("totalItems")) {
            return (ResponseEntity<ApiCollection<List<OrderDTO>>>) ResponseHandler.generateResponse("Liste recuperée", HttpStatus.OK, result.get("data"), (long) result.get("totalItems"), (int) result.get("totalPages"));
        } else
            return (ResponseEntity<ApiCollection<List<OrderDTO>>>) ResponseHandler.generateResponse("Liste recuperée", HttpStatus.OK, result.get("data"));

    }

    @Operation(summary = "Get a order by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the order",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        Order order = orderService.findById(id);
        OrderDTO orderDTO = OrderDTOMapper.convertToDTO(order);
        return ResponseEntity.ok(orderDTO);
    }

    @Operation(summary = "Create a new order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order not created",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        Order newOrder = orderService.createOrder(orderDTO);
        OrderDTO newOrderDTO = OrderDTOMapper.convertToDTO(newOrder);
        return new ResponseEntity<OrderDTO>(newOrderDTO, HttpStatus.CREATED);
    }

    @Operation(summary = "Update a order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order updated",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order not updated",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id,@Valid @RequestBody  OrderDTO orderDTO) {
        Order order = OrderDTOMapper.convertToEntity(orderDTO);
        Order updatedOrder = orderService.update(id, order);
        OrderDTO updatedOrderDTO = OrderDTOMapper.convertToDTO(updatedOrder);
        return ResponseEntity.ok(updatedOrderDTO);
    }

    @Operation(summary = "Delete a order by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Order deleted",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = OrderDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Order not deleted",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<OrderDTO> deleteOrder(@PathVariable Long id) {
        orderService.deleteById(id);
        return ResponseEntity.ok().build();
    }

}
