package com.acme.encounter.service;


import com.example.ed.triage.entity.TriageAssessment;
import com.example.ed.triage.entity.TriageVital;
import com.example.ed.triage.dto.TriageRecommendationResult;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.List;

@Component
public class TriageRecommendationEngine {

    public TriageRecommendationResult generate(TriageAssessment assessment, List<TriageVital> vitals) {
        Map<String, Double> map = vitals.stream()
                .collect(Collectors.toMap(TriageVital::getVitalType, TriageVital::getVitalValue, (a, b) -> b));

        int score = 0;
        if (map.getOrDefault("spo2", 100.0) < 92) score += 35;
        if (map.getOrDefault("heart_rate", 80.0) > 120) score += 20;
        if (map.getOrDefault("systolic_bp", 120.0) < 90) score += 30;
        if (assessment.getPainScore() != null && assessment.getPainScore() >= 8) score += 10;

        boolean escalation = score >= 40;
        String priority = escalation ? "P1" : score >= 20 ? "P2" : "P3";
        String bucket = escalation ? "resuscitation" : score >= 20 ? "urgent" : "standard";
        String level = escalation ? "high" : score >= 20 ? "medium" : "low";

        return new TriageRecommendationResult(priority, bucket, score, level, escalation,
                "Derived from vital sign thresholds and pain score");
    }
}
