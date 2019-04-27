package com.soatech.soatechfinanceapi.service;

import com.soatech.soatechfinanceapi.model.entity.ClientInfoRequest;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static com.soatech.soatechfinanceapi.model.HttpContext.getHttpEntity;

@Service
public class ClientService {

    private RestTemplate _restTemplate;

    @Value("${sandbox.client.url}")
    private String reportingServerUrl;

    public ClientService(RestTemplate restTemplate) {
        this._restTemplate = restTemplate;
    }


    public Optional<ClientInfoResponse> getClientInfo(ClientInfoRequest request, String token) {
        HttpEntity<?> httpEntity = getHttpEntity(request, token);
        ClientInfoResponse response = _restTemplate.postForObject(reportingServerUrl + "/v3/client", httpEntity, ClientInfoResponse.class);
        return Optional.ofNullable(response);
    }
}