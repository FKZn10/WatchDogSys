package com.jpmsoft.projectProducer.service.cockpit;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.projectProducer.pojo.entity.cockpit.SmartSiteCamera;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Clock;
import java.util.*;
import java.util.concurrent.*;

/** Read-only status, separate from administrative Ssc_State and video playback. */
@Service
public class EzvizDeviceService {
    static final String ENDPOINT = "https://open.ys7.com/api/lapp/device/camera/list";
    private final EzvizPlaybackService credentials;
    private final RestTemplate http;
    private final Clock clock;
    private final ExecutorService workers;
    private final ObjectMapper json = new ObjectMapper();
    private final ConcurrentHashMap<String, Entry> cache = new ConcurrentHashMap<>();

    @Autowired
    public EzvizDeviceService(EzvizPlaybackService credentials) {
        this(credentials, httpClient(), Clock.systemUTC(), new ThreadPoolExecutor(4, 4, 30,
            TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(64), task -> {
                Thread thread = new Thread(task, "ezviz-status"); thread.setDaemon(true); return thread;
            }, new ThreadPoolExecutor.AbortPolicy()));
    }

    EzvizDeviceService(EzvizPlaybackService credentials, RestTemplate http, Clock clock, ExecutorService workers) {
        this.credentials = credentials; this.http = http; this.clock = clock; this.workers = workers;
    }

    public List<OnlineStatus> query(List<SmartSiteCamera> cameras) {
        Map<String, Entry> requested = new HashMap<>();
        List<Entry> entries = new ArrayList<>();
        List<String> channels = new ArrayList<>();
        for (SmartSiteCamera camera : cameras) {
            try {
                String[] binding = credentials.statusBinding(camera);
                String key = digest(camera.getSscKey() + "\n" + camera.getSscSecret() + "\n" + binding[0]);
                Entry entry = requested.get(key);
                if (entry == null) {
                    entry = cached(key, camera, binding[0]); requested.put(key, entry);
                }
                entries.add(entry); channels.add(binding[1]);
            } catch (IllegalArgumentException ex) {
                entries.add(null); channels.add("");
            }
        }
        try {
            CompletableFuture.allOf(requested.values().stream().map(e -> e.future)
                .toArray(CompletableFuture[]::new)).get(8, TimeUnit.SECONDS);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException | TimeoutException ignored) {
            // Partial responses are valid. Pending/failed channels remain UNKNOWN.
        }
        List<OnlineStatus> result = new ArrayList<>();
        for (int i = 0; i < cameras.size(); i++) {
            Entry entry = entries.get(i);
            Snapshot snapshot = entry == null ? null : entry.future.getNow(null);
            String state = snapshot == null ? "unknown" : snapshot.channels.getOrDefault(channels.get(i), "unknown");
            result.add(new OnlineStatus(cameras.get(i).getSscId(), state, snapshot == null ? null : snapshot.checkedAt));
        }
        return result;
    }

    private Entry cached(String key, SmartSiteCamera camera, String serial) {
        long now = clock.millis();
        if (cache.size() >= 256) {
            cache.entrySet().removeIf(e -> e.getValue().future.isDone() && e.getValue().expiresAt <= now);
            if (cache.size() >= 256 && !cache.containsKey(key)) return completedUnknown();
        }
        return cache.compute(key, (ignored, old) -> {
            if (old != null && (!old.future.isDone() || old.expiresAt > now)) return old;
            Entry next = new Entry();
            try {
                workers.execute(() -> {
                    Snapshot snapshot = fetch(camera, serial);
                    next.expiresAt = clock.millis() + (snapshot.valid ? 30_000L : 5_000L);
                    next.future.complete(snapshot);
                });
            } catch (RejectedExecutionException ex) {
                next.expiresAt = now + 5_000L;
                next.future.complete(new Snapshot(Collections.emptyMap(), null, false));
            }
            return next;
        });
    }

    private Entry completedUnknown() {
        Entry entry = new Entry(); entry.future.complete(new Snapshot(Collections.emptyMap(), null, false)); return entry;
    }

    private Snapshot fetch(SmartSiteCamera camera, String serial) {
        try {
            MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
            form.add("accessToken", credentials.statusAccessToken(camera)); form.add("deviceSerial", serial);
            JsonNode response = json.readTree(http.postForObject(ENDPOINT, form, String.class));
            if (response == null || !"200".equals(response.path("code").asText()) || !response.path("data").isArray())
                return new Snapshot(Collections.emptyMap(), null, false);
            Map<String, String> states = new HashMap<>();
            for (JsonNode channel : response.path("data")) {
                if (!serial.equalsIgnoreCase(channel.path("deviceSerial").asText())) continue;
                String value = channel.path("status").asText();
                states.put(channel.path("channelNo").asText(), "1".equals(value) ? "online" : "0".equals(value) ? "offline" : "unknown");
            }
            return new Snapshot(states, clock.millis(), true);
        } catch (Exception ignored) {
            // Vendor errors may contain tokens; do not log bodies, credentials or exception messages.
            return new Snapshot(Collections.emptyMap(), null, false);
        }
    }

    private static String digest(String value) {
        try { return Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256").digest(value.getBytes(StandardCharsets.UTF_8))); }
        catch (java.security.NoSuchAlgorithmException ex) { throw new IllegalStateException("SHA-256 unavailable"); }
    }
    private static RestTemplate httpClient() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2_000); factory.setReadTimeout(3_000); return new RestTemplate(factory);
    }
    @PreDestroy public void close() { workers.shutdownNow(); }
    static final class Entry {
        final CompletableFuture<Snapshot> future = new CompletableFuture<>();
        volatile long expiresAt;
    }
    static final class Snapshot {
        final Map<String, String> channels; final Long checkedAt; final boolean valid;
        Snapshot(Map<String, String> channels, Long checkedAt, boolean valid) { this.channels=channels; this.checkedAt=checkedAt; this.valid=valid; }
    }
    public static final class OnlineStatus {
        private final String sscId, onlineStatus; private final Long checkedAt;
        OnlineStatus(String id, String status, Long checkedAt) { this.sscId=id; this.onlineStatus=status; this.checkedAt=checkedAt; }
        public String getSscId() { return sscId; }
        public String getOnlineStatus() { return onlineStatus; }
        public Long getCheckedAt() { return checkedAt; }
    }
}
