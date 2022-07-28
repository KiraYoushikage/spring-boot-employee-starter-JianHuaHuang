package com.rest.springbootemployee;

import org.jasypt.encryption.StringEncryptor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;

@SpringBootTest
@WebAppConfiguration
@ActiveProfiles("test")
public class EncryptPwdTest {
    @Autowired
    StringEncryptor encryptor;

    @Test
    public void getPass() {
        String url = encryptor.encrypt("jdbc:mysql://127.0.0.1:3306/employee_database?characterEncoding=utf-8&useSSL=false&serverTimezone=GMT%2b8");
        String name = encryptor.encrypt("root");
        String password = encryptor.encrypt("root");
        System.out.println(url);
        System.out.println(name);
        System.out.println(password);
    }
}
