package com.natay.ecomm.bakery.product.catalog.persistence;

import com.natay.ecomm.bakery.product.catalog.Product;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author natayeung
 */
public class ProductQueryFakeAdapter implements ProductQueryPort {

    private List<Product> products;

    public void load(Product... products) {
        this.products = List.of(products);
    }

    @Override
    public List<Product> findAll() {
        return products;
    }

    @Override
    public List<Product> findByType(Product.Type type) {
        return products.stream().filter(p -> p.productType() == type).collect(Collectors.toList());
    }

    @Override
    public Optional<Product> findById(String id) {
        return products.stream().filter(p -> p.id().equals(id)).findFirst();
    }
}