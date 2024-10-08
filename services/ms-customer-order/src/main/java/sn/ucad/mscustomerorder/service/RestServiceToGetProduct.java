package sn.ucad.mscustomerorder.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sn.ucad.mscustomerorder.dto.ApiCollection;
import sn.ucad.mscustomerorder.models.Product;

import java.util.List;

@FeignClient(url = "${app.product.url}", name = "ms-products")
public interface RestServiceToGetProduct {

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id);

    @GetMapping
    public ResponseEntity<ApiCollection<List<Product>>> getAllProducts();
}
