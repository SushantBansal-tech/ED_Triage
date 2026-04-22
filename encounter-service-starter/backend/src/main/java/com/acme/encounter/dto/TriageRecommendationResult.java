package com.acme.encounter.dto;



public record TriageRecommendationResult(
        String recommendedPriorityCode,
        String recommendedQueueBucket,
        Integer riskScore,
        String riskLevel,
        Boolean escalationRequired,
        String recommendationReason
) {}
