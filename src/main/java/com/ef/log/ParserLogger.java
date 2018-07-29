package com.ef.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ParserLogger {


	private static Logger LOGGER = null;

	static {
		final InputStream stream = ParserLogger.class.getClassLoader().getResourceAsStream("logging.properties");
		try {
			LogManager.getLogManager().readConfiguration(stream);
			LOGGER = Logger.getLogger(ParserLogger.class.getName());

		} catch (IOException e) {
			throw new RuntimeException("Cannot read log configuration in logging.properties file. ");
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}


	public static void setLevel(final Level targetLevel) {
		final Logger root = Logger.getLogger("");
		root.setLevel(targetLevel);
		for (Handler handler : root.getHandlers()) {
			handler.setLevel(targetLevel);
		}
	}

	public static void testLog() {
		LOGGER.finest("log finest");
		LOGGER.finer("log finer");
		LOGGER.fine("log fine");
		LOGGER.config("log config");
		LOGGER.info("log info");
		LOGGER.warning("log warning");
		LOGGER.severe("log severe");
	}

}
