package dev.hafeez.performance;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

public final class PerformanceMetrics {
    private static final Map<String, Snapshot> METRICS = new ConcurrentHashMap<>();
    private PerformanceMetrics() {}
    public static void record(String implementation, long elapsedNanos, boolean error) {
        var snapshot = METRICS.computeIfAbsent(implementation, key -> new Snapshot());
        snapshot.requests.increment();
        snapshot.totalNanos.add(elapsedNanos);
        snapshot.maxNanos.updateAndGet(current -> Math.max(current, elapsedNanos));
        if (error) snapshot.errors.increment();
    }
    public static Map<String, Map<String, Long>> snapshot() {
        var result = new ConcurrentHashMap<String, Map<String, Long>>();
        METRICS.forEach((name, value) -> result.put(name, Map.of(
            "requests", value.requests.sum(),
            "errors", value.errors.sum(),
            "totalNanos", value.totalNanos.sum(),
            "maxNanos", value.maxNanos.get()
        )));
        return result;
    }
    private static final class Snapshot {
        private final LongAdder requests = new LongAdder();
        private final LongAdder errors = new LongAdder();
        private final LongAdder totalNanos = new LongAdder();
        private final AtomicLong maxNanos = new AtomicLong();
    }
}
