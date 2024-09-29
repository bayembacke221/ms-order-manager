package sn.ucad.msstatistique.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sn.ucad.msstatistique.dto.ApiCollection;
import sn.ucad.msstatistique.models.Product;
import sn.ucad.msstatistique.service.StatistiqueService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Tag(name = "statistiques", description = "The statistiques API")
@RestController
@RequestMapping("/api/v1/statistiques")
@RequiredArgsConstructor
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    @Operation(summary = "Get get best selling products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found best selling products",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Best selling products not found",
                    content = @Content)
    })
    @GetMapping("/best-selling-products")
    public ResponseEntity<ApiCollection<List<Map.Entry<Long, Long>>>> getBestSellingProducts() {
        return ResponseEntity.ok(new ApiCollection<>(statistiqueService.getBestSellingProducts()));
    }

    @Operation(summary = "Get top customers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found top customers",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Top customers not found",
                    content = @Content)
    })
    @GetMapping("/top-customers")
    public ResponseEntity<ApiCollection<List<Map.Entry<Long, BigDecimal>>>> getTopCustomers() {
        return ResponseEntity.ok(new ApiCollection<>(statistiqueService.getTopCustomers()));
    }

    @Operation(summary = "Get total sales")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found total sales",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Total sales not found",
                    content = @Content)
    })
    @GetMapping("/total-sales")
    public ResponseEntity<ApiCollection<BigDecimal>> getTotalSales() {
        return ResponseEntity.ok(new ApiCollection<>(statistiqueService.getTotalSales()));
    }

    @Operation(summary = "Get get Payment Methods Distribution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Payment Methods Distribution",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Payment Methods Distribution not found",
                    content = @Content)
    })
    @GetMapping("/payment-methods-distribution")
    public ResponseEntity<ApiCollection<Map<String, Long>>> getPaymentMethodsDistribution() {
        return ResponseEntity.ok(new ApiCollection<>(statistiqueService.getPaymentMethodsDistribution()));
    }

    @Operation(summary = "Get get Payment Methods Distribution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Payment Methods Distribution",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Payment Methods Distribution not found",
                    content = @Content)
    })
    @GetMapping("/customers-distribution")
    public ResponseEntity<ApiCollection<Map<Long, Long>>> getCustomersDistribution() {
        return ResponseEntity.ok(new ApiCollection<>(statistiqueService.getCustomersDistribution()));
    }

    @Operation(summary = "Get get Payment Methods Distribution")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found Payment Methods Distribution",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema()) }),
            @ApiResponse(responseCode = "404", description = "Payment Methods Distribution not found",
                    content = @Content)
    })
    @GetMapping("/products-distribution")
    public ResponseEntity<ApiCollection<Map<Long, Long>>> getProductsDistribution() {
        return ResponseEntity.ok(new ApiCollection<>(statistiqueService.getProductsDistribution()));
    }
}
