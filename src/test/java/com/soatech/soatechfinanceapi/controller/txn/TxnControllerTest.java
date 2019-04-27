package com.soatech.soatechfinanceapi.controller.txn;

import com.soatech.soatechfinanceapi.SoatechFinanceApiApplication;
import com.soatech.soatechfinanceapi.controllers.TxnController;
import com.soatech.soatechfinanceapi.exception.FinanceApiExceptionHandler;
import com.soatech.soatechfinanceapi.model.JsonUtil;
import com.soatech.soatechfinanceapi.model.entity.*;
import com.soatech.soatechfinanceapi.service.TokenService;
import com.soatech.soatechfinanceapi.service.TxnService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = SoatechFinanceApiApplication.class)
public class TxnControllerTest {

    @MockBean
    TokenService tokenService;
    @MockBean
    TxnService txnService;
    @Autowired
    TxnController txnController;
    @Autowired
    FinanceApiExceptionHandler exceptionHandler;
    private MockMvc _mockMvc;

    @Before
    public void sutup() {
        MockitoAnnotations.initMocks(this);
        this._mockMvc = standaloneSetup(this.txnController)
                .setControllerAdvice(exceptionHandler)
                .build();
    }

    @Test
    public void getTxnIdWithTokenThenNotFound() throws Exception {
        TxnResponse txnResponse = new TxnResponse();
        txnResponse.setStatus("DENIED");

        when(tokenService.getToken()).thenReturn("token");
        when(txnService.getTxn(any(TxnRequest.class), any(String.class))).thenReturn(Optional.ofNullable(txnResponse));

        _mockMvc.perform(get("/api/transactions/{txnId}", "1010992-1539329625-1293"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status").value("NOT_FOUND"));
    }

    @Test
    public void getWithStartDateEndDateWithTokenWhenTransactionThenApproved() throws Exception {
        TxnReportResponse txnReportResponse = JsonUtil.toObject("transactionReportResponse.json", TxnReportResponse.class);
        when(tokenService.getToken()).thenReturn("Token");
        when(txnService.getTxns(any(TxnReportRequest.class), any(String.class))).thenReturn(Optional.ofNullable(txnReportResponse));
        _mockMvc.perform(get("/api/transaction-reports?start={startDate}&end={endDate}", "2015-07-01", "2015-10-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

    @Test
    public void getTxnIdWithTokenWhenTxnThenValidResponse() throws Exception {
        TxnResponse txnResponse = JsonUtil.toObject("transactionResponse.json", TxnResponse.class);

        when(tokenService.getToken()).thenReturn("Token");
        when(txnService.getTxn(any(TxnRequest.class), any(String.class))).thenReturn(Optional.ofNullable(txnResponse));

        MerchantTxn merchant = txnResponse.getTxn().getMerchant();
        _mockMvc.perform(get("/api/transactions/{txnId}", "1010992-1539329625-1293"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.txn.merchant.id").value(merchant.getId().intValue()))
                .andExpect(jsonPath("$.txn.merchant.referenceNo").value(merchant.getReferenceNo()))
                .andExpect(jsonPath("$.txn.merchant.status").value(merchant.getStatus()))
                .andExpect(jsonPath("$.txn.merchant.operation").value(merchant.getOperation()))
                .andExpect(jsonPath("$.txn.merchant.type").value(merchant.getType()))
                .andExpect(jsonPath("$.txn.merchant.txnId").value(merchant.getTxnId()));
    }
}
