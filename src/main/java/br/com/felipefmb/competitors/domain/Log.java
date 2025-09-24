package br.com.felipefmb.competitors.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {

    private Log() {
    }

    private static final String PAYLOAD = "payload";

    private static final Logger LOGGER = LoggerFactory.getLogger(Log.class);

    public static void info(String message) {
        LOGGER.info(message);
    }

    public static void info(String message, Object payload) {
        LOGGER.atInfo()
                .setMessage(message)
                .addKeyValue(PAYLOAD, payload)
                .log();
    }

    public static void warn(String message) {
        LOGGER.warn(message);
    }

    public static void warn(String message, Object payload) {
        LOGGER.atWarn()
                .setMessage(message)
                .addKeyValue(PAYLOAD, payload)
                .log();
    }

    public static void error(String message) {
        LOGGER.error(message);
    }

    public static void error(String message, Object payload) {
        LOGGER.atError()
                .setMessage(message)
                .addKeyValue(PAYLOAD, payload)
                .log();
    }
}