package com.soatech.soatechfinanceapi.service.client;

import com.soatech.soatechfinanceapi.model.JsonUtil;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoRequest;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoResponse;
import com.soatech.soatechfinanceapi.service.ClientService;
import com.soatech.soatechfinanceapi.service.TokenService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ClientServiceIntegrationTest {

    @Autowired
    TokenService tokenService;
    @Autowired
    private ClientService clientService;
    private String _token;

    @Before
    public void setUp() throws Exception {
        _token = tokenService.getToken();
    }

    @Test
    public void getTxnIdWhenCustomerInfoThenValidResponse() throws IOException {
        ClientInfoResponse clientInfoResponse = JsonUtil.toObject("clientInfoResponse.json", ClientInfoResponse.class);
        Optional<ClientInfoResponse> clientInfo = clientService.getClientInfo(new ClientInfoRequest("1010992-1539329625-1293"), _token);
        assertThat(clientInfo.get().getCustomerInfo()).isEqualToComparingFieldByFieldRecursively(clientInfoResponse.getCustomerInfo());
    }

}
