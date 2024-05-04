package com.menu.app;

import com.menu.app.application.Config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = { Config.class })
class AppApplicationTests {
	@Test
	void contextLoads(ApplicationContext context) {
		assertNotNull(context);
	}
}
