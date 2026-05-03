package com.acme.encounter.service;

import com.acme.encounter.entity.*;
import com.acme.encounter.repository.*;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class TriageFinalizeService {

    // 1. Mark as final for proper injection
    private  TriageAssessmentRepository assessmentRepo;

    @Transactional
    public void finalize(Long assessmentId) { // 2. Changed Long to String
        TriageAssessment assessment = assessmentRepo.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        // 3. Make sure your Entity has this method, or use the @Query update pattern
        assessment.setTriageStatus("FINALIZED");
        assessmentRepo.save(assessment);
    }
}