package org.emrah.demorest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

	private static final Logger LOG= LogManager.getLogger(App.class);
	public static void main(String[] args) {
		System.out.println("hello world");
		LOG.debug("this is a debug statement");
		LOG.info("this is a info log");
		LOG.warn("this is a warn log");
		LOG.error("this is a error log", new NullPointerException());
		LOG.fatal("this is a fatal log");
		LOG.trace("this is a trace log");
//logger.rolling.additivity = true console a yazmaya yarıyor.
	
	}
}
