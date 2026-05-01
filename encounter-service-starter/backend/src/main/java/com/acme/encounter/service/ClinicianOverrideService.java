package com.acme.encounter.service;

import com.acme.encounter.entity.*;
import com.acme.encounter.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class ClinicianOverrideService {
    private  TriageAssessmentRepository assessmentRepo;
    private  TriageRecommendationRepository recRepo;

    @Transactional
    public void applyOverride(Long assessmentId, String newPriority, String reason) {
        TriageAssessment assessment = assessmentRepo.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        // Finalize state guard
        if ("FINALIZED".equals(assessment.getAssessmentStatus())) {
            throw new IllegalStateException("Cannot override finalized assessment");
        }

        assessment.setFinalPriorityCode(newPriority);
        assessment.setOverrideApplied(true);
        assessmentRepo.save(assessment);
    }
}
