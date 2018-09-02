package com.github.damingerdai.logger;

import org.junit.Test;

import static org.junit.Assert.*;

public class LoggerManagerTest {

	private final static String DEFAULT_LOGGER = "common.logger";

	@Test
	public void test1() {
		DamingerDaiLogger logger1 = LoggerManager.getLogger(DEFAULT_LOGGER);
		logger1.info("test");
		DamingerDaiLogger logger2 = LoggerManager.getLogger(DEFAULT_LOGGER);
		logger2.info("test");
		assertSame(logger1, logger2);
	}
}