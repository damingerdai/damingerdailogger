package com.github.damingerdai.logger;

import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author daming
 * @version 2018-09-02 13:53
 */
public class LoggerManager {

 	private static Map<String, DamingerDaiLogger> cache = new HashMap<>(16);

	public static DamingerDaiLogger getLogger(String loggerName) {
		return cache.computeIfAbsent(loggerName, LoggerManager::buildLogger);
	}

	public static DamingerDaiLogger getLogger(Class<?> loggerClass) {
		return getLogger(loggerClass.getName());
	}

	private static  DamingerDaiLogger buildLogger(String loggerName) {
		return new DamingerDaiLogger(LoggerFactory.getLogger(loggerName));
	}
}
