package com.example.ed.queue.service;

import com.example.ed.queue.entity.QueueEntry;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class QueueRankingService {

    public List<QueueEntry> recompute(List<QueueEntry> entries) {
        AtomicInteger rank = new AtomicInteger(1);

        return entries.stream()
                .sorted(Comparator
                        .comparingInt((QueueEntry e) -> priorityWeight(resolvePriority(e))).reversed()
                        .thenComparing(QueueEntry::getWaitingSince))
                .map(entry -> {
                    entry.setQueueRank(rank.getAndIncrement());
                    entry.setLastRecomputedAt(OffsetDateTime.now());
                    return entry;
                })
                .toList();
    }

    private String resolvePriority(QueueEntry e) {
        if (e.getManualOverridePriority() != null) return e.getManualOverridePriority();
        if (e.getRecommendedPriorityCode() != null) return e.getRecommendedPriorityCode();
        return e.getPriorityCode();
    }

    private int priorityWeight(String code) {
        return switch (code) {
            case "P1" -> 300;
            case "P2" -> 200;
            case "P3" -> 100;
            default -> 0;
        };
    }
}