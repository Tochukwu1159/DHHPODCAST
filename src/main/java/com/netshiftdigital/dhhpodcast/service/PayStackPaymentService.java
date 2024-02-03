package com.netshiftdigital.dhhpodcast.service;

import com.netshiftdigital.dhhpodcast.payloads.requests.PayStackTransactionRequest;
import com.netshiftdigital.dhhpodcast.payloads.requests.PayStackTransactionResponse;
import com.netshiftdigital.dhhpodcast.utils.SubscriptionPlan;

public interface PayStackPaymentService {
    PayStackTransactionResponse initTransaction(PayStackTransactionRequest request) throws Exception;
}