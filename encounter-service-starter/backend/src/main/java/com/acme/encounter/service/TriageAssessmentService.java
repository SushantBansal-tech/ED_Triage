package com.acme.encounter.service;

import com.acme.encounter.entity.TriageAssessment;
import com.acme.encounter.repository.TriageAssessmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TriageAssessmentService {

    private final TriageAssessmentRepository assessmentRepository;

    @Transactional
    public TriageAssessment openAssessment(String encounterId, String userId) {
        // 1. Validate if an active assessment already exists
        if (assessmentRepository.findByEncounterIdAndTriageStatus(encounterId, "OPEN").isPresent()) {
            throw new IllegalStateException("An open triage assessment already exists for this encounter.");
        }

        // 2. Build the new assessment object (WITHOUT setting the ID)
        TriageAssessment newAssessment = TriageAssessment.builder()
                .encounterId(encounterId)
                .triageStatus("OPEN")
                .assessedBy(userId)
                .assessmentStartedAt(OffsetDateTime.now())
                .createdAt(OffsetDateTime.now())
                .updatedAt(OffsetDateTime.now())
                .build();

        // 3. The database will automatically assign the Long ID here upon saving
        return assessmentRepository.save(newAssessment);
    }
}