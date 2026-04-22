package com.acme.encounter.config;

package com.example.ed.queue.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueueUpdatePublisher {
    private final SimpMessagingTemplate messagingTemplate;

    public void publishZoneUpdate(String zone, Object payload) {
        messagingTemplate.convertAndSend("/topic/queue/zone/" + zone, payload);
        messagingTemplate.convertAndSend("/topic/queue/global", payload);
    }

    public void publishEncounterUpdate(String encounterId, Object payload) {
        messagingTemplate.convertAndSend("/topic/queue/encounter/" + encounterId, payload);
    }
}