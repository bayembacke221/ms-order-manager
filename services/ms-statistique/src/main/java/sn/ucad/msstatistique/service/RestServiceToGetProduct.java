package sn.ucad.msstatistique.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sn.ucad.msstatistique.dto.ApiCollection;
import sn.ucad.msstatistique.models.Product;

import java.util.List;

@FeignClient(name = "ms-product", url = "${app.product.url}")
public interface RestServiceToGetProduct {

    @GetMapping
    public ResponseEntity<ApiCollection<List<Product>>> getAllProducts(@RequestParam(defaultValue = "true") boolean pageable,
                                                                       @RequestParam(defaultValue = "1") int page,
                                                                       @RequestParam(defaultValue = "10") int size);
}
