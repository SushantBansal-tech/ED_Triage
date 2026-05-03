package com.acme.encounter.service;

import com.acme.encounter.entity.*;
import com.acme.encounter.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ClinicianOverrideService {
    // 1. MUST be 'private final' for @RequiredArgsConstructor
    private final TriageAssessmentRepository assessmentRepo;
    private final TriageRecommendationRepository recRepo;

    @Transactional
    public void applyOverride(Long assessmentId, String newPriority, String reason) {
        // 2. assessmentRepo now expects Long
        TriageAssessment assessment = assessmentRepo.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        if ("FINALIZED".equals(assessment.getTriageStatus())) { // 3. Ensure method name matches your Entity
            throw new IllegalStateException("Cannot override finalized assessment");
        }

        //assessment.setFinalPriorityCode(newPriority); // Ensure these setters exist in your Entity
        //assessment.setOverrideApplied(true);
        assessmentRepo.save(assessment);
    }
}
