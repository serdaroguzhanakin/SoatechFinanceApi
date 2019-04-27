package com.soatech.soatechfinanceapi.controller.client;

import com.soatech.soatechfinanceapi.SoatechFinanceApiApplication;
import com.soatech.soatechfinanceapi.controllers.ClientController;
import com.soatech.soatechfinanceapi.exception.FinanceApiExceptionHandler;
import com.soatech.soatechfinanceapi.model.JsonUtil;
import com.soatech.soatechfinanceapi.model.entity.ClientInfoResponse;
import com.soatech.soatechfinanceapi.model.entity.CustomerInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoatechFinanceApiApplication.class)
public class ClientControllerIntegrationTest {

    @Autowired
    ClientController clientController;
    @Autowired
    FinanceApiExceptionHandler exceptionHandler;
    private MockMvc _mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        _mockMvc = standaloneSetup(this.clientController)
                .setControllerAdvice(exceptionHandler).build();
    }

    @Test
    public void getTxnIdWhenCustomerInfoThenNotFound() throws Exception {
        _mockMvc.perform(get("/api/customer-infos/{txnId}", Long.MAX_VALUE))
                .andExpect(status().isNotFound()).
                andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void getTxnIdWhenCustomerInfoThenValidResponse() throws Exception {
        ClientInfoResponse clientInfoResponse = JsonUtil.toObject("clientInfoResponse.json", ClientInfoResponse.class);
        CustomerInfo info = clientInfoResponse.getCustomerInfo();

        _mockMvc.perform(get("/api/customer-infos/{txnId}", clientInfoResponse.getTxn().getMerchant().getTxnId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.customerInfo.id").value(info.getId().intValue()))
                .andExpect(jsonPath("$.customerInfo.number").value(info.getNumber()))
                .andExpect(jsonPath("$.customerInfo.expiryMonth").value(info.getExpiryMonth()))
                .andExpect(jsonPath("$.customerInfo.expiryYear").value(info.getExpiryYear()))
                .andExpect(jsonPath("$.customerInfo.issueNumber").value(info.getIssueNumber()))
                .andExpect(jsonPath("$.customerInfo.email").value(info.getEmail()))
                .andExpect(jsonPath("$.customerInfo.birthday").value(info.getBirthday()))
                .andExpect(jsonPath("$.customerInfo.gender").value(info.getGender()))
                .andExpect(jsonPath("$.customerInfo.billingTitle").value(info.getBillingTitle()))
                .andExpect(jsonPath("$.customerInfo.billingFirstName").value(info.getBillingFirstName()))
                .andExpect(jsonPath("$.customerInfo.billingLastName").value(info.getBillingLastName()))
                .andExpect(jsonPath("$.customerInfo.billingCompany").value(info.getBillingCompany()))
                .andExpect(jsonPath("$.customerInfo.billingAddress1").value(info.getBillingAddress1()))
                .andExpect(jsonPath("$.customerInfo.billingAddress2").value(info.getBillingAddress2()))
                .andExpect(jsonPath("$.customerInfo.billingCity").value(info.getBillingCity()));
    }

}
