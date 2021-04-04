package com.natay.ecomm.bakery.catalog;

import java.util.List;

/**
 * @author natayeung
 */
public interface Catalog<T> {

    List<T> findAll() throws ProductAccessException;
}
