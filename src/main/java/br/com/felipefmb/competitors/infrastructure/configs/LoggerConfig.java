package br.com.felipefmb.competitors.infrastructure.configs;

import ch.qos.logback.classic.pattern.ClassicConverter;
import com.fasterxml.jackson.databind.ObjectMapper;
import ch.qos.logback.classic.spi.ILoggingEvent;
import org.slf4j.event.KeyValuePair;
import java.util.List;
import java.util.stream.Collectors;

public class LoggerConfig extends ClassicConverter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convert(ILoggingEvent event) {
        List<KeyValuePair> kvs = event.getKeyValuePairs();
        if (kvs == null || kvs.isEmpty()) {
            return "";
        }

        String onlyKey = getFirstOption(); // null se não houver {opção}

        if (onlyKey != null && !onlyKey.isBlank()) {
            for (KeyValuePair kv : kvs) {
                if (onlyKey.equals(kv.key)) {
                    return " " + onlyKey + "=" + toJsonSafe(kv.value);
                }
            }
            return ""; // não havia a chave solicitada
        }

        String joined = kvs.stream()
                .map(kv -> kv.key + "=" + toJsonSafe(kv.value))
                .collect(Collectors.joining(", ", "{", "}"));
        return " " + joined;
    }

    private static String toJsonSafe(Object v) {
        if (v == null) return "null";
        try {
            // Strings saem com aspas; objetos viram JSON
            if (v instanceof CharSequence) {
                return "\"" + v.toString() + "\"";
            }
            return MAPPER.writeValueAsString(v);
        } catch (Exception e) {
            // fallback
            return "\"" + v.toString().replace("\"", "\\\"") + "\"";
        }
    }


}
