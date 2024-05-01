package com.example.backend.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {
    private static final Logger log = LoggerFactory.getLogger(BoardServiceTest.class);
    @Autowired
    private BoardService boardService;

    @Test
    public void testRegister(){
        log.info("testRegister...............");
    }
}
