package com.my.test;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TestApplicationTests {

	@Test
	@Transactional
	
	void contextLoads() {
	}

}
