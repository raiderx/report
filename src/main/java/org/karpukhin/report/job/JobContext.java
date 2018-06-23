package org.karpukhin.report.job;

import org.apache.commons.text.StringSubstitutor;

import java.util.HashMap;
import java.util.Map;

public class JobContext {

    private static final ThreadLocal<Map<String, Object>> context = new ThreadLocal<>();

    public static void set(Map<String, Object> data) {
        context.set(data);
    }

    public static void set(String name, Object value) {
        Map<String, Object> data = context.get();
        if (data == null) {
            data = new HashMap<>();
            context.set(data);
        }
        data.put(name, value);
    }

    public static <T> T get(String name) {
        return (T) context.get().get(name);
    }

    public static String replace(String value) {
        StringSubstitutor substitutor = new StringSubstitutor(context.get());
        return substitutor.replace(value);
    }

    public static void remove() {
        context.remove();
    }
}
