package br.com.felipefmb.competitors.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Log {
    private static final Logger LOG = LoggerFactory.getLogger(Log.class);

    public static void info(String message) {
        LOG.info(message);
    }

    public static void info(String message, Object payload) {
        LOG.atInfo()
                .setMessage(message)
                .addKeyValue("payload", payload)
                .log();
    }

    public static void warn(String message) {
        LOG.warn(message);
    }

    public static void warn(String message, Object payload) {
        LOG.atWarn()
                .setMessage(message)
                .addKeyValue("payload", payload)
                .log();
    }

    public static void error(String message) {
        LOG.error(message);
    }

    public static void error(String message, Object payload) {
        LOG.atError()
                .setMessage(message)
                .addKeyValue("payload", payload)
                .log();
    }
}