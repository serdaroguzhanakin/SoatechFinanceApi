package com.soatech.soatechfinanceapi.service.txn;

import com.soatech.soatechfinanceapi.model.JsonUtil;
import com.soatech.soatechfinanceapi.model.entity.*;
import com.soatech.soatechfinanceapi.service.LoginService;
import com.soatech.soatechfinanceapi.service.TxnService;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class TxnServiceTest {

    @Autowired
    LoginService loginService;
    @MockBean
    private RestTemplate restTemplate;
    @Autowired
    private TxnService txnService;

    @Test
    public void getStartDateEndDateWhenTxnThenValidResponse() throws IOException {
        TxnReportResponse txnReportResponse = JsonUtil.toObject("transactionReportResponse.json", TxnReportResponse.class);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class),Matchers.<Class<TxnReportResponse>>any())).thenReturn(new ResponseEntity<>(txnReportResponse,HttpStatus.OK));
        Optional<TxnReportResponse> resp = txnService.getTxns(new TxnReportRequest("2015-07-01","2019-03-01"),null);
        assertThat(resp).isNotNull().matches(x->"APPROVED".equals(x.get().getStatus())
                && x.get().getResponse().size()== txnReportResponse.getResponse().size() );
        assertThat(resp.get()).isEqualToComparingFieldByFieldRecursively(txnReportResponse);
    }

    @Test
    public void getTxnIdWhenTxnThenValidResponse() throws IOException {
        TxnResponse txnResponse= JsonUtil.toObject("transactionResponse.json", TxnResponse.class);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class),Matchers.<Class<TxnResponse>>any())).thenReturn(new ResponseEntity<>(txnResponse,HttpStatus.OK));
        Optional<TxnResponse> resp = txnService.getTxn(new TxnRequest("1010992-1539329625-1293"),null);
        assertThat(resp.get()).isEqualToComparingFieldByFieldRecursively(txnResponse);
    }
}
