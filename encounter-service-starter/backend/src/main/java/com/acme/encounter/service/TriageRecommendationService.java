package com.acme.encounter.service;

import com.acme.encounter.entity.*;
import com.acme.encounter.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service @RequiredArgsConstructor
public class TriageRecommendationService {
    private  TriageRecommendationRepository recRepo;
    private  TriageAssessmentRepository assessmentRepo;

    public TriageRecommendation generateRecommendation(Long assessmentId) {
        TriageAssessment assessment = assessmentRepo.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        // Logic from PDF: get vitals/notes, call Engine, persist
        TriageRecommendation rec = TriageRecommendation.builder()
                .assessmentId(assessmentId)
                .priorityCode("P2") // Example placeholder
                .rationale("Based on vitals check")
                .generatedAt(LocalDateTime.now())
                .build();

        return recRepo.save(rec);
    }
}
