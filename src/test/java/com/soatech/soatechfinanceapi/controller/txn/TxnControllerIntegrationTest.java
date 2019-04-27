package com.soatech.soatechfinanceapi.controller.txn;

import com.soatech.soatechfinanceapi.SoatechFinanceApiApplication;
import com.soatech.soatechfinanceapi.controllers.TxnController;
import com.soatech.soatechfinanceapi.exception.FinanceApiExceptionHandler;
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
public class TxnControllerIntegrationTest {
    private MockMvc _mockMvc;

    @Autowired
    TxnController txnController;

    @Autowired
    FinanceApiExceptionHandler exceptionHandler;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        _mockMvc = standaloneSetup(this.txnController)
                .setControllerAdvice(exceptionHandler)
                .build();
    }

    @Test
    public void getStartDateEndDateWhenTxnThenApproved() throws Exception {
        _mockMvc.perform(get("/api/transaction-reports?start={startDate}&end={endDate}", "2015-07-01", "2015-10-01"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.status").value("APPROVED"));
    }

}
