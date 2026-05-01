package com.acme.encounter.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "triage_recommendations")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TriageRecommendation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendationId;
    private Long assessmentId;
    private String priorityCode; // P1, P2, P3
    private String rationale;
    private LocalDateTime generatedAt;
}
