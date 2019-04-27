package com.soatech.soatechfinanceapi.service;

import com.soatech.soatechfinanceapi.model.entity.TxnReportRequest;
import com.soatech.soatechfinanceapi.model.entity.TxnReportResponse;
import com.soatech.soatechfinanceapi.model.entity.TxnRequest;
import com.soatech.soatechfinanceapi.model.entity.TxnResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.soatech.soatechfinanceapi.model.HttpContext.getHttpEntity;

@Service
public class TxnService {
    @Value("${sandbox.client.url}")
    private String reportingServerUrl;

    RestTemplate _restTemplate;

    public TxnService(RestTemplate restTemplate) {
        _restTemplate = restTemplate;
    }

    public Optional<TxnReportResponse> getTxns(TxnReportRequest txnReportRequest, String token) {
        HttpEntity<?> httpEntity = getHttpEntity(txnReportRequest, token);
        ResponseEntity<TxnReportResponse> response = _restTemplate.postForEntity(reportingServerUrl + "/v3/transactions/report", httpEntity, TxnReportResponse.class);
        return Optional.ofNullable(response.getBody());
    }

    public Optional<TxnResponse> getTxn(TxnRequest request, String token) {
        HttpEntity<?> httpEntity = getHttpEntity(request, token);
        ResponseEntity<TxnResponse> response = _restTemplate.postForEntity(reportingServerUrl + "/v3/transaction", httpEntity, TxnResponse.class);
        return Optional.ofNullable(response.getBody());
    }
}
