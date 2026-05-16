package com.acme.encounter.server;

// server/McpSessionManager.java
@Component
public class McpSessionManager {

    private final ConcurrentHashMap<String, McpSession> sessions
        = new ConcurrentHashMap<>();

    public String createSession(ClientInfo clientInfo) {
        String sessionId = UUID.randomUUID().toString();
        sessions.put(sessionId, McpSession.builder()
            .sessionId(sessionId)
            .clientInfo(clientInfo)
            .createdAt(Instant.now())
            .emitter(new SseEmitter(0L))  // 0 = no timeout
            .build());
        return sessionId;
    }

    public void validate(String sessionId) {
        if (!sessions.containsKey(sessionId))
            throw new McpSessionException("Invalid session: " + sessionId);
    }

    public SseEmitter createEmitter(String sessionId) {
        validate(sessionId);
        return sessions.get(sessionId).getEmitter();
    }

    // Push notification when tool list changes
    public void notifyToolListChanged() {
        sessions.values().forEach(session -> {
            try {
                session.getEmitter().send(
                    SseEmitter.event()
                        .name("tools/list_changed")
                        .data("{}")
                );
            } catch (IOException e) {
                sessions.remove(session.getSessionId());
            }
        });
    }
}
