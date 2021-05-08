package com.natay.ecomm.bakery.product.catalog;

import com.natay.ecomm.bakery.product.catalog.persistence.ProductQueryPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

import static com.natay.ecomm.bakery.common.Arguments.requireNonNull;

/**
 * @author natayeung
 */
@Service
@Slf4j
public class ProductCatalog implements Catalog {

    private final ConcurrentMap<String, Product> cache;
    private final ProductQueryPort productQueryPort;

    public ProductCatalog(ProductQueryPort productQueryPort) {
        this.cache = new ConcurrentHashMap<>();
        this.productQueryPort = productQueryPort;
    }

    @Override
    public List<Product> findAllProducts() throws ProductAccessException {
        return cache.values().stream()
                .sorted(Comparator.comparing(Product::productType).thenComparing(Product::title))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findProductByType(Product.Type productType) throws ProductAccessException {
        requireNonNull(productType, "Product type must be specified");

        return cache.values().stream()
                .filter(p -> p.productType() == productType)
                .sorted(Comparator.comparing(Product::title))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findProductById(String id) {
        return Optional.ofNullable(cache.get(id));
    }

    @PostConstruct
    private void loadProducts() {
        List<Product> retrievedProducts = productQueryPort.findAll();
        log.info("Retrieved {} products.", retrievedProducts.size());
        retrievedProducts.forEach(p -> cache.putIfAbsent(p.id(), p));
    }
}
