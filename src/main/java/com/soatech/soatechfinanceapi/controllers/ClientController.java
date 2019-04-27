package com.soatech.soatechfinanceapi.controllers;

import com.soatech.soatechfinanceapi.exception.FinanceApiException;
import com.soatech.soatechfinanceapi.model.Responsible;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoRequest;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoResponse;
import com.soatech.soatechfinanceapi.service.ClientService;
import com.soatech.soatechfinanceapi.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ClientController {
    public static final String DENIED = "DENIED";

    private ClientService _clientService;
    private TokenService _tokenService;

    public ClientController(ClientService clientService, TokenService tokenService) {
        _clientService = clientService;
        _tokenService = tokenService;
    }

    @GetMapping("/customer-infos/{txnId}")
    public ResponseEntity<ClientInfoResponse> getClient(@PathVariable String txnId) {
        String token = _tokenService.getToken();
        Optional<ClientInfoResponse> client = _clientService.getClientInfo(new ClientInfoRequest(txnId), token);

        client.ifPresent(resp -> {
            if (DENIED.equals(resp.getStatus()))
                throw new FinanceApiException(client.get().getMessage());
        });
        return Responsible.getResponseEntity(client);
    }
}
