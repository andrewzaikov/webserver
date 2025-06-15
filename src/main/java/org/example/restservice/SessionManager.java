package org.example.restservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class (singleton, static).
 * Manages sessions.
 */
public class SessionManager {
    private static final Map<String, SessionData> SESSION_DATA_MAP = new ConcurrentHashMap<>();

    /**
     * Registers new session
     *
     * @return UUID of created session
     */
    public static String register() {
        String sessionId = UUID.randomUUID().toString();

        SESSION_DATA_MAP.put(sessionId, new SessionData(System.currentTimeMillis(), true));
        return sessionId;
    }

    /**
     * Checks if session exists and alive
     *
     * @param sessionId UUID of session to check
     * @return true if session alive, false otherwise
     */
    public static boolean checkSession(String sessionId) {
        SessionData data = SESSION_DATA_MAP.get(sessionId);
        if (data == null || !data.isAlive) {
            return false;
        }

        long currentsTime = System.currentTimeMillis();
        // 30 sec
        data.isAlive = (currentsTime - data.lastUpdate) <= 30000;
        data.lastUpdate = currentsTime;
        // update session data
        SESSION_DATA_MAP.put(sessionId, data);

        return data.isAlive;
    }

    /**
     * Servise method to see session data
     * @return current session data
     */
    public static List<String> getStat() {
        List<String> stat = new ArrayList<>();
        for (var item : SESSION_DATA_MAP.keySet()) {
            stat.add("sess=" + item + ", data=" + SESSION_DATA_MAP.get(item));
        }
        return stat;
    }

    /**
     * Internal datatype for session information
     */
    private static class SessionData {
        private long lastUpdate;
        private boolean isAlive;

        public SessionData(long lastUpdate, boolean isAlive) {
            this.lastUpdate = lastUpdate;
            this.isAlive = isAlive;
        }

        @Override
        public String toString() {
            return "SessionData{" +
                    "lastUpdate=" + lastUpdate +
                    ", isAlive=" + isAlive +
                    '}';
        }
    }
}
