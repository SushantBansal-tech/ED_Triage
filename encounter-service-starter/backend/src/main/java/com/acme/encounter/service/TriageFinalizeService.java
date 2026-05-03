package com.acme.encounter.service;

import com.acme.encounter.entity.*;
import com.acme.encounter.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TriageFinalizeService {
    private final TriageAssessmentRepository assessmentRepo;
    private final QueueRankingService queueRankingService; // Injected
    private final QueueEntryRepository queueEntryRepo;      // Repository for QueueEntry

    @Transactional
    public void finalize(Long assessmentId) {
        // 1. Update the triage assessment status in the database
        assessmentRepo.updateTriageStatus(assessmentId, "FINALIZED");

        // 2. Fetch all active patients currently in the queue
        List<QueueEntry> activeEntries = queueEntryRepo.findByStatus("IN_QUEUE");

        // 3. Trigger the ranking service to recompute ranks based on the new triage priority
        List<QueueEntry> recomputedEntries = queueRankingService.recompute(activeEntries);

        // 4. Save the newly ranked queue entries back to the database
        queueEntryRepo.saveAll(recomputedEntries);
    }
}