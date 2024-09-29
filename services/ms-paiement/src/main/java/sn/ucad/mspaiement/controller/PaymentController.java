package sn.ucad.mspaiement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sn.ucad.mspaiement.dto.ApiCollection;
import sn.ucad.mspaiement.dto.PaymentDTO;
import sn.ucad.mspaiement.dto.mapper.PaymentDTOMapper;
import sn.ucad.mspaiement.models.Payment;
import sn.ucad.mspaiement.service.PaymentService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "payments", description = "The payments API")
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "Get all payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all payments",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Payments not found",
                    content = @Content)
    })
    @GetMapping
    public ResponseEntity<ApiCollection<List<PaymentDTO>>> getAllPayments(@RequestParam(defaultValue = "true") boolean pageable,
                                                                          @RequestParam(defaultValue = "1") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {
        Page<Payment> paymentPage = paymentService.findAll(pageable, page, size);
        List<PaymentDTO> paymentDTOs = paymentPage.getContent().stream()
                .map(
                        PaymentDTOMapper::toPaymentDTO
                )
                .collect(Collectors.toList());

        ApiCollection<List<PaymentDTO>> response = new ApiCollection<>(
                paymentDTOs,
                paymentPage.getTotalElements(),
                paymentPage.getTotalPages()
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Get a payment by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the payment",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Payment not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<PaymentDTO> getPaymentById(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        PaymentDTO paymentDTO = PaymentDTOMapper.toPaymentDTO(payment);
        return ResponseEntity.ok(paymentDTO);
    }

    @Operation(summary = "Create a new payment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PaymentDTO.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(@RequestBody PaymentDTO paymentDTO) {
        Payment newPayment = paymentService.createPayment(paymentDTO);
        PaymentDTO newPaymentDTO = PaymentDTOMapper.toPaymentDTO(newPayment);
        return ResponseEntity.ok(newPaymentDTO);
    }
}
