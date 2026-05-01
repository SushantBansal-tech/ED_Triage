package com.acme.encounter.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity @Table(name = "triage_scores")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class TriageScore {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long scoreId;
    private Long assessmentId;
    private String scoreType; // RULE_BASED, ML
    private Integer scoreValue;
    private LocalDateTime calculatedAt;
}
