package sn.ucad.msproducts.service;

import org.springframework.stereotype.Service;
import sn.ucad.msproducts.models.Product;
import sn.ucad.msproducts.repository.ProductRepository;

@Service
public class ProductService extends GenericService<Product, Long> {
    public ProductService(ProductRepository repository) {
        super(repository);
    }

}
