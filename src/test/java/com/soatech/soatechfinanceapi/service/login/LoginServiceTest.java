package com.soatech.soatechfinanceapi.service.login;

import com.soatech.soatechfinanceapi.model.entity.LoginRequest;
import com.soatech.soatechfinanceapi.model.entity.LoginResponse;
import com.soatech.soatechfinanceapi.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceTest {

    @Autowired
    private LoginService loginService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    public void getUserNameAndPasswordWhenLoginThenApproved() {
        when(restTemplate.postForEntity(any(String.class), new LoginRequest(any(), any()), LoginResponse.class)).thenReturn(new ResponseEntity(new LoginResponse("123", "APPROVED"), HttpStatus.OK));
        Optional<LoginResponse> loginResponse = loginService.login("", "");
        assertThat(loginResponse).isNotNull().matches(x -> x.get().getStatus().equals("APPROVED"));
    }

    @Test
    public void getUserNameAndPasswordWhenLoginThenDenied() {
        when(restTemplate.postForEntity(any(String.class), new LoginRequest(any(), any()), LoginResponse.class)).thenReturn(new ResponseEntity(new LoginResponse("123", "DENIED"), HttpStatus.OK));
        Optional<LoginResponse> loginResponse = loginService.login("", "");
        assertThat(loginResponse).isNotNull().matches(x -> x.get().getStatus().equals("DENIED"));
    }

}
