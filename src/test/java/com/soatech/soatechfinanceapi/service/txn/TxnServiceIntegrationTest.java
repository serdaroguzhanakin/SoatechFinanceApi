package com.soatech.soatechfinanceapi.service.txn;

import com.soatech.soatechfinanceapi.model.JsonUtil;
import com.soatech.soatechfinanceapi.model.entity.TxnReportRequest;
import com.soatech.soatechfinanceapi.model.entity.TxnReportResponse;
import com.soatech.soatechfinanceapi.model.entity.TxnRequest;
import com.soatech.soatechfinanceapi.model.entity.TxnResponse;
import com.soatech.soatechfinanceapi.service.TokenService;
import com.soatech.soatechfinanceapi.service.TxnService;
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
public class TxnServiceIntegrationTest {

    @Autowired
    TxnService txnService;

    @Autowired
    TokenService tokenService;

    private String _token;

    @Before
    public void setUp() throws Exception {
        _token = tokenService.getToken();
    }

    @Test
    public void getStartDateEndDateWhenTxnThenApproved() {
        Optional<TxnReportResponse> txnReportResponse = txnService.getTxns(new TxnReportRequest("2015-07-01", "2019-03-01"), _token);
        assertThat(txnReportResponse).isNotNull().matches(x -> "APPROVED".equals(x.get().getStatus()));
    }

    @Test
    public void getTxnIdWhenTxnThenValidResponse() throws IOException {
        TxnResponse txnResponse = JsonUtil.toObject("transactionResponse.json", TxnResponse.class);
        Optional<TxnResponse> resp = txnService.getTxn(new TxnRequest(txnResponse.getTxn().getMerchant().getTxnId()), _token);
        assertThat(resp.get().getCustomerInfo()).isEqualToComparingFieldByFieldRecursively(txnResponse.getCustomerInfo());
    }

}
