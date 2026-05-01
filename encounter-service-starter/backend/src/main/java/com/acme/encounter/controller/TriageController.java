package com.acme.encounter.controller;

import com.acme.encounter.entity.*;
import com.acme.encounter.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1/triage")
@RequiredArgsConstructor
public class TriageController {
    private  TriageAssessmentService assessmentService;
    private  SymptomNotesService noteService;
    private  TriageRecommendationService recommendationService;
    private  ClinicianOverrideService overrideService;
    private  TriageFinalizeService finalizeService;
    private  TriageAuditService auditService;

    @PostMapping("/{encounterId}/open")
    public TriageAssessment open(@PathVariable Long encounterId, @RequestParam Long userId) {
        return assessmentService.openAssessment(encounterId, userId);
    }

    @PostMapping("/{assessmentId}/vitals")
    public void addVitals(@PathVariable Long assessmentId, @RequestBody VitalSign vital) {
        vital.setAssessmentId(assessmentId);
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
