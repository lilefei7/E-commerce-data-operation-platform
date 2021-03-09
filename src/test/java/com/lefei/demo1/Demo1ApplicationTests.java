package com.lefei.demo1;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo1ApplicationTests {

    @Autowired
    Logger logger;
    @Test
    void contextLoads() {
        logger.info("123");
    }

}
