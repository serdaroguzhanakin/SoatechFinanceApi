package com.soatech.soatechfinanceapi.controllers;

import com.soatech.soatechfinanceapi.exception.FinanceApiException;
import com.soatech.soatechfinanceapi.model.Responsible;
import com.soatech.soatechfinanceapi.model.entity.TxnReportRequest;
import com.soatech.soatechfinanceapi.model.entity.TxnReportResponse;
import com.soatech.soatechfinanceapi.model.entity.TxnRequest;
import com.soatech.soatechfinanceapi.model.entity.TxnResponse;
import com.soatech.soatechfinanceapi.service.TokenService;
import com.soatech.soatechfinanceapi.service.TxnService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api")
public class TxnController {
    public static final String DENIED = "DENIED";

    private TxnService _txnService;

    private TokenService _tokenService;

    public TxnController(TxnService txnService, TokenService tokenService) {
        _txnService = txnService;
        _tokenService = tokenService;
    }

    @GetMapping("/transaction-reports")
    public ResponseEntity<TxnReportResponse> getTxns(@RequestParam("start") String startDate, @RequestParam("end") String endDate) {
        Optional<TxnReportResponse> response = _txnService.getTxns(new TxnReportRequest(startDate, endDate), _tokenService.getToken());
        response.ifPresent(resp -> {
            if (DENIED.equals(resp.getStatus()))
                throw new FinanceApiException(response.get().getMessage());
        });
        return Responsible.getResponseEntity(response);
    }

    @GetMapping("/transactions/{txnId}")
    public ResponseEntity<TxnResponse> getTxn(@PathVariable String txnId) {
        Optional<TxnResponse> response = _txnService.getTxn(new TxnRequest(txnId), _tokenService.getToken());
        response.ifPresent(resp -> {
            if (DENIED.equals(resp.getStatus()))
                throw new FinanceApiException(response.get().getMessage());
        });
        return Responsible.getResponseEntity(response);
    }
}
