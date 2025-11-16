package com.iridium.iridiumcore.logging;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.iridium.iridiumcore.IridiumCore;
import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class OtlpLogHandler extends Handler {

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final String endpoint;
    private final Map<String, String> globalAttributes;
    private final Gson gson = new Gson();

    public OtlpLogHandler(String endpoint, Map<String, String> globalAttributes) {
        this.endpoint = endpoint;
        this.globalAttributes = globalAttributes;
    }

    @Override
    public void publish(LogRecord record) {
        if (record != null) {
            // Send each log asynchronously
            executor.submit(() -> sendLog(record));
        }
    }

    private void sendLog(LogRecord record) {
        try {
            JsonObject root = new JsonObject();
            JsonArray resourceLogsArray = new JsonArray();
            JsonObject resourceLog = new JsonObject();

            // Add resource attributes
            JsonObject resource = new JsonObject();
            JsonArray attributes = new JsonArray();
            globalAttributes.forEach((k, v) -> {
                JsonObject attr = new JsonObject();
                attr.addProperty("key", k);
                JsonObject valueObj = new JsonObject();
                valueObj.addProperty("stringValue", v);
                attr.add("value", valueObj);
                attributes.add(attr);
            });
            resource.add("attributes", attributes);
            resourceLog.add("resource", resource);

            // Add log record
            JsonArray scopeLogs = getJsonElements(record);
            resourceLog.add("scopeLogs", scopeLogs);
            resourceLogsArray.add(resourceLog);
            root.add("resourceLogs", resourceLogsArray);

            // Send via HTTP POST
            URL url = new URL(endpoint);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(gson.toJson(root).getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode >= 400) {
                IridiumCore.getInstance().getLogger().warning("Failed to send log to OTLP, HTTP code: " + responseCode);
            }

            conn.disconnect();

        } catch (Exception e) {
            IridiumCore.getInstance().getLogger().severe(e.getMessage());
            e.printStackTrace();
        }
    }

    private static @NotNull JsonArray getJsonElements(LogRecord record) {
        JsonArray scopeLogs = new JsonArray();
        JsonObject scopeLog = new JsonObject();
        JsonArray logRecords = new JsonArray();

        JsonObject logJson = new JsonObject();
        logJson.addProperty("timeUnixNano", record.getMillis() * 1_000_000L);
        logJson.addProperty("severityText", record.getLevel().getName());

        JsonObject body = new JsonObject();
        body.addProperty("stringValue", record.getMessage());
        logJson.add("body", body);

        logRecords.add(logJson);
        scopeLog.add("logRecords", logRecords);
        scopeLogs.add(scopeLog);
        return scopeLogs;
    }

    @Override
    public void flush() {
        // No batching, nothing to flush
    }

    @Override
    public void close() throws SecurityException {
        executor.shutdown();
    }
}
