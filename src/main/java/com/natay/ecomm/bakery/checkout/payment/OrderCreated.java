package com.natay.ecomm.bakery.checkout.payment;

/**
 * @author natayeung
 */
public record OrderCreated(String externalOrderId, String approvalLink) {}
