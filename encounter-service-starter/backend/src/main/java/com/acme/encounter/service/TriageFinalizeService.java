package com.acme.encounter.service;

import com.acme.encounter.entity.*;
import com.acme.encounter.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service @RequiredArgsConstructor
public class TriageFinalizeService {
    private  TriageAssessmentRepository assessmentRepo;

    @Transactional
    public void finalize(Long assessmentId) {
        TriageAssessment assessment = assessmentRepo.findById(assessmentId)
                .orElseThrow(() -> new RuntimeException("Assessment not found"));

        assessment.setAssessmentStatus("FINALIZED");
        assessmentRepo.save(assessment);

        // TODO: Push to QueueEventService
    }
}
