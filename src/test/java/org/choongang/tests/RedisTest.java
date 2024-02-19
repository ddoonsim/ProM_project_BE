package org.choongang.tests;

import org.choongang.commons.entities.EmailAuth;
import org.choongang.commons.repositories.EmailAuthRedisRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisTest {

    @Autowired
    private EmailAuthRedisRepository repository;

    @Test
    void test1() {
        EmailAuth auth = new EmailAuth();
        auth.setBrowserId(1L);
        auth.setVerified(true);

        repository.save(auth);

        EmailAuth auth2 = repository.findById(1L).orElse(null);
        System.out.println(auth2);
    }
}
