package com.soatech.soatechfinanceapi.service.login;

import com.soatech.soatechfinanceapi.model.entity.LoginResponse;
import com.soatech.soatechfinanceapi.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LoginServiceIntegrationTest {

    @Autowired
    private LoginService loginService;

    @Test
    public void getUserNameAndPasswordWhenLoginThenApproved() {
        Optional<LoginResponse> loginResponse = loginService.login("demo@bumin.com.tr", "cjaiU8CV");
        assertThat(loginResponse).isNotNull().matches(x -> x.get().getStatus().equals("APPROVED"));
    }

}
