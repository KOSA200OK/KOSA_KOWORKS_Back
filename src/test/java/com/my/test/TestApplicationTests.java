package com.my.test;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import java.io.File;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TestApplicationTests {

	@Test
	void contextLoads() {
		File f = new File(".");
		String []list = f.list();
		for(String name: list) {
			log.error(name);
		}
	}

}
