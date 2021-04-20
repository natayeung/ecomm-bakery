package com.natay.ecomm.bakery.checkout;

/**
 * @author natayeung
 */
public class InitiatePaymentResponse {

    private final Status status;
    private final String orderRef;
    private final String approvalLink;
    private final String failureReason;

    public InitiatePaymentResponse(Status status, String orderRef, String approvalLink, String failureReason) {
        this.status = status;
        this.orderRef = orderRef;
        this.approvalLink = approvalLink;
        this.failureReason = failureReason;
    }

    static InitiatePaymentResponse success(String orderRef, String approvalLink) {
        return new InitiatePaymentResponse(Status.SUCCESS, orderRef, approvalLink, null);
    }

    public static InitiatePaymentResponse failure(String reason) {
        return new InitiatePaymentResponse(Status.FAIL, null, null, reason);
    }

    public String getOrderRef() {
        return orderRef;
    }

    public String getApprovalLink() {
        return approvalLink;
    }


    public String getFailureReason() {
        return failureReason;
    }

    public boolean isSuccess() {
        return status == Status.SUCCESS;
    }

    public boolean isFailure() {
        return status == Status.FAIL;
    }

    enum Status {
        SUCCESS,
        FAIL
    }
}
