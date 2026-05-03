package com.acme.encounter.controller;

import com.acme.encounter.entity.*;
import com.acme.encounter.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/triage")
@RequiredArgsConstructor
public class TriageController {

    // 1. All fields must be 'private final' for @RequiredArgsConstructor
    private final TriageAssessmentService assessmentService;
    private final SymptomNotesService noteService;
    private final TriageRecommendationService recommendationService;
    private final ClinicianOverrideService overrideService;
    private final TriageFinalizeService finalizeService;
    private final AuditService auditService;
    private final VitalSignsService vitalService; // 2. Fixed syntax error

    // 3. Types must match: use String for encounterId/userId if that's what your Service uses
    @PostMapping("/{encounterId}/open")
    public TriageAssessment open(@PathVariable String encounterId, @RequestParam String userId) {
        return assessmentService.openAssessment(encounterId, userId);
    }

    @PostMapping("/{assessmentId}/vitals")
    public void addVitals(@PathVariable Long assessmentId, @RequestBody VitalSign vital) {
        vital.setAssessmentId(assessmentId);
        // 4. Use injected service instance, NOT a static method
        vitalService.capture(vital);
        auditService.logEvent(assessmentId, "VITAL_ADDED");
    }

    @PostMapping("/{assessmentId}/symptoms")
    public void addSymptoms(@PathVariable Long assessmentId, @RequestBody SymptomNote note) {
        note.setAssessmentId(assessmentId);
        noteService.capture(note);
       auditService.logEvent(assessmentId, "SYMPTOM_NOTE_ADDED");
    }

    @PostMapping("/{assessmentId}/recommend")
    public TriageRecommendation recommend(@PathVariable Long assessmentId) {
        TriageRecommendation rec = recommendationService.generateRecommendation(assessmentId);
      auditService.logEvent(assessmentId, "RECOMMENDATION_GENERATED");
        return rec;
    }

    @PostMapping("/{assessmentId}/finalize")
    public void finalize(@PathVariable Long assessmentId) {
        finalizeService.finalize(assessmentId);
       auditService.logEvent(assessmentId, "ASSESSMENT_FINALIZED");
    }
} // 5. Added missing closing brace