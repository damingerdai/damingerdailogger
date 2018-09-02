package com.github.damingerdai.function;

/**
 * @author daming
 * @version 2018-09-01 19:58
 */
@FunctionalInterface
public interface Formatter {

	String format(String format, Object...params) ;
}
