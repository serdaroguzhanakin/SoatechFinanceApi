package com.soatech.soatechfinanceapi.service.client;

import com.soatech.soatechfinanceapi.model.JsonUtil;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoRequest;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoResponse;
import com.soatech.soatechfinanceapi.service.ClientService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientServiceTest {

    @MockBean
    RestTemplate restTemplate;

    @Autowired
    private ClientService clientService;

    @Test
    public void getWithoutTxnIdWhenClientInfoThenValidResponse() throws IOException {
        ClientInfoResponse clientInfoResponse = JsonUtil.toObject("clientInfoResponse.json", ClientInfoResponse.class);
        when(restTemplate.postForObject(anyString(), any(HttpEntity.class), any())).thenReturn(clientInfoResponse);
        Optional<ClientInfoResponse> clientInfo = clientService.getClientInfo(new ClientInfoRequest(""), "");
        assertThat(clientInfo.get()).isEqualToComparingFieldByFieldRecursively(clientInfoResponse);
    }

}
